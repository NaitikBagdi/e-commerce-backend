package com.ecommerce.product.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

	private Long id;

	private Long categoryId;

	private String name;

	private String description;

    private Double price;

    private Integer stock;

	private Boolean active;

	private String categoryName;
	private Boolean hasVariants;

	private Boolean available;
	private List<String> imageUrls;

	private List<ProductVariantResponseDto> variants;

}