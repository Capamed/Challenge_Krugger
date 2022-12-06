package com.vaccinekrugger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsersRoleDTO {
	private String identification;
	private String username;
	private String password;
	private String role;
}
