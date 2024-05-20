package com.ssafy.finalproject.aptsale.dto.response;

import com.ssafy.finalproject.aptsale.entity.AptSale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AptSaleDetails {

    private String aptName;
    private Integer dealAmount;
    private Integer constructionYear;
    private String roadNameAddress;
    private String dong;
    private String legalDong;
    private Integer dealYear;
    private Integer dealMonth;
    private Integer dealDay;
    private Double exclusiveArea;
    private Integer floor;
    private String aptCode;
    private Double latitude;
    private Double longitude;
    private String sidoName;

    // ! 객체지향적 설계를 위해 DTO에서 변환 로직의 책임을 지도록.
    // ! 각 객체는 자신의 데이터를 처리할 책임을 지는 것으로.
    public static AptSaleDetails fromEntity(AptSale aptSale) {
        return AptSaleDetails.builder()
                .aptName(aptSale.getAptName())
                .dealAmount(aptSale.getDealAmount())
                .constructionYear(aptSale.getConstructionYear())
                .roadNameAddress(aptSale.getRoadNameAddress())
                .dong(aptSale.getDong())
                .legalDong(aptSale.getLegalDong())
                .dealYear(aptSale.getDealYear())
                .dealMonth(aptSale.getDealMonth())
                .dealDay(aptSale.getDealDay())
                .exclusiveArea(aptSale.getExclusiveArea())
                .floor(aptSale.getFloor())
                .aptCode(aptSale.getAptCode())
                .latitude(aptSale.getLatitude())
                .longitude(aptSale.getLongitude())
                .sidoName(aptSale.getSidoName())
                .build();
    }
}
