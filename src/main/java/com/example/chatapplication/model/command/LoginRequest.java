package com.example.chatapplication.model.command;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
