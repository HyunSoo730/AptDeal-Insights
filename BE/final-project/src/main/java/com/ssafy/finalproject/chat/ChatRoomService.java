package com.ssafy.finalproject.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAll() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom findByName(String name) {
        return chatRoomRepository.findByName(name);
    }

    public ChatRoom createChatRoom(String name) {
        if (chatRoomRepository.findByName(name) != null) {
            throw new IllegalArgumentException("Room name already exists");
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        return chatRoomRepository.save(chatRoom);
    }

    public void deleteChatRoom(Long id) {
        chatRoomRepository.deleteById(id);
    }
}
