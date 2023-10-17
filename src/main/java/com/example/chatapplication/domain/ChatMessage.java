package com.example.chatapplication.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "chat_message")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ChatMessage extends Audiant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "reciver_id")
    private Long reciverId;

    @Column(name = "content")
    private String content;

    @Column(name = "is_delete")
    private Integer status;
}
