package com.example.chatapplication.model.command;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateUser {

    private String address;
    private String name;
    private String nickName;
    @JsonFormat( pattern = "dd-MM-yyyy")
    private Date dob;
    private String email;
    private String phonenumber;
    private MultipartFile avatar;
}
