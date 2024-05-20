package com.ssafy.finalproject.subway.controller;

import com.ssafy.finalproject.like.dto.DTO;
import com.ssafy.finalproject.subway.dto.request.SubwayStationDTO;
import com.ssafy.finalproject.subway.dto.response.SubwayStationResDTO;
import com.ssafy.finalproject.subway.entity.SubwayStation;
import com.ssafy.finalproject.subway.service.SubwayStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subway-stations")
@Slf4j
public class SubwayStationController {

    private final WebClient webClient;
    private final SubwayStationService subwayStationService;

    // TODO : OPEN API로부터 수도권 모든 지하철역 정보(역 이름, 몇 호선, 위도, 경도) 가져와서 DB에 저장(최초 1회)
    @GetMapping("/import")
    public ResponseEntity<?> importSubwayStations() {
        String SEOUL_SUBWAY_API_KEY = "3afccb7a-cc8e-4e58-9bbf-4bb3e58278ff";
        String url = "https://t-data.seoul.go.kr/apig/apiman-gateway/tapi/TaimsKsccDvSubwayStationGeom/1.0?apiKey=" + SEOUL_SUBWAY_API_KEY;
        List<SubwayStationDTO> stations = webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(SubwayStationDTO.class)
                .collectList()
                .block();

        if (stations != null) {
            log.info("stations: {}", stations);
            subwayStationService.saveSubwayStations(stations);
            return new ResponseEntity<>(stations, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import data.");
        }
    }

    // TODO : 현재 아파트로부터 가장 가까운 지하철역 3개 반환
    @GetMapping("/closest")
    public ResponseEntity<List<SubwayStationResDTO>> getClosestSubwayStations(
            @RequestParam("aptLat") double aptLat,
            @RequestParam("aptLng") double aptLng) {
        List<SubwayStationResDTO> closestStations = subwayStationService.findClosestSubwayStations(aptLat, aptLng);
        return new ResponseEntity<>(closestStations, HttpStatus.OK);
    }
}
