package com.jwt.redis.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.redis.dto.AuthResponse;
import com.jwt.redis.dto.LoginRequest;
import com.jwt.redis.security.JwtUtil;
import com.jwt.redis.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;
	
	public AuthController(AuthenticationManager authenticationManager,
			              JwtUtil jwtUtil,
			              CustomUserDetailsService userDetailsService)
	{
		this.authenticationManager=authenticationManager;
		this.userDetailsService=userDetailsService;
		this.jwtUtil=jwtUtil;
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest loginRequest)
	{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				                                                                   loginRequest.getPassword()
				));
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
		
		String token=jwtUtil.generateToken(userDetails.getUsername(),
				                           userDetails.getAuthorities()
				                           .iterator()
				                           .next()
				                           .getAuthority());
		
		return new AuthResponse(token);
				
				
	}

}
