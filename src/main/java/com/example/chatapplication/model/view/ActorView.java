package com.example.chatapplication.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActorView {

    private Long id;
    private String fullName;
    private String nickName;
    private Date dob;
    private String avatar;
    private String profile;
}
