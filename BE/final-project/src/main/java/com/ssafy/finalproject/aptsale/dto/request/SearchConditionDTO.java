package com.ssafy.finalproject.aptsale.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchConditionDTO {
    private String regionCode;
    private String legalDong;
    private Integer minDeposit;
    private Integer maxDeposit;
    private Integer minMonthlyRent;
    private Integer maxMonthlyRent;
    private Double minExclusiveArea;
    private Double maxExclusiveArea;
    private LocalDate startDate;
    private LocalDate endDate;
    private int offset = 0;  // ! 기본값 설정
    private int limit = 20; // ! 기본값 설정
}
