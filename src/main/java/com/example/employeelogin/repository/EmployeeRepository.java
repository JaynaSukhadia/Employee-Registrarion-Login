package com.example.employeelogin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeelogin.entity.Department;
import com.example.employeelogin.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Employee findByEmailAndPassword(String email, String password);
	Employee  findByEmail(String email);
	boolean existsByEmail(String email);
	
	List<Employee> findByDepartment(Department department);
}
