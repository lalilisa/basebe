package com.example.chatapplication.domain;


import com.example.chatapplication.common.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends Audiant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,name = "username")
    private String username;

    @Column(unique = true,nullable = false,name = "email")
    private String email;

    @Column(unique = true,name = "phonenumber")
    private String phonenumber;

    @Column(nullable = false,name = "password")
    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "gender",columnDefinition = "tinyint(1) DEFAULT '1'")
    private Integer gender;

    @Column(name = "role",columnDefinition = "enum('ADMIN','USER') DEFAULT 'USER'")
    @Enumerated(EnumType.STRING)
    private Category.Role role;

    @Column(name = "is_notifi",columnDefinition = "tinyint(1) DEFAULT '0'")
    private Integer isNotifi;
}
