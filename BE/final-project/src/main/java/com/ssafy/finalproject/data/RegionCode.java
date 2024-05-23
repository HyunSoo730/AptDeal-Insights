package com.ssafy.finalproject.data;

import java.util.Arrays;
import java.util.List;

public class RegionCode {
    private static final List<String> seoulLawdCodes = Arrays.asList(
            "11110", // 종로구
            "11140", // 중구
            "11170", // 용산구
            "11200", // 성동구
            "11215", // 광진구
            "11230", // 동대문구
            "11260", // 중랑구
            "11290", // 성북구
            "11305", // 강북구
            "11320", // 도봉구
            "11350", // 노원구
            "11380", // 은평구
            "11410", // 서대문구
            "11440", // 마포구
            "11470", // 양천구
            "11500", // 강서구  // ! 2021 11500에서 터짐
            "11530", // 구로구
            "11545", // 금천구
            "11560", // 영등포구
            "11590", // 동작구
            "11620", // 관악구
            "11650", // 서초구
            "11680", // 강남구
            "11710", // 송파구
            "11740"  // 강동구
    );

    public static List<String> getSeoulLawdCodes() {
        return seoulLawdCodes;
    }
}
