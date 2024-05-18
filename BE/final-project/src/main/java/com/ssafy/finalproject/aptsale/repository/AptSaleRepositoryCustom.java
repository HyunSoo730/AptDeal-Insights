package com.ssafy.finalproject.aptsale.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import com.ssafy.finalproject.aptsale.dto.request.QAptNameAddressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.finalproject.aptsale.entity.QAptSale.aptSale;

@Repository
@RequiredArgsConstructor
public class AptSaleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // TODO : 원하는 아파트 입력 시 자동완성 (최대 10개)
    public List<AptNameAddressDTO> findAptNamesByPrefix(String prefix, int offset, int limit) {
        return queryFactory
                .select(new QAptNameAddressDTO(
                        aptSale.aptCode, aptSale.aptName, aptSale.roadNameAddress, aptSale.latitude, aptSale.longitude
                ))
                .from(aptSale)
                .where(aptSale.aptName.startsWith(prefix))
                .groupBy(aptSale.aptName)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

}
