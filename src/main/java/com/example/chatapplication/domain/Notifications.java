package com.example.chatapplication.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Notifications extends Audiant
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name ="title" )
    private String title;

    @Column(name = "img")
    private String img;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "main_ref")
    private Long mainRef;

    @Column(name = "type")
    private String type;

}
