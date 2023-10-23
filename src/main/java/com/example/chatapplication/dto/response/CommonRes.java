package com.example.chatapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
public class CommonRes<T> {

    private Integer statusCode;
    private Boolean isError;
    private T data;
    private Map<String,Object> errorMap;


    public CommonRes(Integer statusCode,Boolean isError,String error){
        this.statusCode = statusCode;
        this.isError = isError;
        errorMap = new HashMap<>();
        errorMap.put("error",error);
    }
    public CommonRes(Integer statusCode,Boolean isError,T data){
        this.statusCode = statusCode;
        this.isError = isError;
        this.data = data;
    }

    public CommonRes(){

    }


}
