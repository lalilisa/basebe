package com.example.chatapplication.model.command;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateReviewCommand {
    private Long reviewId;
    private String content;
}
