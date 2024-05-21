package com.ssafy.finalproject;

import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    @PostMapping("/sample")
    public String sample(@ModelAttribute SampleDTO addr){
        System.out.println(addr);
        return "ok";
    }

    @GetMapping("/sample")
    public String getSampleAddress(@RequestParam String confmKey, @RequestParam String returnUrl) {
        // 요청 파라미터 확인
        System.out.println("confmKey: " + confmKey);
        System.out.println("returnUrl: " + returnUrl);
        return "주소 팝업 요청이 수신되었습니다.";
    }
}
