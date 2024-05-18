package com.ssafy.finalproject.aptsale.Service;


import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepository;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AptSaleService {

    private final AptSaleRepository aptSaleRepository;
    private final AptSaleRepositoryCustom aptSaleRepositoryCustom;

    public List<AptNameAddressDTO> findAptNameAddressByPrefix(String prefix, int page, int size) {
        int offset = (page - 1) * size;
        return aptSaleRepositoryCustom.findAptNamesByPrefix(prefix, offset, size);
    }


//    public List<AptSale> getApartmentsByDongCode(String dongcode) {
//
//        return aptSaleRepository.findByDongCodeGroupByAptCode(dongcode);
//    }
}
