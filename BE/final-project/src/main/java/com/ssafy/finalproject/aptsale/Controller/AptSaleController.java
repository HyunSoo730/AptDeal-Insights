package com.ssafy.finalproject.aptsale.Controller;


import com.ssafy.finalproject.aptsale.Service.AptSaleService;
import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import lombok.RequiredArgsConstructor;
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

//    @GetMapping("/apartments")
//    public List<AptSale> getApartmentsByDongCode(@RequestParam("dongcode") String dongcode) {
//        return aptSaleService.getApartmentsByDongCode(dongcode);
//    }
}
