package com.ecommerce.cart.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.cart.dto.response.CartResponseDto;
import com.ecommerce.cart.entity.Cart;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductVariant;

@Component
public class CartMapper {

	public CartResponseDto toDto(Cart cart) {
		Product product = cart.getProduct();
		ProductVariant variant = cart.getProductVariant();
		Boolean isAvailable = false;
		if(variant != null) {
			isAvailable =  variant.getStock() >= cart.getQuantity();
		} else {
			isAvailable =  product.getStock() >= cart.getQuantity();
		}
		return CartResponseDto.builder().cartId(cart.getId()).productId(product.getId()).variantId(variant != null ? variant.getId() : null).productName(product.getName())
				.imageUrl(product.getImages() != null && !product.getImages().isEmpty()
						? "http://localhost:8080/ecommerce/" + product.getImages().get(0).getImageUrl()
						: null)
				.size(variant != null ? variant.getSize() : null)
				.color(variant != null ? variant.getColor() : null)
				.quantity(cart.getQuantity())
				.price(cart.getPrice())
				.totalPrice(product.getPrice() !=null ? product.getPrice() : variant.getPrice() * cart.getQuantity())
				.available(isAvailable)
				.build();
	}

}
