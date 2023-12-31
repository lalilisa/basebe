package com.example.chatapplication.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
