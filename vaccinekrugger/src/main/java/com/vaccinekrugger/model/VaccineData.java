package com.vaccinekrugger.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "VACCINE_DATA")
public class VaccineData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_vaccine_data")
    private Integer idVaccineData;
	
    @Column(name = "vaccine_date")
	@Temporal(TemporalType.TIMESTAMP)
    private Date vaccineDate;
	
    @Column(name = "number_dose")
    private Integer numberDose;
	
	@JoinColumns({
        @JoinColumn(name = "id_vaccine", referencedColumnName = "id_vaccine")})
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	private Vaccine vaccine;
	
	@JoinColumns({
        @JoinColumn(name = "id_data_employee", referencedColumnName = "id_data_employee")})
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	private DataEmployee dataEmployee;

}
