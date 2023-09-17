package com.example.employeelogin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.employeelogin.FileUploadUtil;
import com.example.employeelogin.entity.Department;
import com.example.employeelogin.entity.Employee;
import com.example.employeelogin.entity.Skill;
import com.example.employeelogin.service.DepartmentService;
import com.example.employeelogin.service.EmployeeService;
import com.example.employeelogin.service.SkillService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
@Autowired
	private EmployeeService employeeService;
	private DepartmentService departmentService;
	private SkillService skillService;
	
	
	public EmployeeController(EmployeeService employeeService, DepartmentService departmentService,
			SkillService skillService) {
		super();
		this.employeeService = employeeService;
		this.departmentService = departmentService;
		this.skillService = skillService;
	}
	
	
	@GetMapping("/template")
	public String template()
	{
		return "template";
	}
	
	
	@GetMapping("/list")
	public String listEmployee(Model m)
	{
		List<Employee> list=employeeService.listAllEmployees();
		
		m.addAttribute("list",list);
		return "emp/employee_list";
	}
	
	@GetMapping("/new")
	public String showform(Employee employee,Model m)
	{
		List<Department> list= departmentService.listAllDepartments();
		m.addAttribute("listdepartment",list );
		
		List<Skill> list1= skillService.listAllSkills();
		m.addAttribute("listskill",list1 );
		return "emp/employee_add";
	}
	
	@PostMapping("/insert")
	
	public String insert(Employee employee ,@RequestParam("image1") MultipartFile multipartFile) throws IOException
	{
	
	
	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
   
	
	employee.setRegDate(new Date());
	if(fileName.length() > 3 ) {
	employee.setImage(fileName);
     
	
    Employee savedEmployee = employeeService.insertEmployee(employee);

    String uploadDir = "./employee-photos/" + savedEmployee.getId();

    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	} 
    else
    {
 	   employee.setImage("noimg.png");
 	   Employee savedEmployee = employeeService.insertEmployee(employee);
    }
    
    return "redirect:/employee/list";
   
	}

	@GetMapping(value="/edit/{id}")
	public String edit(@PathVariable int id, Model m)
	{
		Employee employee= employeeService.getEmployeeById(id);
		m.addAttribute("employee", employee);
		
		List<Department> list= departmentService.listAllDepartments();
		m.addAttribute("listdepartment",list );
		
		List<Skill> list1= skillService.listAllSkills();
		m.addAttribute("listskill",list1 );
		
		return "emp/employee_edit";
		
	}
	
@PostMapping(value = "/update")
	
	public String update(Employee employee ,@RequestParam("image1") MultipartFile multipartFile) throws IOException
	{
	
		employee.setRegDate(new Date());
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	       
		if(fileName.length() > 3 ) {
		employee.setImage(fileName);
         
        Employee savedEmployee = employeeService.updateEmployee(employee);
 
        String uploadDir = "./employee-photos/" + savedEmployee.getId();
 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} 
        else
        {
     	   //employee.setImage("noimg.png");
     	   Employee savedEmployee = employeeService.updateEmployee(employee);
        }
        
        return "redirect:/employee/list";
       
		}
	
@GetMapping(value="/delete/{id}")
public String delete(@PathVariable int id)
{
	employeeService.deleteEmployee(id);
	return "redirect:/employee/list";
	
}
}
