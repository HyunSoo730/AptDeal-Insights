package com.ssafy.finalproject.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;

    @GetMapping
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.findAll();
    }

    @PostMapping
    public ChatRoom createChatRoom(@RequestBody String name) {
        return chatRoomService.createChatRoom(name);
    }
}
