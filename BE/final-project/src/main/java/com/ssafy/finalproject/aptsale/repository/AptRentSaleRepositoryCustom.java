package com.ssafy.finalproject.aptsale.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.finalproject.aptsale.dto.request.SearchConditionDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptRentSaleDTO;
import com.ssafy.finalproject.aptsale.dto.response.QAptRentSaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.ssafy.finalproject.aptsale.entity.QAptRentSale.aptRentSale;

@Repository
@RequiredArgsConstructor
public class AptRentSaleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // TODO : 구(지역) 및 동(법정동) 전월세 정보 조회
    public List<AptRentSaleDTO> findRentSalesByConditions(SearchConditionDTO searchCondition) {
//        System.out.println("searchCondition: " + searchCondition);
        return queryFactory
                .select(new QAptRentSaleDTO(
                        aptRentSale.constructionYear, aptRentSale.contractType, aptRentSale.contractPeriod,
                        aptRentSale.year, aptRentSale.legalDong, aptRentSale.depositAmount, aptRentSale.apartmentName,
                        aptRentSale.month, aptRentSale.monthlyRent, aptRentSale.day, aptRentSale.exclusiveArea,
                        aptRentSale.previousContractDeposit, aptRentSale.previousContractRent, aptRentSale.regionCode,
                        aptRentSale.floor
                ))
                .from(aptRentSale)
                .where(buildBooleanExpression(searchCondition), inExclusiveAreaRanges(searchCondition.getSelectedPyeongRanges()))
//                .where(eqRegionCode(searchCondition.getRegionCode()), eqLegalDong(searchCondition.getLegalDong()))
                .offset(searchCondition.getOffset())
                .limit(searchCondition.getLimit())
                .fetch();
    }

    // TODO : 특정 아파트의 최근 N년간의 전월세 거래 데이터 조회
    public List<AptRentSaleDTO> findRentSalesByApartmentAndYears(String apartmentName, int years) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(years);

        return queryFactory
                .select(new QAptRentSaleDTO(
                        aptRentSale.constructionYear, aptRentSale.contractType, aptRentSale.contractPeriod,
                        aptRentSale.year, aptRentSale.legalDong, aptRentSale.depositAmount, aptRentSale.apartmentName,
                        aptRentSale.month, aptRentSale.monthlyRent, aptRentSale.day, aptRentSale.exclusiveArea,
                        aptRentSale.previousContractDeposit, aptRentSale.previousContractRent, aptRentSale.regionCode,
                        aptRentSale.floor
                ))
                .from(aptRentSale)
                .where(aptRentSale.apartmentName.eq(apartmentName).and(aptRentSale.year.goe(startDate.getYear())))
                .fetch();
    }

    // TODO : 특정 구의 최근 N년간의 전월세 거래 데이터 조회
    public List<AptRentSaleDTO> findRentSalesByRegionCodeAndYears(String regionCode, int years) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusYears(years);

        return queryFactory
                .select(new QAptRentSaleDTO(
                        aptRentSale.constructionYear, aptRentSale.contractType, aptRentSale.contractPeriod,
                        aptRentSale.year, aptRentSale.legalDong, aptRentSale.depositAmount, aptRentSale.apartmentName,
                        aptRentSale.month, aptRentSale.monthlyRent, aptRentSale.day, aptRentSale.exclusiveArea,
                        aptRentSale.previousContractDeposit, aptRentSale.previousContractRent, aptRentSale.regionCode,
                        aptRentSale.floor
                ))
                .from(aptRentSale)
                .where(aptRentSale.regionCode.eq(regionCode).and(aptRentSale.year.goe(startDate.getYear())))
                .fetch();
    }

    // TODO:  모든 조건 조합
    // ! BooleanBuilder는 null값 허용 -> null인 경우 해당 조건 무시된다. (NPE 발생하지 않고 알아서 처리함 -> getter가 null 반환해도 상관없음)
    private BooleanBuilder buildBooleanExpression(SearchConditionDTO searchCondition) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(eqRegionCode(searchCondition.getRegionCode()))
                .and(eqLegalDong(searchCondition.getLegalDong()))
                .and(goeDeposit(searchCondition.getMinDeposit()))
                .and(loeDeposit(searchCondition.getMaxDeposit()))
                .and(goeMonthlyRent(searchCondition.getMinMonthlyRent()))
                .and(loeMonthlyRent(searchCondition.getMaxMonthlyRent()))
                .and(goeExclusiveArea(searchCondition.getMinExclusiveArea()))
                .and(loeExclusiveArea(searchCondition.getMaxExclusiveArea()))
                .and(goeStartDate(searchCondition.getStartDate()))
                .and(loeEndDate(searchCondition.getEndDate()))
                .and(eqIsCharter(searchCondition.getIsCharter()))
                .and(containsApartmentName(searchCondition.getApartmentName()));

        return builder;
    }

    // ! 전세인지 반전세(월세)인지 -> monthlyRent값이 0 이면 전세
    private BooleanExpression eqIsCharter(Boolean isCharter) {
        if (isCharter == null) {
            return null; // 전체
        } else if (isCharter) {
            return aptRentSale.monthlyRent.eq(0); // * 전세
        } else {
            return aptRentSale.monthlyRent.gt(0); // * 반전세(월세)
        }
    }

    // ! 선택한 평수 범위에 해당하는 전용면적 조건 추가
    private BooleanBuilder inExclusiveAreaRanges(List<Integer> selectedPyeongRanges) {
        if (selectedPyeongRanges == null || selectedPyeongRanges.isEmpty()) {
            return new BooleanBuilder(); // ! 전체를 의미하도록 빈 BooleanBuilder 반환
        }

        BooleanBuilder builder = new BooleanBuilder();
        for (Integer pyeong : selectedPyeongRanges) {
            double minArea = pyeong * 3.3;
            double maxArea = (pyeong + 9) * 3.3;
            builder.or(aptRentSale.exclusiveArea.between(minArea, maxArea));
        }
//        System.out.println(builder.toString());

        return builder;
    }

    private BooleanExpression containsApartmentName(String apartmentName) {
        return apartmentName != null ? aptRentSale.apartmentName.contains(apartmentName) : null;
    }

    private BooleanExpression eqRegionCode(String regionCode) {
        return regionCode != null ? aptRentSale.regionCode.eq(regionCode) : null;
    }

    private BooleanExpression eqLegalDong(String legalDong) {
        return legalDong != null ? aptRentSale.legalDong.eq(legalDong) : null;
    }

    private BooleanExpression goeDeposit(Integer minDeposit) {
        return minDeposit != null ? aptRentSale.depositAmount.goe(minDeposit) : null;
    }

    private BooleanExpression loeDeposit(Integer maxDeposit) {
        return maxDeposit != null ? aptRentSale.depositAmount.loe(maxDeposit) : null;
    }

    private BooleanExpression goeMonthlyRent(Integer minMonthlyRent) {
        return minMonthlyRent != null ? aptRentSale.monthlyRent.goe(minMonthlyRent) : null;
    }

    private BooleanExpression loeMonthlyRent(Integer maxMonthlyRent) {
        return maxMonthlyRent != null ? aptRentSale.monthlyRent.loe(maxMonthlyRent) : null;
    }

    private BooleanExpression goeExclusiveArea(Double minExclusiveArea) {
        return minExclusiveArea != null ? aptRentSale.exclusiveArea.goe(minExclusiveArea) : null;
    }

    private BooleanExpression loeExclusiveArea(Double maxExclusiveArea) {
        return maxExclusiveArea != null ? aptRentSale.exclusiveArea.loe(maxExclusiveArea) : null;
    }

    private BooleanExpression goeStartDate(LocalDate startDate) {
        if (startDate == null) {
            return null;
        }
        return aptRentSale.year.goe(startDate.getYear())
                .and(aptRentSale.month.goe(startDate.getMonthValue()));
    }

    private BooleanExpression loeEndDate(LocalDate endDate) {
        if (endDate == null) {
            return null;
        }
        return aptRentSale.year.loe(endDate.getYear())
                .and(aptRentSale.month.loe(endDate.getMonthValue()));
    }

}
