package com.example.employeelogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeelogin.entity.Skill;

public interface SkillRepository extends JpaRepository<Skill, Integer>{

}
