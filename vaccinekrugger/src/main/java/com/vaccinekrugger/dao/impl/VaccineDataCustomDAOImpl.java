package com.vaccinekrugger.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaccinekrugger.dao.IVaccineDataDAO;
import com.vaccinekrugger.model.VaccineData;

public class VaccineDataCustomDAOImpl{

	@Autowired
	private IVaccineDataDAO iVaccineDataDAO;
	
	public VaccineData insert(VaccineData vaccineData) {
		return iVaccineDataDAO.save(vaccineData);
	}
	
	public VaccineData update(VaccineData vaccineData) {
		return iVaccineDataDAO.save(vaccineData);
	}
	
	public List<VaccineData> getAll(){
		return iVaccineDataDAO.findAll();
	}
	
	public void delete(VaccineData vaccineData){
		iVaccineDataDAO.delete(vaccineData);
	}
}
