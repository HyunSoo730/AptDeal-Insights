package com.ssafy.finalproject.aptsale.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "response")
@Data
public class AptSaleDTO {
    private Header header;
    private Body body;

    // Getter, Setter

    public static class Header {
        private String resultCode;
        private String resultMsg;

        // Getter, Setter
    }

    public static class Body {
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
