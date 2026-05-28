package com.ecommerce.auth.service;

import com.ecommerce.auth.dto.request.LoginDto;
import com.ecommerce.auth.dto.request.RegisterDto;
import com.ecommerce.auth.dto.response.LoginResponse;
import com.ecommerce.user.dto.response.UserResponseDto;


public interface AuthenticationService {

	public LoginResponse login(LoginDto loginDto);
	public UserResponseDto registerUser(RegisterDto registerDto);
	public LoginResponse refreshToken(LoginDto loginDto);
	public String logout();
	public String forgotPassword();
	public String resetPassword();
}
