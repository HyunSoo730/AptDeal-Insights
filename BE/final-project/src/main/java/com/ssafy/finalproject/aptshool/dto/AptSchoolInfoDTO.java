package com.ssafy.finalproject.aptshool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AptSchoolInfoDTO {
    private Long id;
    private String aptName;
    private String roadNameAddress;
    private String legalDong;
    private Double longitude;
    private Double latitude;
    private String middleEduNm;
    private String middleHakgudoNm;
    private String highEduNm;
    private String highHakgudoNm;
    private String lowEduNm;
    private String lowHakgudoNm;
}