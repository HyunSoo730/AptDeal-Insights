package com.ssafy.finalproject.aptsale.Service;


import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AptSaleService {

    private final AptSaleRepository aptSaleRepository;


    public List<AptSaleByDongDTO> getApartmentsByDongCode(String dongcode) {

        return aptSaleRepository.findByDongcodeGroupByAptCode(dongcode);
    }
}
