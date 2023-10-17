package com.example.chatapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DataChartView {

    Long orderId;
    Double singlePrice;
    Integer totalQuantity;
    Long categoryId;
    String categoryName;
    String productName;
    Double totalPrice;
    Long productId;
}
