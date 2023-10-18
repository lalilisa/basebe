package com.example.chatapplication.socket.module;


import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.chatapplication.common.Category;
import com.example.chatapplication.domain.*;
import com.example.chatapplication.repository.*;
import com.example.chatapplication.service.write.FireBaseNotifiCommandService;
import com.example.chatapplication.socket.datalistner.ChatData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component

public class ChatModule {

    private final SocketIONamespace  namespace;

    private static final Logger log = LoggerFactory.getLogger(ChatModule.class);
    @Autowired
    private RoomChatRepository roomChatRepository;

    @Autowired
    private ChatRepository  chatRepository;

    @Autowired
    private FireBaseNotifiCommandService fireBaseNotifiCommandService;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private UserRepository userRepository;
//    Map<Long,List<String>> userRoom=new HashMap<>();
    @Autowired
    public ChatModule(SocketIOServer socketIOServer){
        this.namespace=socketIOServer.addNamespace(Category.SocketService.chat.name);
        this.namespace.addConnectListener(onConnected());
        this.namespace.addDisconnectListener(onDisconnected());
        this.namespace.addEventListener("chat", ChatData.class,onChating());
    }
    private ConnectListener onConnected(){
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            Long userId = Long.parseLong(handshakeData.getSingleUrlParam("userId"));
            List<String> room=roomChatRepository.findRoomChat(userId).stream().map(roomChat -> roomChat.getId().toString()).collect(Collectors.toList());
            room.forEach(client::joinRoom);
//            userRoom.put(userId,room);
            log.info("Client[{}] - Connected to Chat module through '{}'", client.getSessionId().toString(), handshakeData.getUrl());
        };
    }
    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from Chat module.", client.getSessionId().toString());
        };
    }

    private DataListener<ChatData> onChating(){
        return (socketIOClient, chatData, ackRequest) -> {
               sendMessageInRoom(socketIOClient,chatData);
        };
    }

    private void sendMessageInRoom(SocketIOClient client,ChatData chatData){
        this.namespace.getRoomOperations(chatData.getRoom()).sendEvent("message",client,chatData);
        chatRepository.save(ChatMessage.builder()
                .content(chatData.getContent())
                .roomId(Long.parseLong(chatData.getRoom()))
                .senderId(chatData.getSenderId())
                .reciverId(chatData.getReciverId())
                .build());

        User user=userRepository.findById(chatData.getReciverId()).orElse(null);
    }



}
