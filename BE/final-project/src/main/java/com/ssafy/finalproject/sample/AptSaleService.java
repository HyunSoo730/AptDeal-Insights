package com.ssafy.finalproject.sample;// AptSaleService.java

// ... 기존 import 문 생략

import com.ssafy.finalproject.aptsale.entity.AptSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AptSaleService {

    private final AptSaleRepository aptSaleRepository;

    @Autowired
    public AptSaleService(AptSaleRepository aptSaleRepository) {
        this.aptSaleRepository = aptSaleRepository;
    }

    public List<AptSale> getAllAptSales() {
        return aptSaleRepository.findAll();
    }
}