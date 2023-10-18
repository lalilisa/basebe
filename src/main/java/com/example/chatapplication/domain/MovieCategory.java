package com.example.chatapplication.domain;


import com.example.chatapplication.domain.compositekey.MovieCategoryKey;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "movie_catetory")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class MovieCategory {

    @EmbeddedId
    private MovieCategoryKey id;
}
