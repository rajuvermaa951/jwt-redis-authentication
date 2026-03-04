package com.jwt.redis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.redis.entity.Student;
import com.jwt.redis.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	private final StudentService studentService;
    
	
	public StudentController(StudentService studentService)
	{
		this.studentService=studentService;
	}
	
	@GetMapping("/{id}")
	public Student getStudent(@PathVariable long id)
	{
		return studentService.getStudent(id);
	}
}
