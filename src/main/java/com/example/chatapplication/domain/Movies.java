package com.example.chatapplication.domain;


import com.example.chatapplication.common.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "movies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movies extends Audiant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type" )
//    @Enumerated(EnumType.STRING)
    private String moviesType;

    @Column(name = "duration")
    private String duration;

    @Column(name = "thumbnail")
    private String thumnail;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "active")
    private Integer active;

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "view")
    private Integer view;


}
