package com.example.employeelogin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeelogin.entity.Department;
import com.example.employeelogin.repository.DepartmentRepository;
import com.example.employeelogin.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	
	DepartmentRepository repo;
	
	
	public DepartmentServiceImpl(DepartmentRepository repo) {
		super();
		this.repo = repo;
	}

	public List<Department> listAllDepartments() {
		
		return repo.findAll();
	}

	@Override
	public Department getDepartmentById(int id) {
		
		return repo.findById(id).orElseThrow();
	}

	@Override
	public Department insertDepartment(Department department) {
		
		return repo.save(department);
	}

	@Override
	public Department updateDepartment(Department department) {
		
		return repo.save(department);
	}

	@Override
	public int deleteDepartment(int id) {
		repo.deleteById(id);
		return id;
	}

}
