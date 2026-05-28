package com.ecommerce.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.annotation.IsAdmin;
import com.ecommerce.common.ApiResponse;
import com.ecommerce.user.dto.response.UserResponseDto;
import com.ecommerce.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

	private final UserService userService;

	@GetMapping("/user")
	@IsAdmin
	public ApiResponse<List<UserResponseDto>> getAllUser() {
		return ApiResponse.<List<UserResponseDto>>builder().success(true).message("Users fetched successfully")
				.data(userService.getAllUser()).build();
	}

	@DeleteMapping("/user/{id}")
	@IsAdmin
	public ApiResponse<String> deleteUser(@RequestParam Long id) {
		return ApiResponse.<String>builder().success(true).message("User deleted successfully").build();

	}

	@GetMapping("/user/{id}")
	public ApiResponse<UserResponseDto> getUserById(@PathVariable Long id) {
		return ApiResponse.<UserResponseDto>builder().success(true).message("Users fetched successfully")
				.data(userService.getUserById(id)).build();
	}
}
