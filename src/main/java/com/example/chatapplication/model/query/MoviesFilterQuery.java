package com.example.chatapplication.model.query;


import com.example.chatapplication.model.pageable.DefaultOffsetPageable;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoviesFilterQuery extends DefaultOffsetPageable {

    private String category;
    private String moviesType;
    private String trending;
    private Integer year;

}
