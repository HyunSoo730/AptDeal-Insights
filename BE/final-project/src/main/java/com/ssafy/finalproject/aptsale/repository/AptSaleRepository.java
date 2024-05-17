package com.ssafy.finalproject.aptsale.repository;

import com.ssafy.finalproject.aptsale.entity.AptSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AptSaleRepository extends JpaRepository<AptSale,Long> {

    @Query("SELECT * FROM YourEntity y WHERE y.dongcode = :dongcode GROUP BY y.aptCode")
    List<String> findGroupedByAptCode(@Param("dongcode") String dongcode);

}
