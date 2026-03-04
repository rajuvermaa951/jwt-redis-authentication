package com.jwt.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.redis.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {

}
