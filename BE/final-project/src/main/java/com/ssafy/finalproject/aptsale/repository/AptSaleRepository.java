package com.ssafy.finalproject.aptsale.repository;

import com.ssafy.finalproject.aptsale.entity.AptSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AptSaleRepository extends JpaRepository<AptSale,Long> {

    List<AptSale> findAptSaleByAptCodeOrderByDealYearDescDealMonthDescDealDayDesc(String aptCode);
}
