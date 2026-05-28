package com.ecommerce.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

	private String name;
	private String address;
	private String email;
	private String password;
	private Long phone;

}
