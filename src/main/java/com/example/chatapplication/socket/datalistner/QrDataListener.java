package com.example.chatapplication.socket.datalistner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class QrDataListener {
    private String accessToken;
    private Integer status;
}
