package com.jwt.redis.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.jwt.redis.service.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private final JwtProperties jwtProperties;
	private final CustomUserDetailsService service;
	
	public JwtUtil(JwtProperties jwtProperties,
			       CustomUserDetailsService service)
	{
		this.jwtProperties=jwtProperties;
		this.service=service;
	}
	
	public String generateToken(String username,String role)
	{
		return Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+jwtProperties.getExpiration()))
				.signWith(getSignKey(),SignatureAlgorithm.HS256)
				.compact();
	}

	private SecretKey getSignKey() {
 
		byte[] keybytes=Decoders.BASE64.decode(jwtProperties.getKey());
		
		return Keys.hmacShaKeyFor(keybytes);
	}
	
	public Claims extractClaim(String token)
	{
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractUsername(String token)
	{
		return extractClaim(token).getSubject();
	}
	
	public boolean validateToken(String token,String username)
	{
		String tokenUsername=service.loadUserByUsername(username).getUsername();
		return tokenUsername.equals(username) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token)
				.getExpiration()
				.before(new Date());
			
	}

}
