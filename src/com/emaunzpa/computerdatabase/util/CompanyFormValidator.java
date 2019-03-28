package com.emaunzpa.computerdatabase.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.exception.NoManufacturerFoundException;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class CompanyFormValidator {

	private static Logger log;

	public CompanyFormValidator() {
		log = Logger.getLogger(ComputerDriver.class);
	}
	
	public boolean companyFound(ArrayList<Manufacturer> manufacturers, Integer searchId) throws NoManufacturerFoundException {
		
		boolean result = false;
		
		if (manufacturers.stream().filter(manufacturer -> searchId.equals(manufacturer.getId())).findFirst().orElse(null) == null) {
			
			throw new NoManufacturerFoundException("No company found with this ID : " + searchId + ". Request cancelled.");
		
		}
		else {
			result = true;
		}
		
		return result;
	}
}
