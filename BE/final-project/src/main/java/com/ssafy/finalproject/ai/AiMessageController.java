package com.ssafy.finalproject.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class AiMessageController {

    private final AiMessageService aiMessageService;

    @GetMapping("/{memberId}/{sessionId}")
    public List<AiMessage> getChatMessages(@PathVariable("memberId") Long memberId, @PathVariable("sessionId") String sessionId) {
        return aiMessageService.getChatMessages(memberId, sessionId);
    }

    @PostMapping("/save")
    public void saveChatMessages(@RequestParam Long memberId, @RequestParam String sessionId, @RequestBody List<AiMessage> messages) {
        aiMessageService.saveAiMessages(memberId, sessionId, messages);
    }

    @GetMapping("/{memberId}/sessions")
    public List<String> getSessionIds(@PathVariable("memberId") Long memberId) {
        return aiMessageService.getSessionIds(memberId);
    }
}
