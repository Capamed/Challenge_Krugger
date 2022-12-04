package com.vaccinekrugger.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinekrugger.bo.IUsersBO;
import com.vaccinekrugger.dao.impl.UsersCustomDAOImpl;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.model.Users;

@Service
public class EmployeeBOImpl implements IUsersBO {

	@Autowired
	private UsersCustomDAOImpl objUsersCustomDAOImpl ;
	
	@Override
	public List<Users> getAllEmployees() throws BOException {
		List<Users> objUsers = objUsersCustomDAOImpl.getAll();
		return null;
	}

}
