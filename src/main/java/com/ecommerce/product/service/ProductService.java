package com.ecommerce.product.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.product.dto.request.ProductRequestDto;
import com.ecommerce.product.dto.response.ProductResponseDto;

public interface ProductService {

	ProductResponseDto createProduct(ProductRequestDto request, List<MultipartFile> files);

	ProductResponseDto updateProduct(Long id, ProductRequestDto request);

	ProductResponseDto getProductById(Long id);

	Page<ProductResponseDto> getAllProducts(Long categoryId, int page, int size, String sortBy, String direction);

	Page<ProductResponseDto> searchProducts(String keyword, int page, int size);

	void deleteProduct(Long id);

}
