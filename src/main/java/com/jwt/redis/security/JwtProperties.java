package com.jwt.redis.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix="jwt")
@Component
@Data
public class JwtProperties {
	
	
	private long expiration;
	
	private String key;

}
