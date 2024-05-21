package com.ssafy.finalproject.aptsale.Service;


import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
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

    public List<AptSaleByDongDTO> getApartmentsByDongCode(String dongcode) {

        List<AptSaleByDongDTO> byDongcodeGroupByAptCode = aptSaleRepository.findByDongcodeGroupByAptCode(dongcode);
        for (AptSaleByDongDTO aptSaleByDongDTO : byDongcodeGroupByAptCode) {
            System.out.println("aptSaleByDongDTO = " + aptSaleByDongDTO);
        }
        return byDongcodeGroupByAptCode;
    }


    public void registerSale(AptSale aptSale){
        AptSale realOne=aptSaleRepository.findAptSaleByAptCodeOrderByDealYearDescDealMonthDescDealDayDesc(aptSale.getAptCode()).get(0);
        aptSale.setConstructionYear(realOne.getConstructionYear());
        aptSale.setRoadName(realOne.getRoadName());
        aptSale.setRoadNameBonbun(realOne.getRoadNameBonbun());
        aptSale.setDong(realOne.getDong());
        aptSale.setLegalDong(realOne.getLegalDong());
        aptSale.setFloor(realOne.getFloor());
        aptSale.setSidoName(realOne.getSidoName());
        aptSaleRepository.save(aptSale);
    }
}
