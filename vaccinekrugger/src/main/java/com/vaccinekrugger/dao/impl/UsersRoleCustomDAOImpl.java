package com.vaccinekrugger.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaccinekrugger.dao.IUsersRoleDAO;
import com.vaccinekrugger.model.UsersRole;

public class UsersRoleCustomDAOImpl{

	@Autowired
	private IUsersRoleDAO iUsersRoleDAO;
	
	public UsersRole insert(UsersRole userRole) {
		return iUsersRoleDAO.save(userRole);
	}
	
	public UsersRole update(UsersRole userRole) {
		return iUsersRoleDAO.save(userRole);
	}
	
	public List<UsersRole> getAll(){
		return iUsersRoleDAO.findAll();
	}
	
	public void delete(UsersRole userRole){
		iUsersRoleDAO.delete(userRole);
	}
}