package com.ssafy.finalproject.member.service;


import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member){

        memberRepository.save(member);

    }

}
