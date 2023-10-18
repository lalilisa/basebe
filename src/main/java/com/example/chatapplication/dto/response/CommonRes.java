package com.example.chatapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommonRes<T> {

    private Integer statusCode;
    private Boolean isError;
    private T data;
    private Map<String,Object> errorMap;

}
