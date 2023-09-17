package com.example.employeelogin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeelogin.entity.Skill;
import com.example.employeelogin.repository.SkillRepository;
import com.example.employeelogin.service.SkillService;
@Service
public class SkillServiceImpl implements SkillService{

	SkillRepository repo;
	
	
	public SkillServiceImpl(SkillRepository repo) {
		super();
		this.repo = repo;
	}

	public List<Skill> listAllSkills() {
		
		return repo.findAll();
	}

	@Override
	public Skill getSkillById(int id) {
		
		return repo.findById(id).orElseThrow();
	}

	@Override
	public Skill insertSkill(Skill skill) {
		return repo.save(skill);
		
	}

	@Override
	public Skill updateSkill(Skill skill) {
		
		return repo.save(skill);
	}

	@Override
	public int deleteSkill(int id) {
		repo.deleteById(id);
		return id;
	}

}
