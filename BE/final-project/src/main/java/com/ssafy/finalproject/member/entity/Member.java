package com.ssafy.finalproject.member.entity;

import com.ssafy.finalproject.ai.AiMessage;
import com.ssafy.finalproject.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    private String nickname;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<AiMessage> aiMessages;


    @Column(name = "created_at")
    private LocalDateTime createdAt=LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt=LocalDateTime.now();

    @Override
    public String toString() {
        return "Member{id=" + id + ", email='" + email + "', name='" + name + "', nickname='" + nickname + "'}";
    }



}