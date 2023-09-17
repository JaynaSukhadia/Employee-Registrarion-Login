package com.example.employeelogin.service;

import java.util.List;

import com.example.employeelogin.entity.Department;

public interface DepartmentService {

	
	List<Department> listAllDepartments();
	Department getDepartmentById(int id);
	Department insertDepartment(Department department);
	Department updateDepartment(Department department);
	int deleteDepartment(int id);
	
}
