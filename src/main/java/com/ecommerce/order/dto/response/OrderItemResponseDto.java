package com.ecommerce.order.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponseDto {

    private String productName;

    private String imageUrl;

    private Integer quantity;

    private Double price;

    private Double subtotal;

}