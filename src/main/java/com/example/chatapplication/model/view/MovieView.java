package com.example.chatapplication.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieView {

    private Long id;
    private String code;
    private String name;
    private String descption;
    private String moviesType;
    private String duration;
    private String thumnail;
    private Double rate;
    private Boolean active;
    private Date releaseDate;
    private List<CategoryView> categories;
    private List<EpisodeView> episode = new ArrayList<>();
    private Integer canPlay;
//    private String categoryCode;
//    private String categoryName;
}
