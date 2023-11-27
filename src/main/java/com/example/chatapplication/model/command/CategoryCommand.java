package com.example.chatapplication.model.command;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryCommand {
    @NotNull
    private String name;
    @NotNull
    private String code;
    @NotNull
    private Boolean active = true;
}
