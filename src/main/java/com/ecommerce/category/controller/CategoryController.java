package com.ecommerce.category.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.annotation.IsAdmin;
import com.ecommerce.category.dto.request.CategoryRequestDto;
import com.ecommerce.category.dto.response.CategoryResponseDto;
import com.ecommerce.category.service.CategoryService;
import com.ecommerce.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	@IsAdmin
	public ApiResponse<CategoryResponseDto> create(@Valid @RequestBody CategoryRequestDto request) {

		return ApiResponse.<CategoryResponseDto>builder().success(true).message("Category created successfully")
				.data(categoryService.create(request)).build();
	}

	@GetMapping
	public ApiResponse<List<CategoryResponseDto>> getAll() {

		return ApiResponse.<List<CategoryResponseDto>>builder().success(true).message("Categories fetched successfully")
				.data(categoryService.getAll()).build();
	}
}
