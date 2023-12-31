package com.example.chatapplication.model.view;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewView {

    private Long userId;
    private Long reviewId;
    private Long movieId;
    private String name;
    private String avatar;
    private Long parrentId;
    private String content;
    private Date createdAt;
    private Boolean byYourSelf;
    private Integer children;
}
