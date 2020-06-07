package com.dsi.codingtest.loginapp.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public UserServiceImpl(UserRepository userRepository, Utils utils) {
		this.userRepository = userRepository;
		this.utils = utils;
	}

	@Override
	public UserDto createUser(UserDto userDto) {

		if (userRepository.findByEmail(userDto.getEmail()) != null)
			throw new RuntimeException("Record already exists");
		
		UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);

		String publicUserId = utils.generateUserId(30); 
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptedPassword("enc-pass");
//		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

		UserEntity storedUserDetails = userRepository.save(userEntity); // save the created user

		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);
		return returnValue;
	}
	
	

}
