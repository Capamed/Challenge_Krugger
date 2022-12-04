package com.vaccinekrugger.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_users")
    private Integer idUsers;
	
    @Column(name = "identification", length = 10, unique = true)
    private Integer identification;
    
    @Column(name = "username",length = 30)
    private String username;

    @Column(name = "password",length = 30)
    private String password;
    
    @Column(name = "first_name",length = 250)
    private String firstName;
    
    @Column(name = "last_name",length = 250)
    private String lastName;
    
    @Column(name = "mail")
    private String mail;
    
    @Column(name = "state",length = 1)
    private String state;
}
