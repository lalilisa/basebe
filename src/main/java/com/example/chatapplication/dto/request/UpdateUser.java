package com.example.chatapplication.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateUser {

    private String address;
    private String name;

    @JsonFormat( pattern = "dd-MM-yyyy")
    private Date dob;
    private String email;
    private String phonenumber;
}
