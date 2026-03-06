package com.jwt.redis.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenBlackListService {
	
	private final RedisTemplate<String,Object> redisTemplate;
	
	public TokenBlackListService(RedisTemplate<String,Object> redisTemplate)
	{
		this.redisTemplate=redisTemplate;
	}
	
	public void blacklistToken(String token)
	{
		
		//Token is blacklisted for one hour 
		redisTemplate.opsForValue().set(token, "blacklisted",Duration.ofHours(1));
	}

	public boolean isBlacklisted(String token)
	{
		return redisTemplate.hasKey(token);
	}
}
