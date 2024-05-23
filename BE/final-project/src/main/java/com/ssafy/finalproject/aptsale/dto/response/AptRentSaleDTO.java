package com.ssafy.finalproject.aptsale.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AptRentSaleDTO {

    private String renewalRightUsage; // * 갱신 요구권 사용
    private int constructionYear;  // * 건축 년도
    private String contractType; // * 계약 구분
    private String contractPeriod;  // * 계약 기간
    private int year;  // * 거래 년도
    private String legalDong;  // * 법정동
    private int depositAmount;  // * 보증금액
    private String apartmentName;  // * 아파트이름
    private int month;  // * 거래 월
    private int monthlyRent; // * 월세
    private int day;   // * 거래 일
    private double exclusiveArea; // * 전용 면적
    private int previousContractDeposit;  // * 이전 거래 계약 보증금
    private int previousContractRent;   // * 이전 거래 계약 월세
    private String lotNumber;   // * 지번
    private String regionCode;  // * 지역 코드
    private int floor;  // * 층

    @QueryProjection
    public AptRentSaleDTO(int constructionYear, String contractType, String contractPeriod, int year,
                          String legalDong, int depositAmount, String apartmentName, int month,
                          int monthlyRent, int day, double exclusiveArea, int previousContractDeposit,
                          int previousContractRent, String regionCode, int floor) {
        this.constructionYear = constructionYear;
        this.contractType = contractType;
        this.contractPeriod = contractPeriod;
        this.year = year;
        this.legalDong = legalDong;
        this.depositAmount = depositAmount;
        this.apartmentName = apartmentName;
        this.month = month;
        this.monthlyRent = monthlyRent;
        this.day = day;
        this.exclusiveArea = exclusiveArea;
        this.previousContractDeposit = previousContractDeposit;
        this.previousContractRent = previousContractRent;
        this.regionCode = regionCode;
        this.floor = floor;
    }

}
