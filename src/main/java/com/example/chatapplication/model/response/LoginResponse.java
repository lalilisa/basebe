package com.example.chatapplication.model.response;

import com.example.chatapplication.common.Category;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginResponse {
    private Long userId;
    private String name;
    private String avatar;
    private Category.Role role;
    private String accessToken;
    private String refreshToken;
    private Date expireAccressToken;
    private Integer isNotifi;
}
