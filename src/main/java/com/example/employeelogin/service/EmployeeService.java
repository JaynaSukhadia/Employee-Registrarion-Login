package com.example.employeelogin.service;

import java.util.List;

import com.example.employeelogin.entity.Employee;


public interface EmployeeService {



	List<Employee> listAllEmployees();
	Employee getEmployeeById(int id);
	Employee insertEmployee(Employee employee);
	Employee updateEmployee(Employee employee);
	int deleteEmployee(int id);
	
	Employee findByEmailAndPassword(String email, String password);
	Employee  findByEmail(String email);
	boolean existsByEmail(String email);
	
	//List<Employee> findByDepartment(String department);
	List<Employee> findByDepartmentName(String department);
	
}
