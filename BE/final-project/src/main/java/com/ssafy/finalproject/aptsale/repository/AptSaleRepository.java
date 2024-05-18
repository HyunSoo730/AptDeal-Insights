package com.ssafy.finalproject.aptsale.repository;

import com.ssafy.finalproject.aptsale.entity.AptSale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AptSaleRepository extends JpaRepository<AptSale,Long> {

//    @Query("SELECT y.dongcode FROM YourEntity y WHERE y.dongcode = :dongcode GROUP BY y.aptCode")
//    List<String> findGroupedByAptCode(@Param("dongcode") String dongcode);

//    List<AptSale> findByDongCodeGroupByAptCode(String dongcode);
}
