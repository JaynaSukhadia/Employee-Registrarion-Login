package com.example.employeelogin.service;

import java.util.List;

import com.example.employeelogin.entity.Skill;

public interface SkillService {

	
	List<Skill> listAllSkills();
	Skill getSkillById(int id);
	Skill insertSkill(Skill skill);
	Skill updateSkill(Skill skill);
	int deleteSkill(int id);
}
