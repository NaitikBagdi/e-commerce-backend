package com.ecommerce.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductVariantResponseDto {

    private Long id;

    private String size;

    private String color;

    private Double price;

    private Integer stock;

    private Boolean available;
}