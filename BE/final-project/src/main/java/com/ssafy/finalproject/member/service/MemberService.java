package com.ssafy.finalproject.member.service;


import com.ssafy.finalproject.config.jwt.JwtUtil;
import com.ssafy.finalproject.member.dto.LoginDTO;
import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member){

        memberRepository.save(member);

    }


    public Member login(LoginDTO loginDTO){
        Member member = memberRepository.findByEmail(loginDTO.getEmail());
        if(member != null && member.getPassword().equals(loginDTO.getPassword())){
            return member;
        }
        return null;
    }


    public Member updateMember(String email, Member updatedMember) {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            if (updatedMember.getPassword() != null && !updatedMember.getPassword().isEmpty()) {
                member.setPassword(updatedMember.getPassword());
            }
            member.setNickname(updatedMember.getNickname());
            member.setUpdatedAt(LocalDateTime.now());
            return memberRepository.save(member);
        }
        throw new RuntimeException("Member not found");
    }



    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


    public String getPasswordByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        return member != null ? member.getPassword() : null;
    }
}
