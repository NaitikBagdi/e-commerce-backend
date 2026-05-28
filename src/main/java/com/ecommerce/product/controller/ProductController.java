package com.ecommerce.product.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.annotation.IsAdmin;
import com.ecommerce.common.ApiResponse;
import com.ecommerce.product.dto.request.ProductRequestDto;
import com.ecommerce.product.dto.response.ProductResponseDto;
import com.ecommerce.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@IsAdmin
	public ApiResponse<ProductResponseDto> createProduct(@Valid @RequestPart("product") ProductRequestDto request, 
	        @RequestPart(value = "file", required = false) List<MultipartFile> file) {
		return ApiResponse.<ProductResponseDto>builder().success(true).message("Product created successfully")
				.data(productService.createProduct(request, file)).build();
	}

//	@PostMapping(value = "secound", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	@IsAdmin
//	public ApiResponse<ProductResponseDto> createProduct(
//	        @Valid @RequestPart("product") ProductRequestDto1 request, 
//	        @RequestPart(value = "file", required = false) List<MultipartFile> file) {
//	    
//	    return ApiResponse.<ProductResponseDto>builder()
//	            .success(true)
//	            .message("Product created successfully")
//	            .data(new ProductResponseDto())
//	            .build();
//	}
	@PutMapping("/{id}")
	@IsAdmin
	public ApiResponse<ProductResponseDto> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequestDto request) {

		return ApiResponse.<ProductResponseDto>builder().success(true).message("Product updated successfully")
				.data(productService.updateProduct(id, request)).build();
	}

	@GetMapping("/{id}")
	public ApiResponse<ProductResponseDto> getProduct(@PathVariable Long id) {
		return ApiResponse.<ProductResponseDto>builder().success(true).message("Product fetched successfully")
				.data(productService.getProductById(id)).build();
	}

	@GetMapping
	public ApiResponse<Page<ProductResponseDto>> getAllProducts( @RequestParam(required = false) Long categoryId, 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {
		return ApiResponse.<Page<ProductResponseDto>>builder().success(true).message("Products fetched successfully")
				.data(productService.getAllProducts(categoryId, page, size, sortBy, direction)).build();
	}

	@GetMapping("/search")
	public ApiResponse<Page<ProductResponseDto>> searchProducts(@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ApiResponse.<Page<ProductResponseDto>>builder().success(true).message("Products fetched successfully")
				.data(productService.searchProducts(keyword, page, size)).build();
	}

	@DeleteMapping("/{id}")
	@IsAdmin
	public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ApiResponse.<Void>builder().success(true).message("Product deleted successfully").build();
	}

//	@PostMapping(value = "/{id}/images",
//		    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	@IsAdmin
//	public ApiResponse<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//		productService.uploadImage(id, file);
//		return ApiResponse.<String>builder().success(true).message("Image uploaded successfully").build();
//	}
}
