package com.ecommerce.category.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.category.dto.request.CategoryRequestDto;
import com.ecommerce.category.dto.response.CategoryResponseDto;
import com.ecommerce.category.entity.Category;
import com.ecommerce.category.repository.CategoryRepository;
import com.ecommerce.category.service.CategoryService;
import com.ecommerce.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;

	@Override
	public CategoryResponseDto create(CategoryRequestDto request) {

		Category category = Category.builder().name(request.getName()).active(true).build();
		return modelMapper.map(categoryRepository.save(category), CategoryResponseDto.class);
	}

	@Override
	public List<CategoryResponseDto> getAll() {
		return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryResponseDto.class)).toList();
	}

	@Override
	public CategoryResponseDto update(Long id, CategoryRequestDto request) {

		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		category.setName(request.getName());
		return modelMapper.map(categoryRepository.save(category), CategoryResponseDto.class);
	}

	@Override
	public void delete(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		category.setActive(false);
		categoryRepository.save(category);
	}

}
