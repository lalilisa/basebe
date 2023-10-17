package com.example.chatapplication.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QueryProduct {

    private Integer page;

    private Integer limit;

    private String sort;

    private String search;
}
