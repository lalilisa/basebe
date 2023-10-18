package com.example.chatapplication.domain;

import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "review_movie")
public class ReviewMovie extends Audiant{

    @EmbeddedId
    private UserMoviesKey id;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "content")
    private String content;
}
