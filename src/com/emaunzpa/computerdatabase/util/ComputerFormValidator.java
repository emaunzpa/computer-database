package com.emaunzpa.computerdatabase.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.model.Computer;

public class ComputerFormValidator {
	
	private static Logger log;

	public ComputerFormValidator() {
		log = Logger.getLogger(ComputerDriver.class);
	}
	
	public boolean computerFound(ArrayList<Computer> computers, Integer searchId) {
		
		if (computers.stream().filter(computer -> searchId.equals(computer.getId())).findFirst().orElse(null) == null) {
			log.error("No computer found with this ID : " + searchId + ". Request cancelled.");
			log.info("No computer found with this ID : " + searchId + ". Request cancelled.");
			return false;
		}
		else {
			return true;
		}
		
	}
	
	public boolean newComputerHasName(Computer computer) {
		
		if(computer.getName() == null || computer.getName().equals("")) {
			log.error("Impossible to add a computer without any name to the database. Request cancelled.");
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean introducedBeforeDiscontinued(Computer computer) {
		
		if (computer.getIntroducedDate() != null && computer.getDiscontinuedDate() != null && computer.getIntroducedDate().after(computer.getDiscontinuedDate())){
			log.info("Discontinued date must be after introduced date. Request cancelled.");
			return false;
		}
		else {
			return true;
		}
	}
}
