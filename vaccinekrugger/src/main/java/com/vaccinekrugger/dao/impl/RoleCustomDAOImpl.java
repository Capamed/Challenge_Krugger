package com.vaccinekrugger.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaccinekrugger.dao.IRoleDAO;
import com.vaccinekrugger.model.Role;

public class RoleCustomDAOImpl{

	@Autowired
	private IRoleDAO iRoleDAO;
	
	public Role insert(Role role) {
		return iRoleDAO.save(role);
	}
	
	public Role update(Role role) {
		return iRoleDAO.save(role);
	}
	
	public List<Role> getAll(){
		return iRoleDAO.findAll();
	}
	
	public void delete(Role role){
		iRoleDAO.delete(role);
	}
}
