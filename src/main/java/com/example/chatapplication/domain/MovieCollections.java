package com.example.chatapplication.domain;


import com.example.chatapplication.domain.compositekey.UserMoviesKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "movie_collections")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieCollections extends Audiant{

    @EmbeddedId
    private UserMoviesKey id;
}
