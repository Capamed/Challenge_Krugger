package com.vaccinekrugger.bo;

import java.util.List;

import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.model.Users;

public interface IUsersBO {
	public List<Users> getAllEmployees() throws BOException;

}
