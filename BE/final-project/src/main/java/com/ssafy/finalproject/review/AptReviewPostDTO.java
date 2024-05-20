package com.ssafy.finalproject.review;

import com.ssafy.finalproject.member.entity.Member;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class AptReviewPostDTO {
    private Long memberId;

    private String apartmentCode;

    private String content;

    private Integer rating;
}
