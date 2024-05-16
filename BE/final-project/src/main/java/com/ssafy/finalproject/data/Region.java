package com.ssafy.finalproject.data;
public enum Region {
    SEOUL("11", "서울특별시"),
    BUSAN("21", "부산광역시"),
    DAEGU("22", "대구광역시"),
    INCHEON("23", "인천광역시"),
    GWANGJU("24", "광주광역시"),
    DAEJEON("25", "대전광역시"),
    ULSAN("26", "울산광역시"),
    SEJONG("29", "세종특별자치시"),
    GYEONGGI("31", "경기도"),
    GANGWON("32", "강원도"),
    CHUNGBUK("33", "충청북도"),
    CHUNGNAM("34", "충청남도"),
    JEONBUK("35", "전라북도"),
    JEONNAM("36", "전라남도"),
    GYEONGBUK("37", "경상북도"),
    GYEONGNAM("38", "경상남도"),
    JEJU("39", "제주특별자치도");

    private final String code;
    private final String name;

    Region(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Region fromCode(String code) {
        for (Region region : Region.values()) {
            if (region.getCode().equals(code)) {
                return region;
            }
        }
        throw new IllegalArgumentException("Invalid region code: " + code);
    }
}