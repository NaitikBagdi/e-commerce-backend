package com.ecommerce.cart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.annotation.IsCustomer;
import com.ecommerce.cart.dto.request.AddToCartRequestDto;
import com.ecommerce.cart.dto.request.UpdateCartQuantityDto;
import com.ecommerce.cart.dto.response.CartResponseDto;
import com.ecommerce.cart.service.CartService;
import com.ecommerce.common.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@IsCustomer
public class CartController {

	private final CartService cartService;

	@PostMapping
	public ApiResponse<String> addToCart(@Valid @RequestBody AddToCartRequestDto request, Principal principal) {
		cartService.addToCart(principal.getName(), request);
		return ApiResponse.<String>builder().success(true).message("Product added to cart").build();
	}

	@PostMapping("/sync")
    public ApiResponse<String> syncCart(@Valid @RequestBody List<AddToCartRequestDto> requests, Principal principal) {
        cartService.syncGuestCart(principal.getName(), requests);
        return ApiResponse.<String>builder().success(true).message("Guest cart synced successfully").build();
    }

	@GetMapping
	public ApiResponse<List<CartResponseDto>> getCart(Principal principal) {
		return ApiResponse.<List<CartResponseDto>>builder().success(true).message("Cart fetched successfully")
				.data(cartService.getCart(principal.getName())).build();
	}

	@DeleteMapping("/{cartId}")
	public ApiResponse<String> removeCartItem(@PathVariable Long cartId, Principal principal) {
		cartService.removeCartItem(cartId, principal.getName());
		return ApiResponse.<String>builder().success(true).message("Cart item removed").build();
	}

	@DeleteMapping("/clear")
	public ApiResponse<String> clearCart(Principal principal) {
		cartService.clearCart(principal.getName());
		return ApiResponse.<String>builder().success(true).message("Cart cleared").build();
	}

	@PutMapping("/{cartId}")
	public ApiResponse<String> updateQuantity(@PathVariable Long cartId, @RequestBody UpdateCartQuantityDto cartQuantityDto, Principal principal) {
		cartService.updateQuantity(cartId, cartQuantityDto.getQuantity(), principal.getName());
		return ApiResponse.<String>builder().success(true).message("Quantity updated").build();
	}
}
