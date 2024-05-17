package com.ssafy.finalproject.aptsale.Controller;


import com.ssafy.finalproject.aptsale.Service.AptSaleService;
import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AptSaleController {

    private final AptSaleService aptSaleService;

    @GetMapping("/apartments")
    public List<AptSaleByDongDTO> getApartmentsByDongCode(@RequestParam("dongcode") String dongcode) {
        return aptSaleService.getApartmentsByDongCode(dongcode);
    }

}
