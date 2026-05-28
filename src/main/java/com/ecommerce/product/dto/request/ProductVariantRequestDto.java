package com.ecommerce.product.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariantRequestDto {

    private String size;

    private String color;

    private Double price;

    private Integer stock;

    private String sku;
}
