package com.ssafy.finalproject.aptsale.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.finalproject.like.entity.LikeApt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
public class AptSaleByDongDTO {
    private String aptName;
    private Integer dealAmount;
    private String roadName;
    private Integer constructionYear;
    private String aptCode;
    private Double latitude;
    private Double longitude;

    @QueryProjection
    public AptSaleByDongDTO(String aptName, Integer dealAmount, String roadName, Integer constructionYear, String aptCode, Double latitude, Double longitude) {
        this.aptName = aptName;
        this.dealAmount = dealAmount;
        this.roadName = roadName;
        this.constructionYear = constructionYear;
        this.aptCode = aptCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
