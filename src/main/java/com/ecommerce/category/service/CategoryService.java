package com.ecommerce.category.service;

import java.util.List;

import com.ecommerce.category.dto.request.CategoryRequestDto;
import com.ecommerce.category.dto.response.CategoryResponseDto;

public interface CategoryService {

	CategoryResponseDto create(CategoryRequestDto request);

	List<CategoryResponseDto> getAll();

	CategoryResponseDto update(Long id, CategoryRequestDto request);

	void delete(Long id);
}
