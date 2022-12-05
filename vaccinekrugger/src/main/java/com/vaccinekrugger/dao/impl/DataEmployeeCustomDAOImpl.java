package com.vaccinekrugger.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaccinekrugger.dao.IDataEmployeeDAO;
import com.vaccinekrugger.model.DataEmployee;

public class DataEmployeeCustomDAOImpl{

	@Autowired
	private IDataEmployeeDAO iDataEmployeeDAO;
	
	public DataEmployee insert(DataEmployee dataEmployee) {
		return iDataEmployeeDAO.save(dataEmployee);
	}
	
	public DataEmployee update(DataEmployee dataEmployee) {
		return iDataEmployeeDAO.save(dataEmployee);
	}
	
	public List<DataEmployee> getAll(){
		return iDataEmployeeDAO.findAll();
	}
	
	public void delete(DataEmployee dataEmployee){
		iDataEmployeeDAO.delete(dataEmployee);
	}
	
	public Optional<DataEmployee> findById(Integer idUser){
		return iDataEmployeeDAO.findById(idUser);
	}
	
}
