package com.jwt.redis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
	private String username;
	
	private String password;

}
