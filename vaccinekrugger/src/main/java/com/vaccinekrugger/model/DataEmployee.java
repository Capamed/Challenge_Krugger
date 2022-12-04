package com.vaccinekrugger.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Data;


@Entity
@Builder
@Data
@Table(name = "DATA_EMPLOYEE")
public class DataEmployee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_data_employee")
    private Integer idDataEmployee;

    @Column(name = "date_birth")
	@Temporal(TemporalType.TIMESTAMP)
    private Date dateBirth;
	
    @Column(name = "address",length = 200)
    private String address;

    @Column(name = "mobile",length = 30)
    private String mobile;
    
    @Column(name = "vaccination_state",length = 250)
    private String vaccinationState;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_users", referencedColumnName = "id_users")
    private Users users;
   
}
