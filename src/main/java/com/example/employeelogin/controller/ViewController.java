package com.example.employeelogin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


import com.example.employeelogin.FileUploadUtil;
import com.example.employeelogin.Helper;
import com.example.employeelogin.entity.Department;
import com.example.employeelogin.entity.Employee;
import com.example.employeelogin.entity.Skill;
import com.example.employeelogin.service.DepartmentService;
import com.example.employeelogin.service.EmployeeService;
import com.example.employeelogin.service.SkillService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

	@Autowired
	EmployeeService employeeService;
	DepartmentService departmentService;
	SkillService skillService;

	
	
	public ViewController(EmployeeService employeeService, DepartmentService departmentService,
			SkillService skillService) {
		super();
		this.employeeService = employeeService;
		this.departmentService = departmentService;
		this.skillService = skillService;
	}

	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@GetMapping("/index")
	public String index() {
	return "index";
		}
	
	
	@PostMapping("/login")
	public String logincheck(@RequestParam("email") String email,
			@RequestParam("password")String password,HttpSession session) 
	{
		Employee employee = employeeService.findByEmailAndPassword(email, password);
		if(employee!=null)
		{
			session.setAttribute("uname", employee.getFname()+ "    "+employee.getLname());
			session.setAttribute("fname", employee.getFname());
			session.setAttribute("eid", employee.getId());
			session.setAttribute("erole", employee.getRole());


			
			if(Helper.checkEmployeeRole())
			{
				session.setAttribute("msg", "You are succesfully login..");
				return "redirect:/profile";
			}
			else
		{
			session.setAttribute("msg","something went wrong");
			return "redirect:/logout";
		}
		}
			else
			{
				session.setAttribute("msg", "Wrong Username or password");
				return "redirect:/login";
			}
		}	

	

	//------------------------Profile -----------------------
	
	@GetMapping("/profile")
	public String profile(Model m,HttpSession session)
	{
		if(!Helper.checkEmployeeRole())
		{
			return "redirect:/login";
		}

		int eid = 0;
		if(session.getAttribute("eid") != null)
		{
			eid=(int)session.getAttribute("eid");
			
		}
	
		Employee employee=employeeService.getEmployeeById(eid);
		m.addAttribute("employee", employee);
		//session.removeAttribute("msg");
		
		return "profile";
	}
	
	
	//-----------------------Edit Profile-----------------------
	
	
	@GetMapping(value="/editprofile")
	public String editprofile(Model m)
	{
		List<Department> list=departmentService.listAllDepartments();
		m.addAttribute("listdepart", list);
		
		if(!Helper.checkEmployeeRole())
		{
			return "redirect:/login";
		}
		ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		int eid = 0;
		if(session.getAttribute("eid") != null)
		{
			eid=(int)session.getAttribute("eid");
			
		}

		Employee employee=employeeService.getEmployeeById(eid);
		m.addAttribute("employee", employee);

		return "edit_profile";
	}

	@PostMapping(value="/updateprofile")
	public String updateProfile(@ModelAttribute("employee") Employee employee,
			@RequestParam (value = "department_id",defaultValue = "0")int department_id,
			HttpSession session, Model m)
	{
		List<Department> list=departmentService.listAllDepartments();
		m.addAttribute("listdepart", list);
		
		Employee emp = employeeService.getEmployeeById(employee.getId());
		
		emp.setFname(employee.getFname());
		emp.setLname(employee.getLname());
		emp.setEmail(employee.getEmail());
		emp.setPhone(employee.getPhone());
		emp.setDepartment(employee.getDepartment());
		
		employeeService.updateEmployee(emp);
		
	
		session.setAttribute("msg", "Your profile edited...");
		return "redirect:/profile";
	}

	
//--------------------New Registration ----------------------
	@GetMapping("/signup")
	public String signup(Employee employee, Model m) {
		
		List<Department> list= departmentService.listAllDepartments();
		m.addAttribute("listdepartment",list );
		
		List<Skill> list1= skillService.listAllSkills();
		m.addAttribute("listskill",list1 );
		return "signup";
		}

	@PostMapping("/newsignup")
	public String newsignup(@ModelAttribute("employee") Employee employee,
			@RequestParam("image1") MultipartFile multipartFile,
            HttpSession session) throws IOException
	{
		//String imageFile = employee.getImage();
		
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
		
		session.setAttribute("msg", "Signup Successfully, you can login.....");
		return "redirect:/login";
	}
	
	//----------------------Forgot Password---------------------
	
	@GetMapping("/forgot_pass")
	public String forgot_pass(Employee employee) {
		
		return "forgot_pass";
		}

	//------------------------Update Password--------------------
	
	@PostMapping("/update_forgot_pass")
	public String forgotpass(@RequestParam("email") String email,
			 HttpSession session) 
	{
		Employee employee = employeeService.findByEmail(email);
	if(employee !=null)
	{
		session.setAttribute("email", email);
		return "redirect:/reset_pass";
	}else
	{
			session.setAttribute("msg","something went wrong");
			return "redirect:/forgot_pass";
		}
	}
	
	//------------------------Reset Pass---------------------
	@GetMapping("/reset_pass")
	public String reset() {
		
		return "reset_pass";
	}

	@PostMapping("/reset_pass1")
	public String resetpass(@RequestParam("password") String password,
			@RequestParam("cpassword") String cpassword, HttpSession session)
	{
		if(password.equals(cpassword))
		{
			String email = session.getAttribute("email").toString();
			Employee employee = employeeService.findByEmail(email);
			employee.setPassword(password);
			employee = employeeService.updateEmployee(employee);
			if(employee!=null)
			{
				session.setAttribute("msg","Password Reset");
				return "redirect:/login";
			}
			else
			{
				session.setAttribute("msg", "Something went wrong!!");
				return "redirect:/reset_pass";
			}
		}
			else
			{
				session.setAttribute("msg", "Password and confirm password not match");
				return "redirect:/reset_pass";
			}
		}


	
	
	//---------------------Logout--------------------------------
	@GetMapping("/logout")
	public String logout() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession();
		
		if(session.getAttribute("uname") != null)
		 session.removeAttribute("uname");

		if(session.getAttribute("eid") != null)
			 session.removeAttribute("eid");
			
		if(session.getAttribute("erole") != null)
			 session.removeAttribute("erole");
		
		
		session.setAttribute("msg", "You are successfully logout from system");
				return "redirect:/index";
		}

}
