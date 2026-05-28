package com.ecommerce.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private Long cartId;

    private Long productId;

    private Long variantId;
 
    private String productName;

    private String size;

    private String color;
   
    private Integer quantity;

    private Double price;

    private Double totalPrice;

    private String imageUrl;

    private Boolean available;
 
}
