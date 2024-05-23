package com.ssafy.finalproject.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/emailForm")
    public String emailForm() {
        return "emailForm";
    }

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
        try {
            String randomCode = emailService.sendEmail(emailRequestDTO.getTo());
            System.out.println("이메일이 전송되었습니다.");
            return ResponseEntity.ok().body(Map.of("success", true, "code", randomCode));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("success", false));
        }
    }
}