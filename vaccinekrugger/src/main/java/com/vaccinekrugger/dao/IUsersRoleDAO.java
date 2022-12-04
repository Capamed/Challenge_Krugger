package com.vaccinekrugger.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccinekrugger.model.UsersRole;

@Repository
public interface IUsersRoleDAO extends JpaRepository<UsersRole, Integer>{

}
