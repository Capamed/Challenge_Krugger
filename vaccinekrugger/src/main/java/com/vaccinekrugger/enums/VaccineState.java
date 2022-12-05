package com.vaccinekrugger.enums;

public enum VaccineState {
	
	VACCINATED("VACCINATED"),
	NOVACCINATED("NO VACCINATED");

	private String name;

	VaccineState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
