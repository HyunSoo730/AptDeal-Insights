package com.ssafy.finalproject.subway.service;

import com.ssafy.finalproject.subway.dto.request.SubwayStationDTO;
import com.ssafy.finalproject.subway.dto.response.SubwayStationResDTO;
import com.ssafy.finalproject.subway.entity.SubwayStation;
import com.ssafy.finalproject.subway.repository.SubwayStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubwayStationService {

    private final SubwayStationRepository subwayStationRepository;

    // TODO : 수도권 지하철역 정보 저장
    public void saveSubwayStations(List<SubwayStationDTO> subwayStationDTOs) {
        List<SubwayStation> subwayStations = subwayStationDTOs.stream()
                .map(SubwayStationDTO::toEntity)
                .collect(Collectors.toList());
        subwayStationRepository.saveAll(subwayStations);
    }

    // TODO : 하버사인 공식을 이용하여 두 지점 간의 거리 계산
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; //!  지구의 반경 (킬로미터)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    // TODO : 가장 가까운 지하철역 3개 찾기
    public List<SubwayStationResDTO> findClosestSubwayStations(double aptLat, double aptLng) {
        List<SubwayStation> allStations = subwayStationRepository.findAll();
        return allStations.stream()
                .map(station -> new AbstractMap.SimpleEntry<>(station, calculateDistance(aptLat, aptLng, station.getLat(), station.getLng())))
                .sorted(Map.Entry.comparingByValue())
                .limit(4)
                .map(entry -> SubwayStationResDTO.fromEntity(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
