package com.ecommerce.order.dto.request;

import com.ecommerce.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderStatusRequestDto {

    @NotNull
    private OrderStatus status;

}