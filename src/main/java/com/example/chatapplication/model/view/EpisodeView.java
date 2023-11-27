package com.example.chatapplication.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EpisodeView {

    private String src;
    private String name;
    private Date publicDate;
    private Integer episode;
}
