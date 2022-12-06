package com.vaccinekrugger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestCreateEmployeeDTO {
	private String identification;
	private String firstName;
	private String lastName;
	private String mail;
	private String state;
	private String role;
	private String address;
	private String mobile;
	private String dateBirth;
	private String vaccinationState;
	private String vaccineDate;
	private Integer numberDose;
	private String vaccineType;
}
