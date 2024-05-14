package com.ssafy.finalproject.like.entity;

import com.ssafy.finalproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.awt.geom.Area;

@Entity
@Table(name = "like_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_area_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "dongCode")
    private Area area;

    // Getters and Setters
}
