package com.vaccinekrugger.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinekrugger.dao.IUsersDAO;
import com.vaccinekrugger.model.Users;

@Service
public class UsersCustomDAOImpl{

	@Autowired
	private IUsersDAO iUsersDAO;
	
	public Users insert(Users user) {
		return iUsersDAO.save(user);
	}
	
	public Users update(Users user) {
		return iUsersDAO.save(user);
	}
	
	public List<Users> getAll(){
		return iUsersDAO.findAll();
	}
	
	public void delete(Users user){
		iUsersDAO.delete(user);
	}
}
