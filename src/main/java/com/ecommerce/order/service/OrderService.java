package com.ecommerce.order.service;

import java.util.List;

import com.ecommerce.order.dto.request.CreateOrderRequestDto;
import com.ecommerce.order.dto.response.OrderResponseDto;

public interface OrderService {

	OrderResponseDto placeOrder(CreateOrderRequestDto request, String email);

	List<OrderResponseDto> getMyOrders(String email);

//	OrderResponseDto getOrderById(Long orderId, String email);
//
//	void cancelOrder(Long orderId, String email);
//
//	void updateOrderStatus(Long orderId, OrderStatus status);

}
