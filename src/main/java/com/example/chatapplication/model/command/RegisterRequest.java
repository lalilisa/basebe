package com.example.chatapplication.model.command;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String fullName;
}
