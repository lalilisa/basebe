package com.example.chatapplication.model.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PackageCommand {
    private String code;
    private String name;
    private String duration;
    private Double price;
    private Boolean active;
}
