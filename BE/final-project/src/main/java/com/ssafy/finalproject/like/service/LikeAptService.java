package com.ssafy.finalproject.like.service;

import com.ssafy.finalproject.like.entity.LikeApt;
import com.ssafy.finalproject.like.repository.LikeAptRepository;
import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeAptService {

    @Autowired
    private LikeAptRepository likeAptRepository;


    public LikeApt saveLikeApt(LikeApt likeApt) {
        return likeAptRepository.save(likeApt);
    }


}
