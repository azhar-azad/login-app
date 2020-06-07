package com.dsi.codingtest.loginapp.services.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsi.codingtest.loginapp.io.entities.UserEntity;
import com.dsi.codingtest.loginapp.io.repositories.UserRepository;
import com.dsi.codingtest.loginapp.services.UserService;
import com.dsi.codingtest.loginapp.shared.Utils;
import com.dsi.codingtest.loginapp.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	ModelMapper modelMapper = new ModelMapper();
	
	private UserRepository userRepository;
	private Utils utils;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(
			UserRepository userRepository, 
			Utils utils,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		
		this.userRepository = userRepository;
		this.utils = utils;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDto) {

		if (userRepository.findByEmail(userDto.getEmail()) != null)
			throw new RuntimeException("Record already exists");
		
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		String publicUserId = utils.generateUserId(30); 
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

		UserEntity storedUserDetails = userRepository.save(userEntity); // save the created user

		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
		return returnValue;
	}
	
	

}
