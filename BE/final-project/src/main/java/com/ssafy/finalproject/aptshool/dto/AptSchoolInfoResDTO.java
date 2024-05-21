package com.ssafy.finalproject.aptshool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptSchoolInfoResDTO {
    private String middleEduNm;
    private String middleHakgudoNm;
    private String highEduNm;
    private String highHakgudoNm;
    private String lowEduNm;
    private String lowHakgudoNm;
}
