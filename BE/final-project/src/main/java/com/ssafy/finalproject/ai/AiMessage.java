package com.ssafy.finalproject.ai;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.finalproject.member.entity.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class AiMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String content;
    private String sessionId; // 세션 ID 필드 추가

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Override
    public String toString() {
        return "AiMessage{id=" + id + ", role='" + role + "', content='" + content + "', sessionId='" + sessionId + "'}";
    }
}
