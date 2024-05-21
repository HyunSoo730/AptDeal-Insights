package com.ssafy.finalproject.aptsale.Service;


import com.ssafy.finalproject.aptsale.dto.request.AptSaleByDongDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptTransactionResDTO;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.aptsale.dto.request.AptNameAddressDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptSaleDetails;
import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepository;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    // TODO : 특정 아파트의 최근 N년간 거래 정보 조회
    public List<AptTransactionResDTO> findRecentSalesByAptCode(String aptCode, int recentYears) {
        return aptSaleRepositoryCustom.findSalesByAptCode(aptCode, recentYears)
                .stream()
                .map(sale -> new AptTransactionResDTO(
                        LocalDate.of(sale.getDealYear(), sale.getDealMonth(), sale.getDealDay()),
                        sale.getDealAmount()
                ))
                .collect(Collectors.toList());
    }

    // TODO : 특정 구의 최근 N년간 거래 정보 조회
    public List<AptTransactionResDTO> findRecentSalesByDongcode(String dongcode, int recentYears) {
        String dongcodePrefix = dongcode.substring(0, 5);  // ! 앞 5자리만. 사용
        return aptSaleRepositoryCustom.findSalesByDongcode(dongcode, recentYears)
                .stream()
                .map(sale -> new AptTransactionResDTO(
                        LocalDate.of(sale.getDealYear(), sale.getDealMonth(), sale.getDealDay()),
                        sale.getDealAmount()
                ))
                .collect(Collectors.toList());
    }
}
