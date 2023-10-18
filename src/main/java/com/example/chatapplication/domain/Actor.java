package com.example.chatapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "actors")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Actor extends Audiant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "profile")
    private String profile;

}
