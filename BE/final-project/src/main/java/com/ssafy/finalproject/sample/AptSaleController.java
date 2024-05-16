package com.ssafy.finalproject.sample;// AptSaleController.java

// ... 기존 import 문 생략

import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.sample.AptSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AptSaleController {

    private final AptSaleService aptSaleService;

    @Autowired
    public AptSaleController(AptSaleService aptSaleService) {
        this.aptSaleService = aptSaleService;
    }

    @GetMapping("/apt-sales")
    public List<Map<String, Object>> getAptSales() {
        List<AptSale> aptSaleList = aptSaleService.getAllAptSales();
        List<Map<String, Object>> result = new ArrayList<>();
        for (AptSale aptSale : aptSaleList) {
            Map<String, Object> aptSaleMap = new HashMap<>();
            aptSaleMap.put("latitude", aptSale.getLatitude());
            aptSaleMap.put("longitude", aptSale.getLongitude());
            aptSaleMap.put("aptName", aptSale.getAptName());
            result.add(aptSaleMap);
        }
        return result;
    }
}