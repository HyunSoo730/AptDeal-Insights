package com.ssafy.finalproject.review.controller;

import com.ssafy.finalproject.member.service.MemberService;
import com.ssafy.finalproject.review.AptReviewPostDTO;
import com.ssafy.finalproject.review.entity.AptReview;
import com.ssafy.finalproject.review.service.AptReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/apt-review")
public class AptReviewController {


    private final AptReviewService aptReviewService;

    private final MemberService memberService;

    @PostMapping("/post")
    public void post(@RequestBody AptReviewPostDTO aptReviewPostDTO){
        aptReviewService.saveReview(aptReviewPostDTO);
    }

    @GetMapping("/list")
    public List<AptReview> list(@RequestParam String apartmentCode){
        System.out.println(apartmentCode);
        return aptReviewService.reviewList(apartmentCode);
    }
}
