package com.example.employeelogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.employeelogin.entity.Department;
import com.example.employeelogin.service.DepartmentService;


@Controller
@RequestMapping("/department")
public class DepartmentController {
@Autowired
	
	DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		super();
		this.departmentService = departmentService;
	}
	@GetMapping("/list")
	public String listDepartment(Model m)
	{
  	List<Department> list = departmentService.listAllDepartments();
  	m.addAttribute("list", list);
  	return "dept/department_list";
  	
	}
	@GetMapping("/new")
	public String showForm(Department department)
	{
		return "dept/department_add";
	}
	
	@PostMapping("/insert")
	
		public String insert(@ModelAttribute("department") Department department)
		{
			departmentService.insertDepartment(department);
			return "redirect:/department/list";
		}
	
	@GetMapping(value="/edit/{id}")
	public String edit(@PathVariable int id, Model m)
	{
		Department department= departmentService.getDepartmentById(id);
		m.addAttribute("department", department);
		return "dept/department_edit";
		
	}
	
	@PostMapping(value = "/update")
	public String update(@ModelAttribute("department") Department department)
	{
		departmentService.updateDepartment(department);
		return "redirect:/department/list";
		
	}
	
	@GetMapping(value="/delete/{id}")
	public String delete(@PathVariable int id, Model m)
	{
		departmentService.deleteDepartment(id);
		return "redirect:/department/list";
		
	}
}
