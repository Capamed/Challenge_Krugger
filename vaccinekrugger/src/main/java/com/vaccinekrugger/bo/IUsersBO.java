package com.vaccinekrugger.bo;

import java.text.ParseException;
import java.util.List;

import com.vaccinekrugger.dto.ResponseUsersFiltersDTO;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.model.Users;

public interface IUsersBO {
	public  List<ResponseUsersFiltersDTO> getAllEmployees(String vaccineState, String vaccineType, String vaccineDateStart,String vaccineDateEnd) throws BOException, ParseException;

}
