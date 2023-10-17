package com.example.chatapplication.socket.datalistner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ChatData implements Serializable {

    private Long senderId;
    private Long reciverId;
    private String content;
    private String room;
    private Date sentAt;
}
