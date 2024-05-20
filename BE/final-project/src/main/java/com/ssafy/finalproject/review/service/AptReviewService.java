package com.ssafy.finalproject.review.service;


import com.ssafy.finalproject.member.repository.MemberRepository;
import com.ssafy.finalproject.review.AptReviewPostDTO;
import com.ssafy.finalproject.review.entity.AptReview;
import com.ssafy.finalproject.review.repository.AptReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AptReviewService {

    private final AptReviewRepository aptReviewRepository;

    private final MemberRepository memberRepository;


    public void saveReview(AptReviewPostDTO aptReviewPostDTO) {
        AptReview aptReview = AptReview.builder()
                .member(memberRepository.findById(aptReviewPostDTO.getMemberId()).get())
                .apartmentId(aptReviewPostDTO.getApartmentCode())
                .content(aptReviewPostDTO.getContent())
                .rating(aptReviewPostDTO.getRating())
                .build();

        aptReviewRepository.save(aptReview);
    }

    public List<AptReview> reviewList(String apartmentId) {
        return aptReviewRepository.findByApartmentId(apartmentId);
    }
}
