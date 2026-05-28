package com.ecommerce.user.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
	private String name;
	private String address;
	private String email;
	private Long phone;
}
