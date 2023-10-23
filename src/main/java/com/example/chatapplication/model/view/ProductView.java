package com.example.chatapplication.model.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductView {

    private Long id;

    private String code;

    private Integer quantity;

    private Double discount;

    private Double price;

    private String name;

    private String img;

    private Integer view;

    private Long categoryId;

    private String description;

    private String categoryName;
}
