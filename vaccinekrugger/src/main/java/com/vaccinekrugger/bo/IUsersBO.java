package com.vaccinekrugger.bo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import com.vaccinekrugger.dto.RequestCreateEmployeeDTO;
import com.vaccinekrugger.dto.RequestUpdateInformationEmployeeDTO;
import com.vaccinekrugger.dto.ResponseUsersFiltersDTO;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.model.Users;

public interface IUsersBO {
	public  List<ResponseUsersFiltersDTO> getAllEmployees(String vaccineState, String vaccineType, String vaccineDateStart,String vaccineDateEnd) throws BOException, ParseException;

	public  List<ResponseUsersFiltersDTO> getEmployeeByIdentification(String identification) throws BOException, ParseException;
	
	public HashMap<String, Integer> createEmployee(RequestCreateEmployeeDTO objRequestCreateEmployeeDTO)throws BOException, ParseException;
	
	public void updateStateEmployee(String strIdentification, String strState);
	
	public void updateInformationEmployee(Integer intIdUser, RequestUpdateInformationEmployeeDTO objUpdateInformationEmployeeDTO) throws ParseException, BOException;

	public  Users getEmployeeByIdUser(Integer intIdUser) throws BOException, ParseException;
	
	public void deleteEmployee(Integer intIdUser);	
}
