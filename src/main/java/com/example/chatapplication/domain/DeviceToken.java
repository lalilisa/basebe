package com.example.chatapplication.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "device_token")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class DeviceToken extends Audiant{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fcm_token")
    private String fcmToken;

    @Column(name ="user_id" )
    private Long userId;
}
