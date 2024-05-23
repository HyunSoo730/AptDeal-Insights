package com.ssafy.finalproject.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public String sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ssafy2411@gmail.com");
        System.out.println(to);
        message.setTo(to);
        message.setSubject("관통 프로젝트에서 보내는 비밀번호 인증 메일입니다.");

        // 난수 4자리 생성
        String randomCode = generateRandomCode(4);
        message.setText("인증번호는 [ " + randomCode + " ] 입니다.");

        mailSender.send(message);

        return randomCode;
    }

    private String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }
}