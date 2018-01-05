package com.test.security.configurations.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.test.security.model.User;

public class SecurityUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;

	public SecurityUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<String> roles = this.user.getUserRoles().stream().map(userRole -> userRole.getRole().getRole())
				.collect(Collectors.toList());
		return AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()]));
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.isActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.user.isActive();
	}

	@Override
	public boolean isEnabled() {
		return this.user.isActive();
	}

}
