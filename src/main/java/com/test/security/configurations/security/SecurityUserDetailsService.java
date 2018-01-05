package com.test.security.configurations.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.test.security.model.User;
import com.test.security.services.UserService;

@Component
public class SecurityUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userService.getUserByEmail(email);
		if (user == null) {
			throw new BadCredentialsException("Bad Credentials");
		}

		return new SecurityUserDetails(user);
	}

}
