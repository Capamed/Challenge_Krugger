package com.vaccinekrugger.bo.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;

import com.vaccinekrugger.bo.IUsersBO;
import com.vaccinekrugger.dao.impl.UsersCustomDAOImpl;
import com.vaccinekrugger.dao.impl.VaccineCustomDAOImpl;
import com.vaccinekrugger.dto.RequestCreateEmployeeDTO;
import com.vaccinekrugger.dto.ResponseUsersFiltersDTO;
import com.vaccinekrugger.enums.VaccineState;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.exceptions.CustomExceptionHandler;
import com.vaccinekrugger.model.DataEmployee;
import com.vaccinekrugger.model.Users;
import com.vaccinekrugger.model.VaccineData;
import com.vaccinekrugger.utils.FunctionsUtil;

@Service
public class EmployeeBOImpl implements IUsersBO {

	@Autowired
	private UsersCustomDAOImpl objUsersCustomDAOImpl;
	
	@Autowired
	private VaccineCustomDAOImpl objVaccineCustomDAOImpl;
	
	@Override
	public List<ResponseUsersFiltersDTO> getAllEmployees(String strVaccineState, String strVaccineType, String strCaccineDateStart,String strVaccineDateEnd) throws BOException, ParseException {
		//Validate vaccine state
		if(!ObjectUtils.isEmpty(strVaccineState) || !Objects.isNull(strVaccineState)) {
			VaccineState[] vaccineStates = VaccineState.values();
			if(!Arrays.stream(vaccineStates).anyMatch(tmp -> tmp.getName().equals(strVaccineState))) {
				throw new BOException("project.warn.fieldNoExist", new Object[] { strVaccineState });
			}
		}
		
		//Validate vaccine date start
		if(!ObjectUtils.isEmpty(strCaccineDateStart) || !Objects.isNull(strCaccineDateStart)) {			
			if(!FunctionsUtil.validateFormatDate(strCaccineDateStart))
				throw new BOException("project.warn.formatDateStart");
		}
		
		//Validate vaccine date end
		if(!ObjectUtils.isEmpty(strVaccineDateEnd) || !Objects.isNull(strVaccineDateEnd)) {			
			if(!FunctionsUtil.validateFormatDate(strVaccineDateEnd))
				throw new BOException("project.warn.formatDateEnd");
		}
		Date dateStart = null;
		Date dateEnd = null;
		//Validate date start before end
		if((!ObjectUtils.isEmpty(strCaccineDateStart) || !Objects.isNull(strCaccineDateStart)) && (!ObjectUtils.isEmpty(strVaccineDateEnd) || !Objects.isNull(strVaccineDateEnd))) {
			 dateStart = FunctionsUtil.convertStringToDate(strCaccineDateStart); 
			 dateEnd = FunctionsUtil.convertStringToDate(strVaccineDateEnd);
			if(dateEnd.before(dateStart))
				throw new BOException("project.warn..dateStartIsMoreThanDateEnd");
		}
		
		List <ResponseUsersFiltersDTO> lstEmployeesToFilters = objUsersCustomDAOImpl.getEmployeesToFilters(strVaccineState, strVaccineType, dateStart, dateEnd);
		
		return lstEmployeesToFilters;
	}

	@Override
	public List<ResponseUsersFiltersDTO> getEmployeeByIdentification(String strIdentification)
			throws BOException, ParseException {
		
		if(ObjectUtils.isEmpty(strIdentification) || Objects.isNull(strIdentification)) {			
			throw new BOException("project.warn.fieldMandatory", new Object[] { "project.fields.identification" });
		}
		
		List <ResponseUsersFiltersDTO> lstEmployeesToFilters = objUsersCustomDAOImpl.getEmployeeByIdentification(strIdentification);

		return lstEmployeesToFilters;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { BOException.class, CustomExceptionHandler.class, RestClientException.class})
	public HashMap<String, Integer> createEmployee(RequestCreateEmployeeDTO objRequestCreateEmployeeDTO) throws BOException, ParseException {
		
		//Validations mandatorys
		if(ObjectUtils.isEmpty(objRequestCreateEmployeeDTO) || Objects.isNull(objRequestCreateEmployeeDTO))
			throw new BOException("project.warn.fieldMandatory", new Object[] { "project.fields.employee" });
		
		if(ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getIdentification()) || Objects.isNull(objRequestCreateEmployeeDTO.getIdentification()))
			throw new BOException("project.warn.fieldMandatory", new Object[] { "project.fields.identification" });

		if(!FunctionsUtil.validateOnlyTenNumbers(objRequestCreateEmployeeDTO.getIdentification()))
			throw new BOException("project.warn.fieldOnlyAccept", new Object[] { "project.fields.identification" , "ten numbers" });
			
		if(ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getFirstName())|| Objects.isNull(objRequestCreateEmployeeDTO.getFirstName()))
			throw new BOException("project.warn.fieldMandatory", new Object[] { "project.fields.firstName" });
		
		if(!FunctionsUtil.validateOnlyLetters(objRequestCreateEmployeeDTO.getFirstName()))
			throw new BOException("project.warn.fieldOnlyAccept", new Object[] { "project.fields.firstName" , "project.fields.letters" });
		
		if(ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getLastName())|| Objects.isNull(objRequestCreateEmployeeDTO.getLastName()))
			throw new BOException("project.warn.fieldMandatory", new Object[] { "project.fields.lastName" });
		
		if(!FunctionsUtil.validateOnlyLetters(objRequestCreateEmployeeDTO.getLastName()))
			throw new BOException("project.warn.fieldOnlyAccept", new Object[] { "project.fields.lastName" ,"project.fields.letters" });
		
		if(ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getMail())|| Objects.isNull(objRequestCreateEmployeeDTO.getMail())) {			
			throw new BOException("project.warn.fieldMandatory", new Object[] { "project.fields.mail" });
		}else {
			if(!FunctionsUtil.validateFormatMail(objRequestCreateEmployeeDTO.getMail()))
				throw new BOException("project.warn.mailIsInvalid");
		}
		
		//Validate identification exists
		if(objUsersCustomDAOImpl.verifyExistUser(objRequestCreateEmployeeDTO.getIdentification()))
			throw new BOException("project.warn.existUser", new Object[] {objRequestCreateEmployeeDTO.getIdentification()});
		
		//Validate for enable employee depends of state
		 String username = null;
		 String encodedStringPassword = null;
		 
		if(objRequestCreateEmployeeDTO.getState().equalsIgnoreCase("S")) {
			//Generate username and password
			UUID uuid = UUID.randomUUID();
		    String uuidAsString = uuid.toString();
		    String tmpUuidAsString = uuidAsString.split("")[0];
		    
		    String[] arrFirstName = objRequestCreateEmployeeDTO.getFirstName().split("");
		    username = arrFirstName[0].toUpperCase() + objRequestCreateEmployeeDTO.getLastName().toUpperCase();
		    String password = username + tmpUuidAsString;
		    encodedStringPassword = Base64.getEncoder().encodeToString(password.getBytes());
		}
		
		Users objNewUser = new Users();
		objNewUser.setIdentification(objRequestCreateEmployeeDTO.getIdentification());
		objNewUser.setFirstName(objRequestCreateEmployeeDTO.getFirstName());
		objNewUser.setLastName(objRequestCreateEmployeeDTO.getLastName());
		objNewUser.setUsername(username);
		objNewUser.setPassword(encodedStringPassword);
		objNewUser.setMail(objRequestCreateEmployeeDTO.getMail());
		objNewUser.setState(objRequestCreateEmployeeDTO.getState());
		Users userCreated = objUsersCustomDAOImpl.insert(objNewUser);
		
		//Validate vaccine datebirth
		if(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getDateBirth()) || !Objects.isNull(objRequestCreateEmployeeDTO.getDateBirth())) {			
			if(!FunctionsUtil.validateFormatDate(objRequestCreateEmployeeDTO.getDateBirth()))
				throw new BOException("project.warn.formatDateStart");
		}
		
		//Validate vaccine state
		if(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getVaccinationState()) || !Objects.isNull(objRequestCreateEmployeeDTO.getVaccinationState())) {
			VaccineState[] vaccineStates = VaccineState.values();
			if(!Arrays.stream(vaccineStates).anyMatch(tmp -> tmp.getName().equals(objRequestCreateEmployeeDTO.getVaccinationState()))) {
				throw new BOException("project.warn.fieldNoExist", new Object[] { objRequestCreateEmployeeDTO.getVaccinationState() });
			}
		}
		
		DataEmployee objNewDataEmployee = new DataEmployee();
		objNewDataEmployee.setAddress(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getAddress())?objRequestCreateEmployeeDTO.getAddress():null);
		objNewDataEmployee.setDateBirth(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getDateBirth())?FunctionsUtil.convertStringToDate(objRequestCreateEmployeeDTO.getDateBirth()):null);
		objNewDataEmployee.setVaccinationState(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getVaccinationState())?objRequestCreateEmployeeDTO.getVaccinationState():null);
		objNewDataEmployee.setMobile(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getMobile())?objRequestCreateEmployeeDTO.getMobile():null);
		objNewDataEmployee.setUsers(userCreated);
		
		//Validate vaccine date
		if(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getVaccineDate()) || !Objects.isNull(objRequestCreateEmployeeDTO.getVaccineDate())) {			
			if(!FunctionsUtil.validateFormatDate(objRequestCreateEmployeeDTO.getVaccineDate()))
				throw new BOException("project.warn.formatDateStart");
		}
		
		//Get vaccine type by vaccine
		
		
		
		VaccineData objNewVaccineData = new VaccineData();
		objNewVaccineData.setVaccineDate(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getVaccineDate())?FunctionsUtil.convertStringToDate(objRequestCreateEmployeeDTO.getVaccineDate()):null);
		objNewVaccineData.setNumberDose(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getNumberDose())?objRequestCreateEmployeeDTO.getNumberDose():null);
		objNewVaccineData.setDataEmployee(objNewDataEmployee);
		objNewVaccineData.setVaccine(null);
		
		HashMap<String, Integer>hasMapResponse= new HashMap<String, Integer>();
		hasMapResponse.put("idUser", userCreated.getIdUsers());
		return hasMapResponse;
	}

}
