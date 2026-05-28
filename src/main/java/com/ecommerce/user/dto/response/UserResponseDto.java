package com.ecommerce.user.dto.response;

import com.ecommerce.enums.Role;

import lombok.Data;

@Data
public class UserResponseDto {

	private Long id;
	private String name;
	private String address;
	private String email;
	private Long phone;
	private Role role;

}
