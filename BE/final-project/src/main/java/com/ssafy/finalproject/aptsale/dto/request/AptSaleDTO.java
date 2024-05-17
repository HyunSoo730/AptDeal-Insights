package com.ssafy.finalproject.aptsale.dto.request;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class AptSaleDTO implements Serializable {
    private Header header;

    @XmlElement(name = "body")
    private Body body;

    // Getter, Setter

    @Data
    public static class Header implements Serializable{
        private String resultCode;
        private String resultMsg;

        // Getter, Setter
    }

    @Data
    public static class Body implements Serializable{
        @JacksonXmlElementWrapper(localName = "items")
        @JacksonXmlProperty(localName = "item")
        private List<Item> itemList;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        // Getter, Setter
    }

    @XmlRootElement(name = "item")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item implements Serializable{
        @JacksonXmlProperty(localName = "거래금액")
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


        // Getter, Setter
    }
}
