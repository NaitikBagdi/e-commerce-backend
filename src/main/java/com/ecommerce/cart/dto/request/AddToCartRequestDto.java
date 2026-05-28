package com.ecommerce.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddToCartRequestDto {

    @NotNull
    private Long productId;

    private Long variantId;
 
    @Positive
    private Integer quantity;
 
}