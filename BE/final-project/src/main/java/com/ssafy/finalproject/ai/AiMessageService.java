package com.ssafy.finalproject.ai;

import com.ssafy.finalproject.member.entity.Member;
import com.ssafy.finalproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiMessageService {

    private final AiMessageRepository aiMessageRepository;
    private final MemberRepository memberRepository;

    public List<AiMessage> getChatMessages(Long memberId, String sessionId) {
        return aiMessageRepository.findByMemberIdAndSessionId(memberId, sessionId);
    }

    public void saveAiMessages(Long memberId, String sessionId, List<AiMessage> messages) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member != null) {
            for (AiMessage message : messages) {
                message.setMember(member);
                message.setSessionId(sessionId);
                aiMessageRepository.save(message);
            }
        }
    }

    public List<String> getSessionIds(Long memberId) {
        return aiMessageRepository.findDistinctSessionIdsByMemberId(memberId);
    }
}
