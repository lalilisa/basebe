package com.example.chatapplication.model.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SubMovieCommand {

    private String name;
//    private Date publicDate;
    private Long movieId;
    private Integer episode;
    private String src;
    private MultipartFile file;
}
