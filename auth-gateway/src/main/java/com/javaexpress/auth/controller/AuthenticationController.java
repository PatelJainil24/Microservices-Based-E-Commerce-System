package com.javaexpress.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.auth.dto.AuthenticationRequest;
import com.javaexpress.auth.dto.AuthenticationResponse;
import com.javaexpress.auth.service.AuthenticationService;

@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping
	public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
		return authenticationService.authenticate(request);
	}
	
}
