package com.ssafy.finalproject.subway.dto.request;

import com.ssafy.finalproject.subway.entity.SubwayStation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubwayStationDTO {

    private String stnKrNm;  // 역 한글 명칭
    private String lineNm;   // 호선 명칭
    private double convY;    // 위도
    private double convX;    // 경도

    public SubwayStation toEntity() {
        return SubwayStation.builder()
                .stationName(this.stnKrNm)
                .lineNum(this.lineNm)
                .lat(this.convY)
                .lng(this.convX)
                .build();
    }

}
