package com.ssafy.finalproject.aptshool.controller;

import com.ssafy.finalproject.aptshool.dto.AptSchoolInfoResDTO;
import com.ssafy.finalproject.aptshool.entity.AptSchoolInfo;
import com.ssafy.finalproject.aptshool.service.AptSchoolInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api/apt-school")
public class AptSchoolInfoController {

    private final AptSchoolInfoService service;

    @GetMapping("/{aptCode}/{aptName}")
    public AptSchoolInfoResDTO getAptSchoolInfo(@PathVariable("aptCode") String aptCode, @PathVariable("aptName") String aptName) {
        return service.findAptSchoolInfo(aptCode, aptName)
                .orElseThrow(() -> new RuntimeException("해당하는 아파트가 없습니다."));
    }
}