package com.ssafy.finalproject.like.entity;

import com.ssafy.finalproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "like_apt")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeApt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_apt_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "apt_code")
    private String aptCode;

    // Getters and Setters
}