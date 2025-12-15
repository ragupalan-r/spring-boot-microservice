package com.example.order_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private double totalPrice;

    //product details
    private String productName;
    private double productPrice;

}
