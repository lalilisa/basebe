package com.example.chatapplication.domain;

import com.example.chatapplication.domain.compositekey.MovieCategoryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "movie_package")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MoviePackage {

    @EmbeddedId
    private MovieCategoryKey id;
}
