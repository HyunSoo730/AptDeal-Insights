package com.ssafy.finalproject.aptsale.Controller;


import com.ssafy.finalproject.aptsale.Service.AptRentSaleService;
import com.ssafy.finalproject.aptsale.Service.AptSaleService;
import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import com.ssafy.finalproject.aptsale.dto.request.SearchConditionDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptRentSaleDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptSaleDetails;
import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptTransactionResDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apt-sales")
@RequiredArgsConstructor
@Slf4j
public class AptSaleController {

    private final AptSaleService aptSaleService;
    private final AptRentSaleService aptRentSaleService;

    @GetMapping("/apt-names")
    public ResponseEntity<List<AptNameAddressDTO>> getAptNameAddressByPrefix(
            @RequestParam String prefix,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<AptNameAddressDTO> aptNameAddresses = aptSaleService.findAptNameAddressByPrefix(prefix, page, size);
        return new ResponseEntity<>(aptNameAddresses, HttpStatus.OK);

    }


    // AptSaleController.java
    @GetMapping("/apartments")
    public List<AptSaleByDongDTO> getApartmentsByDongCode(
            @RequestParam("dongCode") String dongcode) {
        return aptSaleService.getApartmentsByDongCode(dongcode);
    }

    @GetMapping("/details/{aptCode}")
    public ResponseEntity<?> getAptSaleDetails(@PathVariable("aptCode") String aptCode) {
//        log.info("지금 여기 호출");
        List<AptSaleDetails> aptSaleDetailsList = aptSaleService.findAptSaleDetails(aptCode);
        return new ResponseEntity<>(aptSaleDetailsList, HttpStatus.OK);
    }

    @GetMapping("/area/{dongcode}/{years}")
    public List<AptTransactionResDTO> getTransactionsByDongcode(@PathVariable("dongcode") String dongcode, @PathVariable("years") int years) {
        List<AptTransactionResDTO> recentSalesByDongcode = aptSaleService.findRecentSalesByDongcode(dongcode, years);
        if (recentSalesByDongcode.isEmpty()) {
            System.out.println("데이터없음");
        } else {
            System.out.println("데이터 있음");
        }
        for (AptTransactionResDTO aptTransactionResDTO : recentSalesByDongcode) {
            log.info("aptTransactionResDto: {}", aptTransactionResDTO);
        }
        return recentSalesByDongcode;
    }

    @GetMapping("/apartment/{aptCode}/{years}")
    public List<AptTransactionResDTO> getTransactionsByAptCode(@PathVariable("aptCode") String aptCode, @PathVariable("years") int years) {
        List<AptTransactionResDTO> recentSalesByAptCode = aptSaleService.findRecentSalesByAptCode(aptCode, years);
        if (recentSalesByAptCode.size() > 0) {
            System.out.println("데이터 존재");
        } else {
            System.out.println("데이터 없음");
        }

        for (AptTransactionResDTO aptTransactionResDTO : recentSalesByAptCode) {

            log.info("AptTransactionResDTO : {}", aptTransactionResDTO);
        }
        return recentSalesByAptCode;
    }


    @PostMapping("/registerSale")
    public void registerSale(@RequestBody AptSale aptSale) {
        aptSaleService.registerSale(aptSale);
    }

    // TODO : 검색 조건으로 아파트 전월세 결과 반환
    @PostMapping("/rent-sales")
    public ResponseEntity<?> getRentSales(@RequestBody SearchConditionDTO searchCondition) {
        log.info("Received search condition: {}", searchCondition.toString());
        List<AptRentSaleDTO> rentSalesByConditions = aptRentSaleService.getRentSalesByConditions(searchCondition);
        return new ResponseEntity<>(rentSalesByConditions, HttpStatus.OK);
    }

    // TODO : 검색 조건 완벽 반영
    @PostMapping("rent-sales2")
    public ResponseEntity<?> getRentSales2(@RequestBody SearchConditionDTO searchCondition) {
        log.info("여기 호출합니다", searchCondition.toString());
        List<AptRentSaleDTO> rentSalesByConditions = aptRentSaleService.getRentSalesByConditions2(searchCondition);
        return new ResponseEntity<>(rentSalesByConditions, HttpStatus.OK);
    }

    // TODO : 특정 아파트의 최근 N년간의 전월세 거래 데이터 조회
    @GetMapping("/apartment-rent-sales/{apartmentName}/{years}")
    public ResponseEntity<?> getRentSalesByApartmentAndYears(@PathVariable("apartmentName") String apartmentName, @PathVariable("years") int years) {
        List<AptRentSaleDTO> rentSalesByApartmentAndYears = aptRentSaleService.getRentSalesByApartmentAndYears(apartmentName, years);
        return new ResponseEntity<>(rentSalesByApartmentAndYears, HttpStatus.OK);
    }

    // TODO : 특정 구의 최근 N년간의 전월세 거래 데이터 조회
    @GetMapping("/region-rent-sales/{regionCode}/{years}")
    public ResponseEntity<?> getRentSalesByRegionCodeAndYears(@PathVariable("regionCode") String regionCode, @PathVariable("years") int years) {
        List<AptRentSaleDTO> rentSalesByRegionCodeAndYears = aptRentSaleService.getRentSalesByRegionCodeAndYears(regionCode, years);
        return new ResponseEntity<>(rentSalesByRegionCodeAndYears, HttpStatus.OK);
    }
}
