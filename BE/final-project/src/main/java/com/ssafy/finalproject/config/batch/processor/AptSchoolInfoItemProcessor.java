package com.ssafy.finalproject.config.batch.processor;

import com.ssafy.finalproject.aptshool.entity.AptSchoolInfo;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class AptSchoolInfoItemProcessor implements ItemProcessor<AptSchoolInfo, AptSchoolInfo> {

    @Override
    public AptSchoolInfo process(final AptSchoolInfo aptSchoolInfo) throws Exception {
        String legalDong = aptSchoolInfo.getLegalDong();
        String roadNameAddress = aptSchoolInfo.getRoadNameAddress();

        // 도로명 주소에서 숫자를 추출
        String[] addressParts = roadNameAddress.split(" ");
        String addressNumber = addressParts[addressParts.length - 1];
        String[] numbers = addressNumber.split("-");
        String num1 = numbers[0].length() == 5 ? numbers[0] : String.format("%05d", Integer.parseInt(numbers[0]));
        String num2 = numbers.length > 1 ? (numbers[1].length() == 5 ? numbers[1] : String.format("%05d", Integer.parseInt(numbers[1]))) : "00000";

        String aptCode = legalDong + num1 + num2;
        aptSchoolInfo.setAptCode(aptCode);

        return aptSchoolInfo;
    }
}
