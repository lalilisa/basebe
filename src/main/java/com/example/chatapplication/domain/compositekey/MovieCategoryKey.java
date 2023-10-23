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
public class MovieCategoryKey implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "category_id")
    private Long categoryId;
}
