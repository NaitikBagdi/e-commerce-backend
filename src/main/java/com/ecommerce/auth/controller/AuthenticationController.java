package com.ecommerce.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.dto.request.LoginDto;
import com.ecommerce.auth.dto.request.RegisterDto;
import com.ecommerce.auth.dto.response.LoginResponse;
import com.ecommerce.auth.service.AuthenticationService;
import com.ecommerce.user.dto.response.UserResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public LoginResponse Login(@RequestBody LoginDto loginDto) {
		return authenticationService.login(loginDto);
	}

	@PostMapping("/register")
	public UserResponseDto registerUser(@RequestBody RegisterDto registerDto) {
		return authenticationService.registerUser(registerDto);
	}

	public LoginResponse refreshToken(LoginDto loginDto) {
		return null;
	}

	public String logout() {
		return null;
	}

	public String forgotPassword() {
		return null;
	}

	public String resetPassword() {
		return null;
	}
}