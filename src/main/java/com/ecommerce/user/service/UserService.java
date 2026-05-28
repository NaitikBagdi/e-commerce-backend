package com.ecommerce.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.user.dto.request.UserRequestDto;
import com.ecommerce.user.dto.response.UserResponseDto;

public interface UserService {

	public List<UserResponseDto> getAllUser();
	public ResponseEntity<String> deleteUserById(Long id) ;
	public UserResponseDto getUserById(Long id);
	public ResponseEntity<UserResponseDto> updateUser(UserRequestDto userRequestDto, Long id);
}
