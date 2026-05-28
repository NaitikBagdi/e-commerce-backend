package com.ecommerce.product.service.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.category.entity.Category;
import com.ecommerce.category.repository.CategoryRepository;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.dto.request.ProductRequestDto;
import com.ecommerce.product.dto.response.ProductResponseDto;
import com.ecommerce.product.dto.response.ProductVariantResponseDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductImage;
import com.ecommerce.product.entity.ProductVariant;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;
	private final String UPLOAD_DIR = "uploads/products/";

	@Override
	public ProductResponseDto createProduct(ProductRequestDto request, List<MultipartFile> files) {
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		Product product = Product.builder().name(request.getName()).description(request.getDescription()).price(request.getPrice()).stock(request.getStock()).active(true).category(category).build();
		if( request.getVariants() != null) {
		        List<ProductVariant>
		        variants = request.getVariants().stream().map(v -> {
		                    ProductVariant variant = ProductVariant.builder()
		                    		.size(v.getSize())
		                            .color(v.getColor())
		                            .price(v.getPrice() )
		                            .stock( v.getStock())
		                            .sku(v.getSku())
		                            .product(product)
		                            .build();
		                    return variant;
		                }) .toList();
		        product.setVariants(variants );
		    }
		product.setImages(uploadImage(files, product));
		Product saved = productRepository.save(product);
		return mapToDto(saved);
	}

	@Override
	public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		if( request.getVariants() != null) {
	        List<ProductVariant>
	        variants = request.getVariants().stream().map(v -> {
	                    ProductVariant variant = ProductVariant.builder()
	                    		.size(v.getSize())
	                            .color(v.getColor())
	                            .price(v.getPrice() )
	                            .stock( v.getStock())
	                            .sku(v.getSku())
	                            .product(product)
	                            .build();
	                    return variant;
	                }) .toList();
	        product.setVariants(variants );
	    }
		product.setCategory(category);
		return modelMapper.map(productRepository.save(product), ProductResponseDto.class);
	}

	@Override
	public ProductResponseDto getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		return mapToDto(product);
	}

	@Override
	public Page<ProductResponseDto> getAllProducts(Long categoryId, int page, int size, String sortBy,
			String direction) {
		Page<Product> products;
		Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		if (categoryId != null) {
			products = productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable);
		} else {
			products = productRepository.findByActiveTrue(pageable);
		}
		Page<ProductResponseDto> map = products.map(
				product -> mapToDto(product));
		return map;
	}

	@Override
	public Page<ProductResponseDto> searchProducts(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findByNameContainingIgnoreCase(keyword, pageable).map(product -> mapToDto(product));
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		productRepository.delete(product);
	}

	public List<ProductImage> uploadImage(List<MultipartFile> files, Product product) {
		List<ProductImage> productImageList = new ArrayList<>();
		try {
				// unique file name
				for (MultipartFile file : files) {
					String fileName = file.getOriginalFilename();
					// full path
					Path uploadPath = Paths.get(UPLOAD_DIR);
					// create folder if not exists
					if (!Files.exists(uploadPath)) {
						Files.createDirectories(uploadPath);
					}
					// final file location
					Path filePath = uploadPath.resolve(fileName);
					// save file
					Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	
					// URL save in DB
					String imageUrl = UPLOAD_DIR + fileName;
	
					ProductImage productImage = ProductImage.builder().imageUrl(imageUrl).product(product).build();
					productImageList.add(productImage);
			}

			return productImageList;
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload image");
		}
	}

	private ProductResponseDto mapToDto(Product product) {
		return ProductResponseDto
				.builder()
				.id(product.getId())
				.categoryId(product.getCategory().getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.stock(product.getStock())
				.active(product.getActive())
				.categoryName(product.getCategory().getName())
				.hasVariants(!product.getVariants().isEmpty())
				.available(product.getVariants().isEmpty() ? product.getStock() > 0 : product.getVariants().stream().anyMatch(v -> v.getStock() > 0))
				.imageUrls(product.getImages().stream().map(image ->"http://localhost:8080/ecommerce/" + image.getImageUrl()).toList())
				.variants(product.getVariants().stream().map(variant -> ProductVariantResponseDto.builder()
						.id(variant.getId())
						.size(variant.getSize())
						.color(variant.getColor())
						.price(variant.getPrice())
						.stock(variant.getStock())
						.available(variant.getStock() > 0)
						.build()).toList())
				.build();
	}
}
