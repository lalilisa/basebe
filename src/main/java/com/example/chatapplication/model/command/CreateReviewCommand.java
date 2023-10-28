package com.example.chatapplication.model.command;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateReviewCommand {
    private Long moviesId;
    private String content;
    private Long parrentId;
}
