package com.example.chatapplication.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "packages")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Packages extends Audiant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code" , unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "price")
    private Double price;

    @Column(name = "img")
    private String img;

    @Column(name = "active")
    private Boolean active;
}
