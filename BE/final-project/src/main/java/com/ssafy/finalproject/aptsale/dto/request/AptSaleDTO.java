package com.ssafy.finalproject.aptsale.dto.request;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class AptSaleDTO {

    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name = "body")
    private Body body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlRootElement(name = "header")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header {
        @XmlElement(name = "resultCode")
        private String resultCode;
        @XmlElement(name = "resultMsg")
        private String resultMsg;
    }

    @Data
    @XmlRootElement(name = "body")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {
        @XmlElement(name = "items")
        private Items items;
    }

    @Data
    @XmlRootElement(name = "items")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Items {
        @XmlElement(name = "item")
        private List<Item> itemList;
    }

    @Data
    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @XmlElement(name = "거래금액")
        private String dealAmount;
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

        @XmlTransient
        private String aptCode;
    }
}
