package com.example.chatapplication.service.read;


import com.example.chatapplication.domain.ChatMessage;
import com.example.chatapplication.repo.ChatRepository;
import com.example.chatapplication.socket.datalistner.ChatData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatRepository chatRepository;

    public List<ChatData> getMessageRoom(Long u1,Long u2){
        List<ChatMessage> chatMessages=chatRepository.getMessageRoom(u1,u2);

        return chatMessages.stream().map(chatMessage -> ChatData.builder()
                .room(Long.toString(chatMessage.getRoomId()))
                .reciverId(chatMessage.getReciverId())
                .senderId(chatMessage.getSenderId())
                 .content(chatMessage.getContent())
                .sentAt(chatMessage.getCreatedAt())
                .build()).collect(Collectors.toList());
    }
}
