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

import com.vaccinekrugger.dao.IVaccineDAO;
import com.vaccinekrugger.model.Vaccine;

@Service
public class VaccineCustomDAOImpl{

	@Autowired
	private IVaccineDAO iVaccineDAO;
	
	@PersistenceContext
	private EntityManager em;
	
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
	
	public Integer getIdVaccineByType(String strVaccineType) {
		StringBuilder strJPQL = new StringBuilder();
		
		try {
			strJPQL.append("  SELECT v.idVaccine ");
			strJPQL.append("  FROM 		Vaccine v");
			strJPQL.append("  WHERE 	v.type = :strVaccineType ");
			
			Query query = em.createQuery(strJPQL.toString());
			
			query.setParameter("pVaccineType", strVaccineType);
			
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
