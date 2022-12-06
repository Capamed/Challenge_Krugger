package com.vaccinekrugger.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "ROLE")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_role")
    private Integer idRole;
	
    @Column(name = "role",length = 15)
    private String role;

}
