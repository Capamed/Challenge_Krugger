package com.vaccinekrugger.api;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinekrugger.bo.IUsersBO;
import com.vaccinekrugger.dto.RequestCreateEmployeeDTO;
import com.vaccinekrugger.dto.RequestUpdateInformationEmployeeDTO;
import com.vaccinekrugger.dto.ResponseOk;
import com.vaccinekrugger.dto.ResponseUsersFiltersDTO;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.exceptions.CustomExceptionHandler;
import com.vaccinekrugger.model.Users;
import com.vaccinekrugger.utils.MessagesUtil;

@RestController
@RequestMapping("/employee")
public class EmployeeApi {
	
	@Autowired
	private IUsersBO iUsersBO;
	
	//List All Users
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllEmployees(
			@RequestHeader(value="Accept-Language", required = false) String strLanguage,
	 		@RequestParam(name = "vaccineState", required = false) String vaccineState,
			@RequestParam(name = "vaccineType", required = false) String vaccineType,
	    	@RequestParam(name = "vaccineDateStart", required = false) String vaccineDateStart,
	    	@RequestParam(name = "vaccineDateEnd", required = false) String vaccineDateEnd)throws BOException, ParseException {
		try {
			List<ResponseUsersFiltersDTO> objUsers = iUsersBO.getAllEmployees(vaccineState,vaccineType,vaccineDateStart,vaccineDateEnd);
			return new ResponseEntity<>(new ResponseOk(
					MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
					objUsers), HttpStatus.OK);
		} catch (BOException be) {
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	//List user by identification
	@RequestMapping(value = "/{identification}",method = RequestMethod.GET)
	public ResponseEntity<?> getEmployeeByIdentification(
			@RequestHeader(value="Accept-Language", required = false) String strLanguage,
			@PathVariable(name = "identification", required = false) String identification
	 		)throws BOException, ParseException {
		try {
			List<ResponseUsersFiltersDTO> objUsers = iUsersBO.getEmployeeByIdentification(identification);
			return new ResponseEntity<>(new ResponseOk(
					MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
					objUsers), HttpStatus.OK);
		} catch (BOException be) {
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	
	//List user by idUser
	@RequestMapping(value = "/{idUser}",method = RequestMethod.GET)
	public ResponseEntity<?> getEmployeeByIdUser(
			@RequestHeader(value="Accept-Language", required = false) String strLanguage,
			@PathVariable(name = "identification", required = false) Integer intIdUser
	 		)throws BOException, ParseException {
		try {
			Users objUsers = iUsersBO.getEmployeeByIdUser(intIdUser);
			return new ResponseEntity<>(new ResponseOk(
					MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
					objUsers), HttpStatus.OK);
		} catch (BOException be) {
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	//Create Employee
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createEmployee(
			@RequestHeader(value="Accept-Language", required = false) String strLanguage,
			@RequestBody(required = false) RequestCreateEmployeeDTO objRequestCreateEmployeeDTO)throws BOException, ParseException {
		try {
			HashMap<String, Integer> idUser = iUsersBO.createEmployee(objRequestCreateEmployeeDTO);
			return new ResponseEntity<>(new ResponseOk(
					MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
					idUser), HttpStatus.OK);
		} catch (BOException be) {
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
	
	//Update state employee to generate credentials 
	@RequestMapping(value = "/enable", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStateEmployee(
			@RequestHeader(value="Accept-Language", required = false) String strLanguage,
			@RequestParam(name = "state", required = false) String strState,
			@RequestParam(name = "identification", required = false) String strIdentification)throws BOException, ParseException {
		iUsersBO.updateStateEmployee(strIdentification,strState);
		return new ResponseEntity<>(new ResponseOk(
				MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
				null), HttpStatus.OK);
	}
	
	//Update information employee by idUser
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> updateInformationEmployee(
			@RequestHeader(value="Accept-Language", required = false) String strLanguage,
			@RequestParam(name = "idUser", required = false) Integer  intIdUser,
			@RequestBody(required = false) RequestUpdateInformationEmployeeDTO objUpdateInformationEmployeeDTO)throws BOException, ParseException {
		iUsersBO.updateInformationEmployee(intIdUser,objUpdateInformationEmployeeDTO);
		return new ResponseEntity<>(new ResponseOk(
				MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
				null), HttpStatus.OK);
	}
	
}
