package com.example.chatapplication.model.command;


import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReviewCommand {
    private Long reviewId;
    private String content;
}
