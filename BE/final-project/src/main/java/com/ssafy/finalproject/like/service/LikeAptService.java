package com.ssafy.finalproject.like.service;

import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.aptsale.repository.AptSaleRepository;
import com.ssafy.finalproject.like.entity.LikeApt;
import com.ssafy.finalproject.like.repository.LikeAptRepository;
import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeAptService {

    private final LikeAptRepository likeAptRepository;

    private final AptSaleRepository aptSaleRepository;

    public LikeApt saveLikeApt(LikeApt likeApt) {
        return likeAptRepository.save(likeApt);
    }


    public List<AptSale> findLikedAptsByMemberId(Long memberId) {
        List<LikeApt> byMemberId = likeAptRepository.findByMemberId(memberId);
        
        List<AptSale> aptSaleList=new ArrayList<>();
        
        for (LikeApt likeApt : byMemberId) {
            aptSaleList.addAll(aptSaleRepository.findByAptCode(likeApt.getAptCode()));
        }
        return aptSaleList;
    }

}
