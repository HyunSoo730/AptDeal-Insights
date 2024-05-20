package com.ssafy.finalproject.aptsale.Controller;


import com.ssafy.finalproject.aptsale.Service.AptSaleService;
import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/apt-sales")
@RequiredArgsConstructor
@Slf4j
public class AptSaleController {

    private final AptSaleService aptSaleService;

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
    public List<AptSaleByDongDTO> getApartmentsByDongCode(@RequestParam("dongCode") String dongCode) {
        log.info("동코드: {}", dongCode);
        return aptSaleService.getApartmentsByDongCode(dongCode);
    }

}
