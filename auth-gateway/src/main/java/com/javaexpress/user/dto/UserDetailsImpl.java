package com.javaexpress.user.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetails{

	private CredentialDto credentialDto;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.info("Role {}",credentialDto.getRoleBasedAuthority().getRole());
		return List.of(new SimpleGrantedAuthority("ROLE_"+credentialDto.getRoleBasedAuthority().getRole()));
	}

	@Override
	public String getPassword() {
		return credentialDto.getPassword();
	}

	@Override
	public String getUsername() {
		return credentialDto.getUsername();
	}
	
}
