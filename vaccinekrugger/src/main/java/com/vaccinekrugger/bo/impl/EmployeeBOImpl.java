package com.vaccinekrugger.bo.impl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.vaccinekrugger.bo.IUsersBO;
import com.vaccinekrugger.dao.impl.UsersCustomDAOImpl;
import com.vaccinekrugger.dto.ResponseUsersFiltersDTO;
import com.vaccinekrugger.enums.VaccineType;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.model.Users;
import com.vaccinekrugger.utils.FunctionsUtil;

@Service
public class EmployeeBOImpl implements IUsersBO {

	@Autowired
	private UsersCustomDAOImpl objUsersCustomDAOImpl ;
	
	@Override
	public List<ResponseUsersFiltersDTO> getAllEmployees(String vaccineState, String vaccineType, String vaccineDateStart,String vaccineDateEnd) throws BOException, ParseException {
		//Validate vaccine type
		if(!ObjectUtils.isEmpty(vaccineType) || !Objects.isNull(vaccineType)) {
			VaccineType[] vaccineTypes = VaccineType.values();
			if(!Arrays.stream(vaccineTypes).anyMatch(tmp -> tmp.equals(vaccineType))) {
				throw new BOException("project.warn.fieldMandatory", new Object[] { "CAMPOOO" });
			}
		}
		
		//Validate vaccine date start
		if(!ObjectUtils.isEmpty(vaccineDateStart) || !Objects.isNull(vaccineDateStart)) {			
			if(!FunctionsUtil.validateFormatDate(vaccineDateStart))
				throw new BOException("age.warn.validarFormatoFechaInicio");
		}
		
		//Validate vaccine date end
		if(!ObjectUtils.isEmpty(vaccineDateEnd) || !Objects.isNull(vaccineDateEnd)) {			
			if(!FunctionsUtil.validateFormatDate(vaccineDateEnd))
				throw new BOException("age.warn.validarFormatoFechaInicio");
		}
		
		//Validate date start before end
		if(!ObjectUtils.isEmpty(vaccineDateStart) || !Objects.isNull(vaccineDateStart) || !ObjectUtils.isEmpty(vaccineDateEnd) || !Objects.isNull(vaccineDateEnd)) {
			Date dateStart = FunctionsUtil.convertStringToDate(vaccineDateStart); 
			Date dateEnd = FunctionsUtil.convertStringToDate(vaccineDateEnd);
			if(dateEnd.before(dateStart))
				throw new BOException("age.warn.validarPrelacionFechaFin");
		}
		
		
		List <ResponseUsersFiltersDTO> h = objUsersCustomDAOImpl.getEmployeesToFilters(vaccineState, vaccineType, vaccineDateStart, vaccineDateEnd);
		
		return h;
	}

}
