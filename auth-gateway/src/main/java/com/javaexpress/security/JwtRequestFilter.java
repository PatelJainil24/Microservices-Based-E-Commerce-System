package com.javaexpress.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javaexpress.user.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("doFilterInternal ");
		String header = request.getHeader("Authorization");
		String jwtToken = null;
		String username= null;
		
		if(header != null && header.startsWith("Bearer ")) {
			log.info("Fetching token from request object in filter");
			jwtToken = header.substring(7);
			username = jwtUtil.extractUsername(jwtToken);
		}
		
		if(username != null) {
			UserDetails dbUserDetails = userDetailsService.loadUserByUsername(username);
			
			if(jwtUtil.validateToken(jwtToken, dbUserDetails)) {
				log.info("validateToken in filter");
				UsernamePasswordAuthenticationToken obj =  new 
						UsernamePasswordAuthenticationToken(dbUserDetails, null,dbUserDetails.getAuthorities());
				obj.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(obj);
				//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//				if (auth != null) {
//				    auth.getAuthorities().forEach(a -> System.out.println("Authority: " + a.getAuthority()));
//				}
			}
		}
		
		
		filterChain.doFilter(request, response);
	}

	
}
