package com.ssafy.finalproject.member.controller;


import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/login")
    public void login(){

    }

    @PostMapping("/join")
    public void join(@RequestBody Member member){
        memberService.join(member);
        log.info("회웝가입 완료!: {}",member);
    }

}
