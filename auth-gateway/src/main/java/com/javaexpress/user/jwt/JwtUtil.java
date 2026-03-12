package com.javaexpress.user.jwt;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUtil {

	String generateToken(UserDetails userDetails);
	
	String extractUsername(String token);
	
	Date extractExpiration(String token);
	
	Boolean validateToken(String token,UserDetails userDetails);
}
