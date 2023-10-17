package com.example.chatapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DataCategoryChart {

    private Long categoryId;
    private String categoryName;
    private Double totalPrice;
    private Integer totalQuantity;
    private Double percentQuantity;
    private Double percentPrice;
}
