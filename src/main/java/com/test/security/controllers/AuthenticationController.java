package com.test.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.security.dto.AuthResponseDTO;
import com.test.security.dto.LoginDTO;
import com.test.security.services.AuthenticationService;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/auth")
	public AuthResponseDTO authenticate(@RequestBody LoginDTO loginDTO) {
		return authenticationService.authenticate(loginDTO);
	}
}
