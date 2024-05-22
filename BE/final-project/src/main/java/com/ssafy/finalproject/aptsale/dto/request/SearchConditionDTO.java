package com.ssafy.finalproject.aptsale.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SearchConditionDTO {
    private String regionCode;
    private String legalDong;
    private String minDeposit;
    private String maxDeposit;
    private String minMonthlyRent;
    private String maxMonthlyRent;
    private Double minExclusiveArea;
    private Double maxExclusiveArea;
    private LocalDate startDate;
    private LocalDate endDate;
    private int offset;
    private int limit;
}
