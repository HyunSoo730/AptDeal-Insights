package com.ssafy.finalproject.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    @MessageMapping("/chat.sendMessage/{roomName}")
    @SendTo("/topic/{roomName}")
    public ChatMessage sendMessage(ChatMessage chatMessage, @DestinationVariable String roomName) {
        ChatRoom chatRoom = chatRoomService.findByName(roomName);
        if (chatRoom == null) {
            throw new IllegalArgumentException("Room does not exist");
        }
        return chatMessage;
    }
}
