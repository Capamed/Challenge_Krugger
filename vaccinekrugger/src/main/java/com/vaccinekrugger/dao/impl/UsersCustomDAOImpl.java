package com.vaccinekrugger.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.vaccinekrugger.dao.IUsersDAO;
import com.vaccinekrugger.dto.ResponseUsersFiltersDTO;
import com.vaccinekrugger.model.Users;

@Service
public class UsersCustomDAOImpl{

	@Autowired
	private IUsersDAO iUsersDAO;
	
	@PersistenceContext
	private EntityManager em;
	
	
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
	
	public List <ResponseUsersFiltersDTO> getEmployeesToFilters(String strVaccineState, String strVaccineType, Date strVaccineDateStart,Date strVaccineDateEnd) {
		
		StringBuilder strJPQL = new StringBuilder();
		try {
			strJPQL.append(" SELECT usr.identification as identification, ");
			strJPQL.append("usr.username as username, ");
			strJPQL.append("usr.password as password, ");
			strJPQL.append("usr.first_name as firstName, ");
			strJPQL.append("usr.last_name as lastName, ");
			strJPQL.append("usr.mail as mail, ");
			strJPQL.append("usr.state as state, ");
			strJPQL.append("de.date_birth as dateBirth, ");
			strJPQL.append("de.address as address, ");
			strJPQL.append("de.mobile as mobile, ");			
			strJPQL.append("de.vaccination_state as vaccinationState, ");
			strJPQL.append("vd.vaccine_date as vaccineDate, ");
			strJPQL.append("vd.number_dose as numberDose, ");
			strJPQL.append("v.type as type ");
			strJPQL.append(" FROM USERS usr ");
			strJPQL.append(" INNER JOIN users_role usro ON  usr.id_users = usro.id_users ");
			strJPQL.append(" INNER JOIN DATA_EMPLOYEE de ON usr.id_users = de.id_users ");
			strJPQL.append(" INNER JOIN VACCINE_DATA vd ON de.id_data_employee = vd.id_data_employee ");
			strJPQL.append(" INNER JOIN VACCINE v ON vd.id_vaccine = v.id_vaccine ");
			strJPQL.append(" WHERE usro.id_role = :pCodeRole ");
			
			if(!ObjectUtils.isEmpty(strVaccineState) || !Objects.isNull(strVaccineState)) {				
				strJPQL.append(" AND de.vaccination_state = :pVaccinationState ");
			}
			
			if(!ObjectUtils.isEmpty(strVaccineType) || !Objects.isNull(strVaccineType)) {				
				strJPQL.append(" AND v.type = :pVaccineType ");
			}
			
			if((!ObjectUtils.isEmpty(strVaccineDateStart) || !Objects.isNull(strVaccineDateStart)) && (!ObjectUtils.isEmpty(strVaccineDateEnd) || !Objects.isNull(strVaccineDateEnd))) {				
				strJPQL.append(" AND vd.vaccine_date BETWEEN :pDateStart AND :pDateEnd ");
			}
			
			@SuppressWarnings("unchecked")
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createNativeQuery(strJPQL.toString(), Tuple.class);
			query.setParameter("pCodeRole", 2);
			
			if(!ObjectUtils.isEmpty(strVaccineState) || !Objects.isNull(strVaccineState)) {				
				query.setParameter("pVaccinationState", strVaccineState);
			}
			
			if(!ObjectUtils.isEmpty(strVaccineType) || !Objects.isNull(strVaccineType)) {				
				query.setParameter("pVaccineType", strVaccineType);
			}
			
			if((!ObjectUtils.isEmpty(strVaccineDateStart) || !Objects.isNull(strVaccineDateStart)) && (!ObjectUtils.isEmpty(strVaccineDateEnd) || !Objects.isNull(strVaccineDateEnd))) {				
				query.setParameter("pDateStart", strVaccineDateStart);
				query.setParameter("pDateEnd", strVaccineDateEnd);
			}
			
			List<Tuple> lsResult = query.getResultList();
			if (lsResult != null) {
				ResponseUsersFiltersDTO objResponseUsersFilters = null;
				List<ResponseUsersFiltersDTO> lstResponseUsersFilters = new ArrayList<ResponseUsersFiltersDTO>();
				for (Tuple objArr : lsResult) {
					objResponseUsersFilters = new ResponseUsersFiltersDTO();
					objResponseUsersFilters.setIdentification(objArr.get("identification", String.class).toString());
					objResponseUsersFilters.setUsername(objArr.get("username", String.class).toString());
					objResponseUsersFilters.setPassword(objArr.get("password", String.class).toString());
					objResponseUsersFilters.setFirstName(objArr.get("firstName", String.class).toString());
					objResponseUsersFilters.setLastName(objArr.get("lastName", String.class).toString());
					objResponseUsersFilters.setMail(objArr.get("mail", String.class).toString());
					objResponseUsersFilters.setDateBirth(objArr.get("dateBirth", Date.class).toString());
					objResponseUsersFilters.setAddress(objArr.get("address", String.class).toString());
					objResponseUsersFilters.setMobile(objArr.get("mobile", String.class).toString());
					objResponseUsersFilters.setVaccinationState(objArr.get("vaccinationState", String.class).toString());
					objResponseUsersFilters.setVaccineDate(objArr.get("vaccineDate", Date.class).toString());
					objResponseUsersFilters.setNumberDose(objArr.get("numberDose", Number.class).intValue());
					objResponseUsersFilters.setState(objArr.get("state", String.class).toString());
					objResponseUsersFilters.setType(objArr.get("type", String.class).toString());
	
					lstResponseUsersFilters.add(objResponseUsersFilters);
				}

				return lstResponseUsersFilters;

			}
		} catch (NoResultException e) {
			return null;
		}
		return null;
	}
	
	
public List <ResponseUsersFiltersDTO> getEmployeeByIdentification(String strIdentification) {
		
		StringBuilder strJPQL = new StringBuilder();
		try {
			strJPQL.append(" SELECT usr.identification as identification, ");
			strJPQL.append("usr.username as username, ");
			strJPQL.append("usr.password as password, ");
			strJPQL.append("usr.first_name as firstName, ");
			strJPQL.append("usr.last_name as lastName, ");
			strJPQL.append("usr.mail as mail, ");
			strJPQL.append("usr.state as state, ");
			strJPQL.append("de.date_birth as dateBirth, ");
			strJPQL.append("de.address as address, ");
			strJPQL.append("de.mobile as mobile, ");			
			strJPQL.append("de.vaccination_state as vaccinationState, ");
			strJPQL.append("vd.vaccine_date as vaccineDate, ");
			strJPQL.append("vd.number_dose as numberDose, ");
			strJPQL.append("v.type as type ");
			strJPQL.append(" FROM USERS usr ");
			strJPQL.append(" INNER JOIN users_role usro ON  usr.id_users = usro.id_users ");
			strJPQL.append(" INNER JOIN DATA_EMPLOYEE de ON usr.id_users = de.id_users ");
			strJPQL.append(" INNER JOIN VACCINE_DATA vd ON de.id_data_employee = vd.id_data_employee ");
			strJPQL.append(" INNER JOIN VACCINE v ON vd.id_vaccine = v.id_vaccine ");
			strJPQL.append(" WHERE usro.id_role = :pCodeRole ");
			strJPQL.append(" AND usr.identification = :pIdentification ");
			

			@SuppressWarnings("unchecked")
			TypedQuery<Tuple> query = (TypedQuery<Tuple>) em.createNativeQuery(strJPQL.toString(), Tuple.class);
			query.setParameter("pCodeRole", 2);
			query.setParameter("pIdentification", strIdentification);
			
			
			List<Tuple> lsResult = query.getResultList();
			if (lsResult != null) {
				ResponseUsersFiltersDTO objResponseUsersFilters = null;
				List<ResponseUsersFiltersDTO> lstResponseUsersFilters = new ArrayList<ResponseUsersFiltersDTO>();
				for (Tuple objArr : lsResult) {
					objResponseUsersFilters = new ResponseUsersFiltersDTO();
					objResponseUsersFilters.setIdentification(objArr.get("identification", String.class).toString());
					objResponseUsersFilters.setUsername(objArr.get("username", String.class).toString());
					objResponseUsersFilters.setPassword(objArr.get("password", String.class).toString());
					objResponseUsersFilters.setFirstName(objArr.get("firstName", String.class).toString());
					objResponseUsersFilters.setLastName(objArr.get("lastName", String.class).toString());
					objResponseUsersFilters.setMail(objArr.get("mail", String.class).toString());
					objResponseUsersFilters.setDateBirth(objArr.get("dateBirth", Date.class).toString());
					objResponseUsersFilters.setAddress(objArr.get("address", String.class).toString());
					objResponseUsersFilters.setMobile(objArr.get("mobile", String.class).toString());
					objResponseUsersFilters.setVaccinationState(objArr.get("vaccinationState", String.class).toString());
					objResponseUsersFilters.setVaccineDate(objArr.get("vaccineDate", Date.class).toString());
					objResponseUsersFilters.setNumberDose(objArr.get("numberDose", Number.class).intValue());
					objResponseUsersFilters.setState(objArr.get("state", String.class).toString());
					objResponseUsersFilters.setType(objArr.get("type", String.class).toString());
	
					lstResponseUsersFilters.add(objResponseUsersFilters);
				}

				return lstResponseUsersFilters;

			}
		} catch (NoResultException e) {
			return null;
		}
		return null;
	}


	public Boolean verifyExistUser(String strIdentification) {
	
		StringBuilder strJPQL = new StringBuilder();
		
		try {
			strJPQL.append("  SELECT Count(1) ");
			strJPQL.append("  FROM Users usr ");
			strJPQL.append("  WHERE usr.identification = :pIdentification");
			
			Query query = em.createQuery(strJPQL.toString());
			
			query.setParameter("pIdentification", strIdentification);
			
			long lngResult = (long) query.getSingleResult();
			
			if(lngResult > 0) {
				return true;
			}else {
				return false;
			}
			
		} catch (NoResultException e) {
			return true;
		}
	}
}
