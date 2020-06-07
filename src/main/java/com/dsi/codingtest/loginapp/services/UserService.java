package com.dsi.codingtest.loginapp.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dsi.codingtest.loginapp.shared.dto.UserDto;

public interface UserService extends UserDetailsService {

	UserDto createUser(UserDto userDto);

}
