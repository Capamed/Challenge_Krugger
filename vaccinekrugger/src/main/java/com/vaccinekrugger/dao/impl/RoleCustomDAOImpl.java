package com.vaccinekrugger.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaccinekrugger.dao.IRoleDAO;
import com.vaccinekrugger.model.Role;

@Service
public class RoleCustomDAOImpl{

	@Autowired
	private IRoleDAO iRoleDAO;
	
	@PersistenceContext
	private EntityManager em;
	
	public Role insert(Role role) {
		return iRoleDAO.save(role);
	}
	
	public Role update(Role role) {
		return iRoleDAO.save(role);
	}
	
	public List<Role> getAll(){
		return iRoleDAO.findAll();
	}
	
	public void delete(Role role){
		iRoleDAO.delete(role);
	}
	
	public Role getByRoleName(String Role){
		try {
			return em.createQuery(
						"SELECT us\n" +
						"FROM 	Role us \n"+
						"WHERE  us.role = :pRole \n",Role.class)
						.setParameter("pRole", Role)
						.getSingleResult();
			} catch(NoResultException e) {
				return null;
			}
	}
	
}
