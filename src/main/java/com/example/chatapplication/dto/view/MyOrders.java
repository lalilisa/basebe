package com.example.chatapplication.dto.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MyOrders {
    private String name;
    private Double totalPrice;
    private String code;
    private String address;
    private Long orderId;
    private Date createdAt;
    private Integer status;
    private List<ProductView> productViews;
}
