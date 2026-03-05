package com.jwt.redis.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.redis.entity.User;
import com.jwt.redis.repository.UserRepository;
import com.jwt.redis.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository)
	{
		this.userRepository=userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username  not found"));
		return new CustomUserDetails(user);
	}

}
