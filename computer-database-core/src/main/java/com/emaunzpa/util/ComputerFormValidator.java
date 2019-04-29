package com.emaunzpa.util;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.emaunzpa.exception.ComputerWithoutNameException;
import com.emaunzpa.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.exception.IncoherenceBetweenDateException;
import com.emaunzpa.exception.NoComputerFoundException;
import com.emaunzpa.model.Computer;

public class ComputerFormValidator {
	
	private static Logger log;

	public ComputerFormValidator() {
		log = Logger.getLogger(ComputerFormValidator.class);
	}
	
	public boolean computerFound(ArrayList<Optional<Computer>> arrayList, Integer searchId) throws NoComputerFoundException {
		
		boolean result = false;
		
		if (arrayList.stream().filter(computer -> searchId.equals(computer.get().getId())).findFirst().orElse(null) == null) {
			
			throw new NoComputerFoundException("No computer found with this ID : " + searchId + ". Request cancelled.");
		
		}
		else {
			result = true;
		}
		
		return result;
	}
	
	public boolean newComputerHasName(Computer computer) throws ComputerWithoutNameException {
		
		boolean result = false;
		
		if(computer.getName() == null || computer.getName().equals("")) {
			
			throw new ComputerWithoutNameException("Impossible to add a computer without any name to the database. Request cancelled.");
		}
		
		else {
			result =  true;
		}
		
		return result;
	}
	
	public boolean introducedBeforeDiscontinued(Computer computer) throws IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		boolean result = false;
		
		if (computer.getIntroducedDate() != null && computer.getDiscontinuedDate() != null && computer.getIntroducedDate().after(computer.getDiscontinuedDate())){
			
			throw new DiscontinuedBeforeIntroducedException("Discontinued date must be after introduced date. Request cancelled.");
			
		}
		
		else if (computer.getDiscontinuedDate() != null && computer.getIntroducedDate() == null) {
			
			throw new IncoherenceBetweenDateException("Discontinued must be null if introduced date is null. Request cancelled.");
			
		}
		
		else {
			result = true;
		}
		
		return result;
	}
}
