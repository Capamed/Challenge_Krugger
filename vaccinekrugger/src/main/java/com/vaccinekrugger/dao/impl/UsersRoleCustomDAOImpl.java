package com.vaccinekrugger.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinekrugger.dao.IUsersRoleDAO;
import com.vaccinekrugger.model.UsersRole;

@Service
public class UsersRoleCustomDAOImpl{

	@Autowired
	private IUsersRoleDAO iUsersRoleDAO;
	
	@PersistenceContext
	private EntityManager em;
	
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
