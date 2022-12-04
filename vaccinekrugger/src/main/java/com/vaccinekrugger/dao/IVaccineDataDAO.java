package com.vaccinekrugger.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vaccinekrugger.model.VaccineData;

@Repository
public interface IVaccineDataDAO extends JpaRepository<VaccineData, Integer>{

}
