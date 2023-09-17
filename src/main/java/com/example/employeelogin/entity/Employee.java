package com.example.employeelogin.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="empyoyees")


public class Employee {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",nullable = false)
	private int id;
	
	@Column(name="image",nullable = false)
	private String image;
	
	@Transient
	public String getPhotosImagePath()
	{
		if(image==null || id==0)return null;
		return "/employee-photos/" + id + "/" +image;
	}
	
	@Column(name="fname", nullable = false)
	private String fname;
	
	@Column(name="lname",nullable = false)
	private String lname;
	
	@Column(name="email", nullable = false)
	private String email;
	
	@Column(name="password", nullable =  false)
	private String password;
	
	@Column(name="gender", nullable=false)
	private String gender;
	
	@Column(name="phone", nullable =  false)
	private String phone;
	
	@Column(name="role", nullable =  false)
	private String role;
	
	@Column(name="reg_date",nullable = false)
	@Temporal(TemporalType.DATE)
	private Date regDate;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinColumn(name="department_id", referencedColumnName = "id", nullable = false)
	@JsonIgnore
	private Department department;
	
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "employees_skills",
            joinColumns = { @JoinColumn(name = "employee_id",referencedColumnName = "id",
            nullable = false, updatable = false)},
            inverseJoinColumns = { @JoinColumn(name = "skill_id", referencedColumnName = "id",
            nullable = false, updatable = false)})
     @JsonIgnore     
    private Set<Skill> skills = new HashSet<>();

	
	@Column(name="salary",nullable = false)
	private Float salary;
	
}
