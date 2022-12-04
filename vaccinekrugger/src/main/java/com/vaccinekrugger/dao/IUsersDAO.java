package com.vaccinekrugger.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccinekrugger.model.Users;

@Repository
public interface IUsersDAO extends JpaRepository<Users, Integer>{

}
