package com.ecommerce.order.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.annotation.IsAdmin;
import com.ecommerce.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@IsAdmin
@CrossOrigin("*")
public class AdminOrderController {

	private final OrderService orderService;

//	@PutMapping("/{id}/status")
//	public ApiResponse<String> updateStatus(
//
//			@PathVariable Long id,
//
//			@Valid @RequestBody UpdateOrderStatusRequestDto request) {
//
//		orderService.updateOrderStatus(id, request.getStatus());
//
//		return ApiResponse.<String>builder().success(true).message("Order status updated").build();
//	}
}
