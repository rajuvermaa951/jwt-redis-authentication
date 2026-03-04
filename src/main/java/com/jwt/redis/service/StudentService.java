package com.jwt.redis.service;

import org.springframework.stereotype.Service;

import com.jwt.redis.entity.Student;
import com.jwt.redis.repository.StudentRepository;

@Service
public class StudentService {

	
	private final StudentRepository studentRepository;
	private final StudentCacheService cacheService;
	
	public StudentService(StudentRepository studentRepository,
			              StudentCacheService cacheService)
	{
		this.studentRepository=studentRepository;
		this.cacheService=cacheService;

	}
	
	public Student getStudent(long id)
	{
		Student cachedStudent = cacheService.getStuedentFromCache(id);
		if(cachedStudent!=null)
		{
			System.out.println("Fetched from Cache Service ");
			return cachedStudent;
		}
		System.out.println("fetched from database ...");
		Student student=studentRepository.findById(id).orElseThrow();
		
		cacheService.saveStudent(student);
		
		return student;
				
	}
	
}
