package com.vaccinekrugger.model;
import java.io.Serializable;

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

import lombok.Builder;


@Entity
@Builder
@Table(name = "USERS_ROLE")
public class UsersRole implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_users_role")
    private Integer idUsersRole;

	@JoinColumns({
        @JoinColumn(name = "id_users", referencedColumnName = "id_users")})
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	private Users users;
	
	@JoinColumns({
        @JoinColumn(name = "id_role", referencedColumnName = "id_role")})
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	private Role role;
}
