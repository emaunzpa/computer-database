package com.emaunzpa.util;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.emaunzpa.exception.NoManufacturerFoundException;
import com.emaunzpa.model.Manufacturer;

public class CompanyFormValidator {

	private static Logger log;

	public CompanyFormValidator() {
		log = Logger.getLogger(CompanyFormValidator.class);
	}
	
	public boolean companyFound(ArrayList<Manufacturer> arrayList, Integer searchId) throws NoManufacturerFoundException {
		
		boolean result = false;
		
		if (arrayList.stream().filter(manufacturer -> searchId.equals(manufacturer.getId())).findFirst().orElse(null) == null) {
			
			throw new NoManufacturerFoundException("No company found with this ID : " + searchId + ". Request cancelled.");
		
		}
		else {
			result = true;
		}
		
		return result;
	}
}
