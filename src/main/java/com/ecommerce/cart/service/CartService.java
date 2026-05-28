package com.ecommerce.cart.service;

import java.util.List;

import com.ecommerce.cart.dto.request.AddToCartRequestDto;
import com.ecommerce.cart.dto.response.CartResponseDto;

import jakarta.validation.Valid;

public interface CartService {
	void addToCart(String email, AddToCartRequestDto request);

	List<CartResponseDto> getCart(String email);

	void removeCartItem(Long cartId, String email);

	void clearCart(String email);

	void updateQuantity(Long cartId, Integer quantity, String email);

	void syncGuestCart(String name, @Valid List<AddToCartRequestDto> requests);

}
