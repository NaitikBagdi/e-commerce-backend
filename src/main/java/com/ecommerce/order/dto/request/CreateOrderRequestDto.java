package com.ecommerce.order.dto.request;

import com.ecommerce.enums.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequestDto {

	@NotBlank
    private String address;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private PaymentMethod paymentMethod;

}
