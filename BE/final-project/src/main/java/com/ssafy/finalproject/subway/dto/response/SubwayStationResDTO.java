package com.ssafy.finalproject.subway.dto.response;

import com.ssafy.finalproject.subway.entity.SubwayStation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubwayStationResDTO {
    private String stationName;
    private String lineNum;
    private double latitude;
    private double longitude;
    private double distance;
    private String walkingTime;

    public static SubwayStationResDTO fromEntity(SubwayStation station, double distance) {
        return SubwayStationResDTO.builder()
                .stationName(station.getStationName())
                .lineNum(station.getLineNum())
                .latitude(station.getLat())
                .longitude(station.getLng())
                .distance(distance)
                .walkingTime(calculateWalkingTime(distance))
                .build();
    }

    // TODO : 5km/h로 걷는다고 가정하고 시간 계산
    private static String calculateWalkingTime(double distance) {
        double walkingSpeed = 5.0; // km/h
        double time = distance / walkingSpeed * 60; // minutes
        int hours = (int) time / 60;
        int minutes = (int) time % 60;
        if (hours > 0) {
            return hours + "시간 " + minutes + "분";
        } else {
            return minutes + "분";
        }
    }
}


