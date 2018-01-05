package com.test.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.test.security.dto.AuthResponseDTO;
import com.test.security.dto.AuthResponseDTO.AuthType;
import com.test.security.dto.LoginDTO;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthResponseDTO authenticate(LoginDTO loginDTO) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = "authToken";

		return new AuthResponseDTO(AuthType.CUSTOM, token);
	}
}
