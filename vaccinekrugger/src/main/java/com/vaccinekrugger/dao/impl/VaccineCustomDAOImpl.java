package com.vaccinekrugger.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaccinekrugger.dao.IVaccineDAO;
import com.vaccinekrugger.model.Vaccine;

public class VaccineCustomDAOImpl{

	@Autowired
	private IVaccineDAO iVaccineDAO;
	
	public Vaccine insert(Vaccine vaccine) {
		return iVaccineDAO.save(vaccine);
	}
	
	public Vaccine update(Vaccine vaccine) {
		return iVaccineDAO.save(vaccine);
	}
	
	public List<Vaccine> getAll(){
		return iVaccineDAO.findAll();
	}
	
	public void delete(Vaccine vaccine){
		iVaccineDAO.delete(vaccine);
	}
	
	public Optional<Vaccine> findById(Integer id) {
		return iVaccineDAO.findById(id);
	}
}
