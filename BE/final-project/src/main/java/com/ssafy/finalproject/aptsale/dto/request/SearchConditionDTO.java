package com.ssafy.finalproject.aptsale.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

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
    private Boolean isCharter; // ! 전세인지
    private List<Integer> selectedPyeongRanges; // ! 선택한 평수 리스트
    private int offset = 0;  // ! 기본값 설정
    private int limit = 20; // ! 기본값 설정
}
