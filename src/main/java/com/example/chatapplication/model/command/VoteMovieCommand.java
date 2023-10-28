package com.example.chatapplication.model.command;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VoteMovieCommand {

    private Integer vote;
    private Long movieId;
}
