package com.ecommerce.product.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductRequestDto1 {
	@NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private Double price;

    @PositiveOrZero
    private Integer stock;

    @NotNull
    private Long categoryId;

    private List<ProductVariantRequestDto> variants;
 
}
