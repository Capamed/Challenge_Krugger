package com.vaccinekrugger.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccinekrugger.model.Vaccine;

@Repository
public interface IVaccineDAO extends JpaRepository<Vaccine, Integer>{

}
