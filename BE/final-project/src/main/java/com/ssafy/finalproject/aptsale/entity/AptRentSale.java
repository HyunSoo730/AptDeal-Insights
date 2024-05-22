package com.ssafy.finalproject.aptsale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "apt_rent_sale")
public class AptRentSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String renewalRightUsage; // * 갱신 요구권 사용
    private int constructionYear;  // * 건축 년도
    private String contractType; // * 계약 구분
    private String contractPeriod;  // * 계약 기간
    private int year;  // * 거래 년도
    private String legalDong;  // * 법정동
    private String depositAmount;  // * 보증금액
    private String apartmentName;  // * 아파트이름
    private int month;  // * 거래 월
    private String monthlyRent; // * 월세
    private int day;   // * 거래 일
    private double exclusiveArea; // * 전용 면적
    private String previousContractDeposit;  // * 이전 거래 계약 보증금
    private String previousContractRent;   // * 이전 거래 계약 월세
    private String lotNumber;   // * 지번
    private String regionCode;  // * 지역 코드
    private int floor;  // * 층

}
