package com.example.employeelogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeelogin.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	Department findByName(String name);
	
}
