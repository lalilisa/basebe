package com.example.chatapplication.model.view;

import com.example.chatapplication.common.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserView {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dob;
    private String phonnumber;
    private String address;
    private String fullname;
    private Integer gender;
    private Category.Role role;
    private Integer isNotifi;
}
