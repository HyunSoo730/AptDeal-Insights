package com.ssafy.finalproject.ai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AiMessageRepository extends JpaRepository<AiMessage, Long> {
    List<AiMessage> findByMemberIdAndSessionId(Long memberId, String sessionId);

    @Query("SELECT DISTINCT sessionId FROM AiMessage WHERE member.id = :memberId")
    List<String> findDistinctSessionIdsByMemberId(Long memberId);
}
