package com.vaccinekrugger.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccinekrugger.model.Role;

@Repository
public interface IRoleDAO extends JpaRepository<Role, Integer>{

}
