package com.ssafy.finalproject.aptsale.entity;

import com.ssafy.finalproject.like.entity.LikeApt;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "apt_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AptSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aptSale_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "like_apt_id")
    private LikeApt likeApt;

    @Column(name = "apt_name")
    private String aptName;

    @Column(name = "deal_amount")
    private Integer dealAmount;

    @Column(name = "construction_year")
    private Integer constructionYear;

    @Column(name = "road_name")
    private String roadName;

    @Column(name = "road_name_bonbun")
    private String roadNameBonbun;

    @Column(name = "road_name_bubun")
    private String roadNameBubun;

    @Column(name = "road_name_address")
    private String roadNameAddress;

    private String dong;

    @Column(name = "legal_dong")
    private String legalDong;

    private String dongcode;

    @Column(name = "deal_year")
    private Integer dealYear;

    @Column(name = "deal_month")
    private Integer dealMonth;

    @Column(name = "deal_day")
    private Integer dealDay;

    @Column(name = "exclusive_area")
    private Double exclusiveArea;

    private Integer floor;

    // 20자리로 조합하기
    @Column(name = "apt_code")
    private String aptCode;
 
    // (x,y) 좌표
    private Double latitude;
    private Double longitude;

    @Column(name="sido_name") // 넣어주기
    private String sidoName;

}