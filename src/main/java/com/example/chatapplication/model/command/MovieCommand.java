package com.example.chatapplication.model.command;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.signature.qual.BinaryName;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@BinaryName
public class MovieCommand {

    @Schema(format = "binary")
    private MultipartFile file;
    private String code;
    private String name;
    private String description;
    private String moviesType;
    private String duration;
    private String thumnail;
    private Integer active;
//    private Date releaseDate;
}
