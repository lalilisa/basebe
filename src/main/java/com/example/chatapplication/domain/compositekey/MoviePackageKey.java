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
public class MoviePackageKey implements Serializable {

    @Column(name = "package_id")
    private Long packageId;
    @Column(name = "movie_id")
    private Long movieId;
}
