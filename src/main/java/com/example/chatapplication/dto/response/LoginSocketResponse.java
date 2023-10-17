package com.example.chatapplication.dto.response;


import com.example.chatapplication.common.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginSocketResponse{
    private Integer status;
    private Long userId;
    private Category.Role role;
    private String accessToken;
    private String refreshToken;
    private Date expireAccressToken;
}
