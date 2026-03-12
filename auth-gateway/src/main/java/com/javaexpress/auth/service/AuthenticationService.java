package com.javaexpress.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javaexpress.auth.dto.AuthenticationRequest;
import com.javaexpress.auth.dto.AuthenticationResponse;
import com.javaexpress.user.jwt.JwtUtil;
import com.javaexpress.user.service.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// encoded pass in database and input is original password
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.getUsername());
		// TODO : jwt token generation
		if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
			throw new IllegalArgumentException("Bad Credentials");
		}
		String token = jwtUtil.generateToken(userDetails);
		return new AuthenticationResponse(token);
	}
	
}
