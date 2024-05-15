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

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;

        // Getter, Setter
    }

    @Data
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        @JacksonXmlProperty(localName = "거래금액")
        private String dealAmount;

        @JacksonXmlProperty(localName = "거래유형")
        private String dealType;

        @JacksonXmlProperty(localName = "건축년도")
        private Integer constructionYear;

        @JacksonXmlProperty(localName = "년")
        private Integer year;

        @JacksonXmlProperty(localName = "도로명")
        private String roadName;

        @JacksonXmlProperty(localName = "도로명건물본번호코드")
        private String roadNameBuildingMainCode;

        @JacksonXmlProperty(localName = "도로명건물부번호코드")
        private String roadNameBuildingSubCode;

        @JacksonXmlProperty(localName = "도로명시군구코드")
        private String roadNameSigunguCode;

        @JacksonXmlProperty(localName = "도로명일련번호코드")
        private String roadNameSerialCode;

        @JacksonXmlProperty(localName = "도로명지상지하코드")
        private String roadNameGroundCode;

        @JacksonXmlProperty(localName = "도로명코드")
        private String roadNameCode;

        @JacksonXmlProperty(localName = "동")
        private String dong;

        @JacksonXmlProperty(localName = "법정동")
        private String legalDong;

        @JacksonXmlProperty(localName = "법정동본번코드")
        private String legalDongMainCode;

        @JacksonXmlProperty(localName = "법정동부번코드")
        private String legalDongSubCode;

        @JacksonXmlProperty(localName = "법정동시군구코드")
        private String legalDongSigunguCode;

        @JacksonXmlProperty(localName = "법정동읍면동코드")
        private String legalDongEupmyeondongCode;

        @JacksonXmlProperty(localName = "법정동지번코드")
        private String legalDongJibunCode;

        @JacksonXmlProperty(localName = "아파트")
        private String apartment;

        @JacksonXmlProperty(localName = "월")
        private Integer month;

        @JacksonXmlProperty(localName = "일")
        private Integer day;

        @JacksonXmlProperty(localName = "일련번호")
        private String serialNumber;

        @JacksonXmlProperty(localName = "전용면적")
        private Double exclusiveArea;

        @JacksonXmlProperty(localName = "지번")
        private String jibun;

        @JacksonXmlProperty(localName = "지역코드")
        private String regionCode;

        @JacksonXmlProperty(localName = "층")
        private Integer floor;

        // Getter, Setter
    }
}
