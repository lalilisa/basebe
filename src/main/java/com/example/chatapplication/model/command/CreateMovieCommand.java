package com.example.chatapplication.model.command;

import com.example.chatapplication.domain.SubMovie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateMovieCommand {
    private String code;
    private String name;
    private String description;
    private String moviesType;
    private String duration;

    @Nullable
    private MultipartFile thumnail;
    private Integer active;
//    private Date releaseDate;
    private List<Long> categoriesIds;
}
