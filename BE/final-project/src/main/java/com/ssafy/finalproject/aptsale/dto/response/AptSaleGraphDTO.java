package com.ssafy.finalproject.aptsale.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AptSaleGraphDTO {

    private String aptCode;
    private String aptName;
    private Integer dealAmount;
    private Integer dealYear;
    private Integer dealMonth;
    private Integer dealDay;
    private Double exclusiveArea;
    private String dongcode; // 구 구분을 위해 필요

    @QueryProjection
    public AptSaleGraphDTO(String aptCode, String aptName, Integer dealAmount, Integer dealYear, Integer dealMonth, Integer dealDay, Double exclusiveArea, String dongcode) {
        this.aptCode = aptCode;
        this.aptName = aptName;
        this.dealAmount = dealAmount;
        this.dealYear = dealYear;
        this.dealMonth = dealMonth;
        this.dealDay = dealDay;
        this.exclusiveArea = exclusiveArea;
        this.dongcode = dongcode;
    }

}
