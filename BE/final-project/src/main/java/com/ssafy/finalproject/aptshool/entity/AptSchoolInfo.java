package com.ssafy.finalproject.aptshool.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AptSchoolInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aptName;   // * 아파트 이름
    private String roadNameAddress;  // * 도로명 풀 주소
    private String legalDong; // * 법정동
    private Double longitude; // * 경도
    private Double latitude; // * 위도
    private String middleEduNm; // * 중학교 교육청 이름
    private String middleHakgudoNm; // * 중학교 학군
    private String highEduNm; // * 고등학교 교육청 이름
    private String highHakgudoNm; // * 고등학교 학군
    private String lowEduNm;    // * 초등학교 교육청 이름
    private String lowHakgudoNm;   // * 초등학교 학군
    private String aptCode; // * 아파트를 구분할 코드

}