package com.vaccinekrugger.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinekrugger.bo.IUsersBO;
import com.vaccinekrugger.dto.ResponseOk;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.exceptions.CustomExceptionHandler;
import com.vaccinekrugger.model.Users;
import com.vaccinekrugger.utils.MessagesUtil;

@RestController
@RequestMapping("/employee")
public class EmployeeApi {
	
	@Autowired
	private IUsersBO iUsersBO;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllEmployees(@RequestHeader(value="Accept-Language", required = false) String strLanguage)throws BOException {
		List<Users> objUsers;
		try {
			objUsers = iUsersBO.getAllEmployees();
			return new ResponseEntity<>(new ResponseOk(
					MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
					objUsers), HttpStatus.OK);
		} catch (BOException be) {
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
