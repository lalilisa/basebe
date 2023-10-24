package com.example.chatapplication.model.query;

import com.example.chatapplication.model.pageable.DefaultOffsetPageable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieQuery extends DefaultOffsetPageable {
    private Long movieId;
}
