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

import com.vaccinekrugger.dao.IDataEmployeeDAO;
import com.vaccinekrugger.model.DataEmployee;

@Service
public class DataEmployeeCustomDAOImpl{

	@Autowired
	private IDataEmployeeDAO iDataEmployeeDAO;
	
	@PersistenceContext
	private EntityManager em;
	
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
	
	public Integer getIdDataEmployeeByUser(Integer intIdDataEmployee) {
		StringBuilder strJPQL = new StringBuilder();
		
		try {
			strJPQL.append("  SELECT vd.idDataEmployee ");
			strJPQL.append("  FROM 		DataEmployee vd ");
			strJPQL.append("  WHERE 	vd.users.idUsers = :pUsers ");
			
			Query query = em.createQuery(strJPQL.toString());
			
			query.setParameter("pUsers", intIdDataEmployee);
			
			Integer intIdDataEmploye = (Integer) query.getSingleResult();
			
			if(!Objects.isNull(intIdDataEmploye)) {
				return intIdDataEmploye;
			}else {
				return null;
			}
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
