package com.jwt.redis.controller;

import org.springframework.http.ResponseEntity;
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
import com.jwt.redis.service.TokenBlackListService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;
	private final TokenBlackListService tokenBlacklistService;
	
	public AuthController(AuthenticationManager authenticationManager,
			              JwtUtil jwtUtil,
			              CustomUserDetailsService userDetailsService, 
			              TokenBlackListService tokenBlacklistService)
	{
		this.authenticationManager=authenticationManager;
		this.userDetailsService=userDetailsService;
		this.jwtUtil=jwtUtil;
		this.tokenBlacklistService=tokenBlacklistService;
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

	    try {

	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getUsername(),
	                        loginRequest.getPassword()
	                )
	        );

	        UserDetails userDetails =
	                userDetailsService.loadUserByUsername(loginRequest.getUsername());

	        String token = jwtUtil.generateToken(
	                userDetails.getUsername(),
	                userDetails.getAuthorities()
	                        .iterator()
	                        .next()
	                        .getAuthority()
	        );

	        return ResponseEntity.ok(new AuthResponse(token));

	    } catch (Exception e) {
	        return ResponseEntity.status(401).body("Invalid username or password");
	    }
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {

	    String authHeader = request.getHeader("Authorization");

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {

	        String token = authHeader.substring(7);

	        tokenBlacklistService.blacklistToken(token);

	        return ResponseEntity.ok("Logged out successfully");
	    }

	    return ResponseEntity.status(401).body("No valid token found");
	}
}
