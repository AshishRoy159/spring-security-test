package com.test.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.security.model.User;
import com.test.security.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
