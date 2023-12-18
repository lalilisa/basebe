package com.example.chatapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "sub_movie")

public class SubMovie extends Audiant{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "public_date")
    private Date publicDate;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "episode")
    private Integer episode;

    @Column(name = "active")
    private Integer active;

    @Column(name = "src")
    private String src;
}
