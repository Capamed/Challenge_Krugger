package com.vaccinekrugger.enums;

public enum VaccineType {
	
	VACCINATED("VACCINATED"),
	NOVACCINAED("NO VACCIATED");

	private String name;

	VaccineType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
