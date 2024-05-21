package com.ssafy.finalproject.aptsale.repository;

import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.review.entity.AptReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


import java.util.List;

public interface AptSaleRepository extends JpaRepository<AptSale,Long> {

    List<AptSale> findAptSaleByAptCodeOrderByDealYearDescDealMonthDescDealDayDesc(String aptCode);

    @Query("SELECT new com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO(a.aptName, a.dealAmount, a.roadName, a.constructionYear, a.aptCode, a.latitude, a.longitude) FROM AptSale a WHERE a.dongcode = :dongCode GROUP BY a.aptCode")
    List<AptSaleByDongDTO> findByDongcodeGroupByAptCode(@Param("dongCode") String dongCode);


    List<AptSale> findByAptCode(String aptCode);
}
