package com.example.chatapplication.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "room_chat")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class RoomChat extends Audiant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adminId")
    private Long adminId;

    @Column(name = "userId")
    private Long userId;
}
