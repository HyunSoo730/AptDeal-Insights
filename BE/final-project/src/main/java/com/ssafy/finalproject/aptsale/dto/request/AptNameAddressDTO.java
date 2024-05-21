package com.ssafy.finalproject.aptsale.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AptNameAddressDTO {

    private String aptCode; // ! 아파트 구분을 위해
    private String aptName;
    private String roadNameAddress;
    private Double latitude; // ! 위도(x)
    private Double longitude; // ! 경도(y)
    private String dongcode; // 동코드

    @QueryProjection
    public AptNameAddressDTO(String aptCode, String aptName, String roadNameAddress, Double latitude, Double longitude, String dongcode) {
        this.aptCode = aptCode;
        this.aptName = aptName;
        this.roadNameAddress = roadNameAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dongcode = dongcode;
    }
}
