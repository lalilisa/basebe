package com.example.chatapplication.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListAll<T> {
    private int totalPage;
    private int currentPage;
    private int currentData;
    private List<T> data;
//    private List<T> result;
}