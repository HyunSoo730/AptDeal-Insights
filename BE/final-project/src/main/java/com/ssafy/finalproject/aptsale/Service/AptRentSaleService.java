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
}
