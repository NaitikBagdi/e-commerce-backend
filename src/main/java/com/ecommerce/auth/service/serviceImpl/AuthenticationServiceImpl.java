package com.ecommerce.auth.service.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.dto.request.LoginDto;
import com.ecommerce.auth.dto.request.RegisterDto;
import com.ecommerce.auth.dto.response.LoginResponse;
import com.ecommerce.auth.service.AuthenticationService;
import com.ecommerce.authconfig.CustomUserDetailsService;
import com.ecommerce.authconfig.JwtService;
import com.ecommerce.enums.Role;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.user.dto.response.UserResponseDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	private final UserRepository userReporistory;

	private final CustomUserDetailsService customUserDetailsService;

	private final PasswordEncoder passwordEncoder;

	private final ModelMapper modelMapper;

	@Override
	public LoginResponse login(LoginDto loginDto) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		customUserDetailsService.loadUserByUsername(loginDto.getEmail());
		User user = userReporistory.findByEmail(loginDto.getEmail()).orElseThrow();
		String token = jwtService.generateToken(user);
		return new LoginResponse(token, "", user.getRoles().name());
	}
	
	@Override
	public UserResponseDto registerUser(RegisterDto registerDto) {
		if (userReporistory.existsByEmail(registerDto.getEmail())) throw new ResourceNotFoundException ("email already exist");
		User user = modelMapper.map(registerDto,User.class);
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setRoles(Role.ROLE_CUSTOMER);
		user = userReporistory.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public LoginResponse refreshToken(LoginDto loginDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String logout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forgotPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String resetPassword() {
		// TODO Auto-generated method stub
		return null;
	}

}
