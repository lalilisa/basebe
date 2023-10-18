package com.example.chatapplication.domain.compositekey;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class MovieCastKey implements Serializable {
    @Column(name = "cast_id")
    private Long castId;
    @Column(name = "movie_id")
    private Long movieId;
}
