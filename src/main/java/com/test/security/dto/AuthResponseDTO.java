package com.test.security.dto;

public class AuthResponseDTO {

	public enum AuthType {
		BASIC, JWT, CUSTOM
	}

	private AuthType authType;
	private String token;

	public AuthResponseDTO(AuthType authType, String token) {
		super();
		this.authType = authType;
		this.token = token;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
