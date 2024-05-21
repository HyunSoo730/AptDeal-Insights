package com.ssafy.finalproject.aptshool.service;

import com.ssafy.finalproject.aptshool.dto.AptSchoolInfoResDTO;
import com.ssafy.finalproject.aptshool.entity.AptSchoolInfo;
import com.ssafy.finalproject.aptshool.repository.AptSchoolInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AptSchoolInfoService {

    private final AptSchoolInfoRepository repository;

    public Optional<AptSchoolInfoResDTO> findAptSchoolInfo(String aptCode, String aptName) {
        System.out.println("aptCode: " + aptCode);
        System.out.println("aptName: " + aptName);
        return repository.findByAptCodeStartingWithAndAptNameStartingWith(aptCode.substring(0, 20), aptName.substring(0, 2))
                .map(aptSchoolInfo -> new AptSchoolInfoResDTO(
                        aptSchoolInfo.getMiddleEduNm(),
                        aptSchoolInfo.getMiddleHakgudoNm(),
                        aptSchoolInfo.getHighEduNm(),
                        aptSchoolInfo.getHighHakgudoNm(),
                        aptSchoolInfo.getLowEduNm(),
                        aptSchoolInfo.getLowHakgudoNm()
                ));
    }

}
