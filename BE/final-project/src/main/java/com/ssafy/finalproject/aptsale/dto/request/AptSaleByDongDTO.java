package com.ssafy.finalproject.aptsale.dto.request;

import com.ssafy.finalproject.like.entity.LikeApt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptSaleByDongDTO {
    private String aptName;
    private Integer dealAmount;
    private String roadName;
    private Integer constructionYear;
    private String aptCode;
    private Double latitude;
    private Double longitude;
}
