package com.example.employeelogin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeelogin.entity.Employee;

import com.example.employeelogin.service.EmployeeService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmpRestController {
	
private final EmployeeService employeeService;
   

    @GetMapping("/by-department")
    public List<Employee> getEmployeesByDepartmentName(@RequestParam String departmentName) {
        return employeeService.findByDepartmentName(departmentName);
    } 

}


