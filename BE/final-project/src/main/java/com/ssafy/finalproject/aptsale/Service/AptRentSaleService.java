package com.ssafy.finalproject.aptsale.Service;

import com.ssafy.finalproject.aptsale.dto.request.SearchConditionDTO;
import com.ssafy.finalproject.aptsale.dto.response.AptRentSaleDTO;
import com.ssafy.finalproject.aptsale.repository.AptRentSaleRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AptRentSaleService {

    private final AptRentSaleRepositoryCustom aptRentSaleRepositoryCustom;

    public List<AptRentSaleDTO> getRentSalesByConditions(SearchConditionDTO searchCondition) {
        return aptRentSaleRepositoryCustom.findRentSalesByConditions(searchCondition);
    }

    public List<AptRentSaleDTO> getRentSalesByConditions2(SearchConditionDTO searchCondition) {
        return aptRentSaleRepositoryCustom.findRentSalesByConditions2(searchCondition);
    }

    // ! 특정 아파트 최근 N년 거래 데이터
    public List<AptRentSaleDTO> getRentSalesByApartmentAndYears(String apartmentName, int years) {
        return aptRentSaleRepositoryCustom.findRentSalesByApartmentAndYears(apartmentName, years);
    }

    // ! 특정 구 최근 N년 거래 데이터
    public List<AptRentSaleDTO> getRentSalesByRegionCodeAndYears(String regionCode, int years) {
        return aptRentSaleRepositoryCustom.findRentSalesByRegionCodeAndYears(regionCode, years);
    }
}

