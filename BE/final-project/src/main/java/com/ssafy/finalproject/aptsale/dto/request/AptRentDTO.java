package com.ssafy.finalproject.aptsale.dto.request;

import com.ssafy.finalproject.aptsale.entity.AptRentSale;
import lombok.*;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class AptRentDTO {

    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name = "body")
    private Body body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlRootElement(name = "header")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header {
        @XmlElement(name = "resultCode")
        private String resultCode;
        @XmlElement(name = "resultMsg")
        private String resultMsg;
    }

    @Data
    @XmlRootElement(name = "body")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        @XmlElement(name = "items")
        private Items items;
    }

    @Data
    @XmlRootElement(name = "items")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Items {
        @XmlElement(name = "item")
        private List<Item> itemList;
    }

    @Data
    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @XmlElement(name = "갱신요구권사용")
        private String renewalRightUsage;
        @XmlElement(name = "건축년도")
        private Integer constructionYear;
        @XmlElement(name = "계약구분")
        private String contractType;
        @XmlElement(name = "계약기간")
        private String contractPeriod;
        @XmlElement(name = "년")
        private Integer year;
        @XmlElement(name = "법정동")
        private String legalDong;
        @XmlElement(name = "보증금액")
        private String depositAmount;
        @XmlElement(name = "아파트")
        private String apartmentName;
        @XmlElement(name = "월")
        private Integer month;
        @XmlElement(name = "월세금액")
        private String monthlyRent;
        @XmlElement(name = "일")
        private Integer day;
        @XmlElement(name = "전용면적")
        private Double exclusiveArea;
        @XmlElement(name = "종전계약보증금")
        private String previousContractDeposit;
        @XmlElement(name = "종전계약월세")
        private String previousContractRent;
        @XmlElement(name = "지번")
        private String lotNumber;
        @XmlElement(name = "지역코드")
        private String regionCode;
        @XmlElement(name = "층")
        private Integer floor;

        // ! 엔티티로 변환하는 메서드 추가
        public AptRentSale toEntity() {
            return AptRentSale.builder()
                    .renewalRightUsage(this.renewalRightUsage)
                    .constructionYear(this.constructionYear)
                    .contractType(this.contractType)
                    .contractPeriod(this.contractPeriod)
                    .year(this.year)
                    .legalDong(this.legalDong)
                    .depositAmount(parsePrice(this.depositAmount))
                    .apartmentName(this.apartmentName)
                    .month(this.month)
                    .monthlyRent(parsePrice(this.monthlyRent))
                    .day(this.day)
                    .exclusiveArea(this.exclusiveArea)
                    .previousContractDeposit(parsePrice(this.previousContractDeposit))
                    .previousContractRent(parsePrice(this.previousContractRent))
                    .lotNumber(this.lotNumber)
                    .regionCode(this.regionCode)
                    .floor(this.floor)
                    .build();
        }

        private int parsePrice(String price) {
            if (price == null || price.trim().isEmpty()) {
                return 0;
            }
            return Integer.parseInt(price.replaceAll(",", ""));
        }

    }


}

