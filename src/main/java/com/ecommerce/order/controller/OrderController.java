package com.ecommerce.order.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.annotation.IsCustomer;
import com.ecommerce.cart.dto.request.UpdateCartQuantityDto;
import com.ecommerce.common.ApiResponse;
import com.ecommerce.order.dto.request.CreateOrderRequestDto;
import com.ecommerce.order.dto.response.OrderResponseDto;
import com.ecommerce.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@IsCustomer
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ApiResponse<OrderResponseDto> placeOrder(@Valid @RequestBody CreateOrderRequestDto request, Principal principal) {
		return ApiResponse.<OrderResponseDto>builder().success(true).message("Order placed successfully").data(orderService.placeOrder(request, principal.getName())).build();
	}

	@GetMapping
	public ApiResponse<List<OrderResponseDto>> getMyOrders(Principal principal) {
		return ApiResponse.<List<OrderResponseDto>>builder().success(true).message("Orders fetched successfully")
				.data(orderService.getMyOrders(principal.getName())).build();
	}

//	@GetMapping("/{id}")
//	public ApiResponse<OrderResponseDto> getOrder(@PathVariable Long id, Principal principal) {
//		return ApiResponse.<OrderResponseDto>builder().success(true).message("Order fetched successfully")
//				.data(orderService.getOrderById(id, principal.getName())).build();
//	}

//	@PutMapping("/{id}/cancel")
//	public ApiResponse<String> cancelOrder(@PathVariable Long id, Principal principal) {
//		orderService.cancelOrder(id, principal.getName());
//		return ApiResponse.<String>builder().success(true).message("Order cancelled successfully").build();
//	}

}
