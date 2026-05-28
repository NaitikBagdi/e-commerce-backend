package com.ecommerce.order.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDto {

	  private Long id;

	    private String orderNumber;

	    private Double totalAmount;

	    private String paymentMethod;

	    private String paymentStatus;

	    private String orderStatus;

	    private LocalDateTime orderDate;

	    private List<OrderItemResponseDto> items;

}