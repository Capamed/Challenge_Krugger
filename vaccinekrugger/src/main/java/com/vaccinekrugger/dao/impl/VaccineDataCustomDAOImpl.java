package com.vaccinekrugger.dao.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinekrugger.dao.IVaccineDataDAO;
import com.vaccinekrugger.model.VaccineData;

@Service
public class VaccineDataCustomDAOImpl{

	@Autowired
	private IVaccineDataDAO iVaccineDataDAO;
	
	@PersistenceContext
	private EntityManager em;
	
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
	
	public Optional<VaccineData> findById(Integer idUserEmployee){
		return iVaccineDataDAO.findById(idUserEmployee);
	}

	
	public Integer getVaccineDataIdbyIdDataEmployee(Integer intIdDataEmployee) {
		StringBuilder strJPQL = new StringBuilder();
		
		try {
			strJPQL.append("  SELECT v.idVaccineData ");
			strJPQL.append("  FROM 		VaccineData vd");
			strJPQL.append("  WHERE 	vd.idDataEmployee = :pDataEmployee ");
			
			Query query = em.createQuery(strJPQL.toString());
			
			query.setParameter("pDataEmployee", intIdDataEmployee);
			
			Integer intIdVaccine = (Integer) query.getSingleResult();
			
			if(!Objects.isNull(intIdVaccine)) {
				return intIdVaccine;
			}else {
				return null;
			}
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
}
