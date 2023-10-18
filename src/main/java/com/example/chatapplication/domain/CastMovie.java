package com.example.chatapplication.domain;


import com.example.chatapplication.domain.compositekey.MovieCastKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cast_movie")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CastMovie {

    @EmbeddedId
    private MovieCastKey id;
}
