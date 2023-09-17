package com.example.employeelogin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employeelogin.entity.Department;
import com.example.employeelogin.entity.Employee;
import com.example.employeelogin.repository.DepartmentRepository;
import com.example.employeelogin.repository.EmployeeRepository;
import com.example.employeelogin.service.EmployeeService;

@Service

public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;
	

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> listAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(int id) {
		
		return employeeRepository.findById(id).orElseThrow();
	}

	@Override
	public Employee insertEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		
		return employeeRepository.save(employee);
	}

	@Override
	public int deleteEmployee(int id) {
		employeeRepository.deleteById(id);
		return id;
	}

	@Override
	public Employee findByEmailAndPassword(String email, String password) {
		
		return employeeRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public Employee findByEmail(String email) {
		
		return employeeRepository.findByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		
		return employeeRepository.existsByEmail(email);
	}

	@Override
	public List<Employee> findByDepartmentName(String departmentName) {
		Department department =  this.departmentRepository.findByName(departmentName);
		return employeeRepository.findByDepartment(department);
	}

}
