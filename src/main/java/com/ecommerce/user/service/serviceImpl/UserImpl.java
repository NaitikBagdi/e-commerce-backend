package com.ecommerce.user.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.annotation.IsAdmin;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.user.dto.request.UserRequestDto;
import com.ecommerce.user.dto.response.UserResponseDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

	private final UserRepository userReporistory;

	private final ModelMapper modelMapper;

	@Override
	@IsAdmin
	public List<UserResponseDto> getAllUser() {
		List<User> userList = userReporistory.findAll();
		return  userList.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());
	}

	@Override
	@IsAdmin
	public ResponseEntity<String> deleteUserById(Long id) {
		userReporistory.deleteById(id);
		return ResponseEntity.ok("Record deleted successsfull!");
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		User user = userReporistory.findById(id).orElseThrow(() -> new RuntimeException("User not fount!"));
		UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
		return userResponseDto;
	}

	@Override
	public ResponseEntity<UserResponseDto> updateUser(UserRequestDto userRequestDto, Long id) {
		if (id == null) throw new ResourceNotFoundException("Id can not be null");
		User user = modelMapper.map(userRequestDto, User.class);
		user = userReporistory.save(user);
		UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
		return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
	}
}
