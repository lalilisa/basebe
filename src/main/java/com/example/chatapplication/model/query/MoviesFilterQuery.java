package com.example.chatapplication.model.query;


import com.example.chatapplication.model.pageable.DefaultOffsetPageable;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoviesFilterQuery extends DefaultOffsetPageable {

    private String categories = "";
    private String name = "";
    private String moviesType;
    private String trending;
    private Integer year;

}
