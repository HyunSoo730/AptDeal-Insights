package com.ssafy.finalproject.review.entity;

import com.ssafy.finalproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "apt_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AptReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apt_review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "apartment_id")
    private Long apartmentId;

    @Lob
    private String content;

    private Integer rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
