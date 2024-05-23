package com.ssafy.finalproject.member.controller;


import com.ssafy.finalproject.config.jwt.JwtUtil;
import com.ssafy.finalproject.email.EmailRequestDTO;
import com.ssafy.finalproject.member.dto.LoginDTO;
import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){
        Member member = memberService.login(loginDTO);
        if(member != null){
            log.info("로그인 성공: {}",member);
            return JwtUtil.createToken(member);
        }
        return null;
    }


    @GetMapping("/user")
    public Member getUserInfo(@RequestHeader("Authorization") String token) {
        String email = JwtUtil.getEmail(token);
        log.info("회원정보 불러오기 email:{}",email);
        return memberService.findByEmail(email);
    }

    @PutMapping("/user")
    public ResponseEntity<Member> updateUser(@RequestBody Member updatedMember, @RequestHeader("Authorization") String token) {
        try {
            String email = JwtUtil.getEmail(token);
            Member member = memberService.updateMember(email, updatedMember);
            log.info("선택된 회원: {}",member);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }




    @PostMapping("/join")
    public void join(@RequestBody Member member){
        memberService.join(member);
        log.info("회웝가입 완료!: {}",member);
    }


    @PostMapping("/get-password")
    public ResponseEntity<?> getPassword(@RequestBody EmailRequestDTO emailRequestDTO) {
        try {
            String password = memberService.getPasswordByEmail(emailRequestDTO.getTo());
            if (password != null) {
                return ResponseEntity.ok().body(Map.of("success", true, "password", password));
            } else {
                return ResponseEntity.status(404).body(Map.of("success", false, "message", "User not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("success", false));
        }
    }

}
