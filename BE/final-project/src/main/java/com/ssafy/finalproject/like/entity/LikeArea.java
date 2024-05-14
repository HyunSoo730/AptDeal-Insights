package com.ssafy.finalproject.like.entity;

import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.model.Area;
import jakarta.persistence.*;
import lombok.*;


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
    @JoinColumn(name = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "dongCode")
    private Area area;

}
