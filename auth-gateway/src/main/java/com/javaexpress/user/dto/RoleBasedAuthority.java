package com.javaexpress.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum RoleBasedAuthority {

	ROLE_USER("USER"),
	ROLE_ADMIN("ADMIN");
	
	private final String role;

	public String getRole() {
		return role;
	}

	private RoleBasedAuthority(String role) {
		this.role = role;
	}
	
	
}
