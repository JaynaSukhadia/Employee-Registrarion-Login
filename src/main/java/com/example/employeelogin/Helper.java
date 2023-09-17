package com.example.employeelogin;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

public class Helper {
	public static boolean checkEmployeeRole()
	{
		String role="";
		ServletRequestAttributes attr=(ServletRequestAttributes) 
				RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		
		if(session.getAttribute("erole")!= null) {
			role = session.getAttribute("erole").toString();
			
		}
		return role.equalsIgnoreCase("Employee");
	}
	
	
}
