package com.jwt.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jwt.redis.entity.Student;

@Service
public class StudentCacheService {
	
	private final RedisTemplate<String,Object> redisTemplate;
	
	public StudentCacheService(RedisTemplate<String,Object> redisTemplate)
	{
		this.redisTemplate=redisTemplate;
	}
	
	public Student getStuedentFromCache(long id)
	{
		return (Student) redisTemplate.opsForValue().get("student:"+id);
	}
	
	public void saveStudent(Student student)
	{
		redisTemplate.opsForValue().set("student:"+student.getId(), student);
	}

}
