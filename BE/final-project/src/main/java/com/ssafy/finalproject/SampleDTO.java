package com.ssafy.finalproject;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SampleDTO {
    private String roadFullAddr;  // 전체 영문 도로명주소
    private String roadAddr;      // 영문 도로명주소
    private String jibunAddr;     // 영문 지번주소
    private String zipNo;         // 우편번호
    private String addrDetail;    // 고객 입력 상세 주소 (선택사항)
    private String admCd;         // 행정구역코드
    private String rnMgtSn;       // 도로명코드
    private String bdKdcd;        // 공동주택여부 (1: 공동주택, 0: 비공동주택)
    private String siNm;          // 영문 시도명
    private String sggNm;         // 영문 시군구명
    private String emdNm;         // 영문 읍면동명
    private String liNm;          // 영문 법정리명 (선택사항)
    private String rn;            // 영문 도로명
    private String udrtYn;        // 지하여부 (0: 지상, 1: 지하)
    private int buldMnnm;         // 건물본번
    private int buldSlno;         // 건물부번
    private String mtYn;          // 산여부 (0: 대지, 1: 산)
    private int lnbrMnnm;         // 지번본번 (번지)
    private int lnbrSlno;         // 지번부번 (호)
    private String korAddr;       // 도로명주소 (한글)

}