package com.ssafy.finalproject.aptsale.dto.request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class AptSaleDTO {

    @XmlElement(name = "body")
    private Body body;

    private String aptCode; // ! processor에서 가공한 코드

    public void setAptCode() {
        Item item = body.getItems().getItemList().get(0);
        this.aptCode = item.getLegalDongSigunguCode() + item.getLegalDongEubmyeondongCode()
                + item.getRoadNameBonbun() + item.getRoadNameBubun();
    }

    @XmlRootElement(name = "body")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Body {
        @XmlElement(name = "items")
        private Items items;
    }

    @XmlRootElement(name = "items")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Items {
        @XmlElement(name = "item")
        private List<Item> itemList;
    }

    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Item {
        @XmlElement(name = "거래금액")
        private Integer dealAmount;

        @XmlElement(name = "건축년도")
        private Integer constructionYear;

        @XmlElement(name = "도로명")
        private String roadName;

        @XmlElement(name = "도로명건물본번호코드")
        private String roadNameBonbun;

        @XmlElement(name = "도로명건물부번호코드")
        private String roadNameBubun;

        @XmlElement(name = "도로명시군구코드")
        private String roadNameSigunguCode;

        @XmlElement(name = "동")
        private String dong;

        @XmlElement(name = "법정동")
        private String legalDong;

        @XmlElement(name = "법정동시군구코드")
        private String legalDongSigunguCode;

        @XmlElement(name = "법정동읍면동코드")
        private String legalDongEubmyeondongCode;

        @XmlElement(name = "아파트")
        private String aptName;

        @XmlElement(name = "년")
        private Integer dealYear;

        @XmlElement(name = "월")
        private Integer dealMonth;

        @XmlElement(name = "일")
        private Integer dealDay;

        @XmlElement(name = "전용면적")
        private Double exclusiveArea;

        @XmlElement(name = "층")
        private Integer floor;
    }
}
