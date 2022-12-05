package com.vaccinekrugger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseUsersFiltersDTO {
	private String identification;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String mail;
	private String state;
	private String dateBirth;
	private String address;
	private String mobile;
	private String vaccinationState;
	private String vaccineDate;
	private Integer numberDose;
	private String type;
}
