package com.example.chatapplication.model.command;


import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewCommand {
    private Long moviesId;
    private String content;
    private Long parentId;
}
