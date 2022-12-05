package com.vaccinekrugger.bo.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;

import com.vaccinekrugger.bo.IUsersBO;
import com.vaccinekrugger.dao.impl.DataEmployeeCustomDAOImpl;
import com.vaccinekrugger.dao.impl.UsersCustomDAOImpl;
import com.vaccinekrugger.dao.impl.VaccineCustomDAOImpl;
import com.vaccinekrugger.dao.impl.VaccineDataCustomDAOImpl;
import com.vaccinekrugger.dto.RequestCreateEmployeeDTO;
import com.vaccinekrugger.dto.RequestUpdateInformationEmployeeDTO;
import com.vaccinekrugger.dto.ResponseUsersFiltersDTO;
import com.vaccinekrugger.enums.VaccineState;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.exceptions.CustomExceptionHandler;
import com.vaccinekrugger.model.DataEmployee;
import com.vaccinekrugger.model.Users;
import com.vaccinekrugger.model.Vaccine;
import com.vaccinekrugger.model.VaccineData;
import com.vaccinekrugger.utils.FunctionsUtil;

@Service
public class EmployeeBOImpl implements IUsersBO {

	@Autowired
	private UsersCustomDAOImpl objUsersCustomDAOImpl;
	
	@Autowired
	private VaccineCustomDAOImpl objVaccineCustomDAOImpl;
	
	@Autowired
	private DataEmployeeCustomDAOImpl objDataEmployeeCustomDAOImpl;
	
	@Autowired
	private VaccineDataCustomDAOImpl objVaccineDataCustomDAOImpl;
	
	
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
		
		//Validations mandatory
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
				throw new BOException("project.warn.formatDateIsInvalid");
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
				throw new BOException("project.warn.formatDateIsInvalid");
		}
		
		
		if(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getVaccinationState()) || !Objects.isNull(objRequestCreateEmployeeDTO.getVaccinationState())) {
			
			if(objRequestCreateEmployeeDTO.getVaccinationState().equals(VaccineState.VACCINATED.getName())) {
				//Get id vaccine by vaccine type
				Integer intVaccine = objVaccineCustomDAOImpl.getIdVaccineByType(objRequestCreateEmployeeDTO.getVaccineType());
				if(ObjectUtils.isEmpty(intVaccine) || Objects.isNull(intVaccine)) {
					throw new BOException("project.warn.NoexistVaccine", new Object[] { objRequestCreateEmployeeDTO.getVaccineType() }); 
				}
				Optional<Vaccine> optVaccine = objVaccineCustomDAOImpl.findById(intVaccine);
				
				VaccineData objNewVaccineData = new VaccineData();
				objNewVaccineData.setVaccineDate(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getVaccineDate())?FunctionsUtil.convertStringToDate(objRequestCreateEmployeeDTO.getVaccineDate()):null);
				objNewVaccineData.setNumberDose(!ObjectUtils.isEmpty(objRequestCreateEmployeeDTO.getNumberDose())?objRequestCreateEmployeeDTO.getNumberDose():null);
				objNewVaccineData.setDataEmployee(objNewDataEmployee);
				objNewVaccineData.setVaccine(optVaccine.get());				
			}
		}
		
		HashMap<String, Integer>hasMapResponse= new HashMap<String, Integer>();
		hasMapResponse.put("idUser", userCreated.getIdUsers());
		return hasMapResponse;
	}

	@Override
	public void updateStateEmployee(String strIdentification, String strState) {
		List<Users> lstUsers =  objUsersCustomDAOImpl.getUserByIdentification(strIdentification);
		 String username = null;
		 String encodedStringPassword = null;
		 
		if(strState.equalsIgnoreCase("S")) {
			//Generate username and password
			UUID uuid = UUID.randomUUID();
		    String uuidAsString = uuid.toString();
		    String tmpUuidAsString = uuidAsString.split("")[0];

		    
		    String[] arrFirstName = lstUsers.get(0).getFirstName().split("");
		    username = arrFirstName[0].toUpperCase() + lstUsers.get(0).getLastName().toUpperCase();
		    String password = username + tmpUuidAsString;
		    encodedStringPassword = Base64.getEncoder().encodeToString(password.getBytes());
		    
		}else {
			
		}
		
	  if(lstUsers.size() > 0 && !ObjectUtils.isEmpty(lstUsers)) {
	    	Users updateUser = new Users();
	    	updateUser.setIdUsers(lstUsers.get(0).getIdUsers());
	    	updateUser.setIdentification(strIdentification);
	    	updateUser.setUsername((strState.equalsIgnoreCase("S"))?username:lstUsers.get(0).getUsername());
	    	updateUser.setPassword((strState.equalsIgnoreCase("S"))?encodedStringPassword:lstUsers.get(0).getPassword());
	    	updateUser.setFirstName(lstUsers.get(0).getFirstName());
	    	updateUser.setLastName(lstUsers.get(0).getLastName());
	    	updateUser.setMail(lstUsers.get(0).getMail());
	    	updateUser.setState(strState);
	    	objUsersCustomDAOImpl.update(updateUser);
	    }
	}

	@Override
	public Users getEmployeeByIdUser(Integer intIdUser) throws BOException, ParseException {
		Optional<Users> optUsers = objUsersCustomDAOImpl.findById(intIdUser);
		Users objUser = null;
		if(optUsers.isPresent()) {
			objUser = new Users();
			objUser = optUsers.get();
		}
		return objUser;
	}

	@Override
	public void updateInformationEmployee(Integer intIdUser, RequestUpdateInformationEmployeeDTO objUpdateInformationEmployeeDTO) throws ParseException, BOException {
		Optional<Users> optUsers = objUsersCustomDAOImpl.findById(intIdUser);
		Users objUserUpdate = null;
		if(optUsers.isPresent()) {
			objUserUpdate = new Users();
			objUserUpdate = optUsers.get();
			if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getMail())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getMail())) {				
				objUserUpdate.setMail(objUpdateInformationEmployeeDTO.getMail());
				objUsersCustomDAOImpl.update(objUserUpdate);
			}
			
			Optional<DataEmployee> optDataEmployee = objDataEmployeeCustomDAOImpl.findById(objUserUpdate.getIdUsers());
			DataEmployee updateDataEmployee = null;
			if(optDataEmployee.isPresent()) {				
				updateDataEmployee = optDataEmployee.get();
				if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getAddress())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getAddress())) {
					updateDataEmployee.setAddress(objUpdateInformationEmployeeDTO.getAddress());
				}
				
				if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getMobile())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getMobile())) {
					updateDataEmployee.setMobile(objUpdateInformationEmployeeDTO.getMobile());
				}
				
				
				if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getDateBirth())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getDateBirth())) {
					updateDataEmployee.setDateBirth(FunctionsUtil.convertStringToDate(objUpdateInformationEmployeeDTO.getDateBirth()));
				}
				
				if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getVaccinationState())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getVaccinationState())) {
					if(!ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getVaccinationState()) || !Objects.isNull(objUpdateInformationEmployeeDTO.getVaccinationState())) {
						VaccineState[] vaccineStates = VaccineState.values();
						if(!Arrays.stream(vaccineStates).anyMatch(tmp -> tmp.getName().equals(objUpdateInformationEmployeeDTO.getVaccinationState()))) {
							throw new BOException("project.warn.fieldNoExist", new Object[] { objUpdateInformationEmployeeDTO.getVaccinationState() });
						}
						updateDataEmployee.setVaccinationState(objUpdateInformationEmployeeDTO.getVaccinationState());
					}
				}
				objDataEmployeeCustomDAOImpl.update(updateDataEmployee);
				
				Integer intIdVaccineData = objVaccineDataCustomDAOImpl.getVaccineDataIdbyIdDataEmployee(optDataEmployee.get().getIdDataEmployee());
				
				Optional<VaccineData> optVaccineData = objVaccineDataCustomDAOImpl.findById(intIdVaccineData);
				VaccineData updateVaccineData = null;
				if(optVaccineData.isPresent()) {				
					updateVaccineData = optVaccineData.get();
					if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getVaccineDate())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getVaccineDate())) {
						updateVaccineData.setVaccineDate(FunctionsUtil.convertStringToDate(objUpdateInformationEmployeeDTO.getVaccineDate()));
					}
					
					if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getNumberDose())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getNumberDose())) {
						updateVaccineData.setNumberDose(objUpdateInformationEmployeeDTO.getNumberDose());
					}
					
					if(ObjectUtils.isEmpty(objUpdateInformationEmployeeDTO.getVaccineType())|| !Objects.isNull(objUpdateInformationEmployeeDTO.getVaccineType())) {
						//Get id vaccine by vaccine type
						Integer intVaccine = objVaccineCustomDAOImpl.getIdVaccineByType(objUpdateInformationEmployeeDTO.getVaccineType());
						if(ObjectUtils.isEmpty(intVaccine) || Objects.isNull(intVaccine)) {
							throw new BOException("project.warn.NoexistVaccine", new Object[] { objUpdateInformationEmployeeDTO.getVaccineType() }); 						
						}
						Optional<Vaccine> optVaccine = objVaccineCustomDAOImpl.findById(intVaccine);
						updateVaccineData.setVaccine(optVaccine.get());
					}
					
					objVaccineDataCustomDAOImpl.update(updateVaccineData);
				}
			}
		}
	}

	@Override
	public void deleteEmployee(Integer intIdUser) {
		// TODO Auto-generated method stub
		Optional<Users> optUsers = objUsersCustomDAOImpl.findById(intIdUser);
		if(optUsers.isPresent()) {
			Optional<DataEmployee> optDataEmployee = objDataEmployeeCustomDAOImpl.findById(optUsers.get().getIdUsers());
			if(optDataEmployee.isPresent()){
				//Get idDataVaccine by idDataEmployee
				Integer intIdVaccineData = objVaccineDataCustomDAOImpl.getVaccineDataIdbyIdDataEmployee(optDataEmployee.get().getIdDataEmployee());
				Optional<VaccineData> optVaccineData = objVaccineDataCustomDAOImpl.findById(intIdVaccineData);
				if(optVaccineData.isPresent()) {
					objVaccineDataCustomDAOImpl.delete(optVaccineData.get());
				}
				objDataEmployeeCustomDAOImpl.delete(optDataEmployee.get());
			}
			objUsersCustomDAOImpl.delete(optUsers.get());
		}
	}
}
