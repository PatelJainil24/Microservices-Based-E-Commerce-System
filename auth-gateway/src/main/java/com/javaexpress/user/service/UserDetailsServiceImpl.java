package com.javaexpress.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javaexpress.user.dto.CredentialDto;
import com.javaexpress.user.dto.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private CredentialClientService credentialClientService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CredentialDto credentialDto = credentialClientService.findByUsername(username).getBody();
		return new UserDetailsImpl(credentialDto);
	}

}
