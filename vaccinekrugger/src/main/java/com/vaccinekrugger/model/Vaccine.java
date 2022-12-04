package com.vaccinekrugger.model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;


@Entity
@Builder
@Data
@Table(name = "VACCINE")
public class Vaccine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_vaccine")
    private Integer idVaccine;
	
    @Column(name = "type",length = 200)
    private String type;

}
