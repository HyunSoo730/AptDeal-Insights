package com.ssafy.finalproject.aptsale.Service;


import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptSaleDetails;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepository;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AptSaleService {

    private final AptSaleRepository aptSaleRepository;
    private final AptSaleRepositoryCustom aptSaleRepositoryCustom;

    public List<AptNameAddressDTO> findAptNameAddressByPrefix(String prefix, int page, int size) {
        int offset = (page - 1) * size;
        return aptSaleRepositoryCustom.findAptNamesByPrefix(prefix, offset, size);
    }

    public List<AptSaleDetails> findAptSaleDetails(String aptCode) {
        List<AptSale> aptSales = aptSaleRepository.findAptSaleByAptCodeOrderByDealYearDescDealMonthDescDealDayDesc(aptCode);
        return aptSales.stream()
                .map(AptSaleDetails::fromEntity)
                .collect(Collectors.toList());
    }


//    public List<AptSale> getApartmentsByDongCode(String dongcode) {
//
//        return aptSaleRepository.findByDongCodeGroupByAptCode(dongcode);
//    }
}
