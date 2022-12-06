package com.vaccinekrugger.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUserDTO {
	@NotBlank
	private String userName;
	@NotBlank
	private String password;
}
