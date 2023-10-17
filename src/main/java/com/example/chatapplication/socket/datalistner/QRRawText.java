package com.example.chatapplication.socket.datalistner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QRRawText implements Serializable {
    private String hashCode;
}
