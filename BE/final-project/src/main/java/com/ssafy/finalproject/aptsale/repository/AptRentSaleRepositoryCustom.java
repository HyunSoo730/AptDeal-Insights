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
        System.out.println("searchCondition: " + searchCondition);
        return queryFactory
                .select(new QAptRentSaleDTO(
                        aptRentSale.constructionYear, aptRentSale.contractType, aptRentSale.contractPeriod,
                        aptRentSale.year, aptRentSale.legalDong, aptRentSale.depositAmount, aptRentSale.apartmentName,
                        aptRentSale.month, aptRentSale.monthlyRent, aptRentSale.day, aptRentSale.exclusiveArea,
                        aptRentSale.previousContractDeposit, aptRentSale.previousContractRent, aptRentSale.regionCode,
                        aptRentSale.floor
                ))
                .from(aptRentSale)
                .where(buildBooleanExpression(searchCondition))
//                .where(eqRegionCode(searchCondition.getRegionCode()), eqLegalDong(searchCondition.getLegalDong()))
                .offset(searchCondition.getOffset())
                .limit(searchCondition.getLimit())
                .fetch();
    }

    // TODO:  모든 조건 조합
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
                .and(loeEndDate(searchCondition.getEndDate()));

        return builder;
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
