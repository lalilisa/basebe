package com.example.chatapplication.domain;


import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_vote")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class UserVote extends Audiant {

    @EmbeddedId
    private UserMoviesKey id;

    @Column(name = "vote")
    private Integer vote;
}
