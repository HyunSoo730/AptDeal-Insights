package com.ssafy.finalproject.review.entity;

import com.ssafy.finalproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agent_review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "agent_id")
    private Long agentId;

    @Lob
    private String content;

    private Integer rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
}