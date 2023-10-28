package com.example.chatapplication.domain;

import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "review_movie")
public class ReviewMovie extends Audiant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "content")
    private String content;

    @Column(name = "parrent_id")
    private Long parrentId;

    @Column(name = "children_count")
    private Integer childrenCount;

}
