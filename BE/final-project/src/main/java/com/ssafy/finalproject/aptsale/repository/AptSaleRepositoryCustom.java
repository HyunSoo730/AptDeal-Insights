package com.ssafy.finalproject.aptsale.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.dto.request.QAptNameAddressDTO;
import com.ssafy.finalproject.aptsale.dto.request.QAptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptSaleGraphDTO;
import com.ssafy.finalproject.aptsale.dto.response.QAptSaleGraphDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.ssafy.finalproject.aptsale.entity.QAptSale.aptSale;

@Repository
@RequiredArgsConstructor
public class AptSaleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // TODO : 동코드로 group by 해서 페이징 처리 진행.
    public List<AptSaleByDongDTO> getApartmentsByDongCode(String dongcode, int limit, int offset) {
        return queryFactory
                .select(new QAptSaleByDongDTO(
                        aptSale.aptName, aptSale.dealAmount, aptSale.roadName, aptSale.constructionYear, aptSale.aptCode, aptSale.latitude, aptSale.longitude
                ))
                .from(aptSale)
                .where(aptSale.dongcode.eq(dongcode))
                .groupBy(aptSale.aptCode)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    // TODO : 원하는 아파트 입력 시 자동완성 (최대 10개)
    public List<AptNameAddressDTO> findAptNamesByPrefix(String prefix, int offset, int limit) {
        return queryFactory
                .select(new QAptNameAddressDTO(
                        aptSale.aptCode, aptSale.aptName, aptSale.roadNameAddress, aptSale.latitude, aptSale.longitude, aptSale.dongcode
                ))
                .from(aptSale)
                .where(aptSale.aptName.startsWith(prefix))
                .groupBy(aptSale.aptName)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    // TODO : 구(지역) 거래 정보 조회
    public List<AptSaleGraphDTO> findSalesByDongcode(String dongcodePrefix, int recentYears) {
        LocalDate startDate = LocalDate.now().minusYears(recentYears); // ! 가져오고자 하는 시작 년도
        return queryFactory
                .select(new QAptSaleGraphDTO(
                        aptSale.aptCode, aptSale.aptName, aptSale.dealAmount, aptSale.dealYear, aptSale.dealMonth, aptSale.dealDay, aptSale.exclusiveArea, aptSale.dongcode
                ))
                .from(aptSale)
                .where(
                        aptSale.dongcode.startsWith(dongcodePrefix)
                                .and(aptSale.dealYear.goe(startDate.getYear()))
                )
                .fetch();
    }

    // TODO : 특정 아파트의 거래 정보 조회
    public List<AptSaleGraphDTO> findSalesByAptCode(String aptCode, int recentYears) {
        LocalDate startDate = LocalDate.now().minusYears(recentYears);
        return queryFactory
                .select(new QAptSaleGraphDTO(
                        aptSale.aptCode, aptSale.aptName, aptSale.dealAmount, aptSale.dealYear, aptSale.dealMonth, aptSale.dealDay, aptSale.exclusiveArea, aptSale.dongcode
                ))
                .from(aptSale)
                .where(
                        aptSale.aptCode.eq(aptCode)
                                .and(aptSale.dealYear.goe(startDate.getYear()))
                )
                .fetch();
    }


}
