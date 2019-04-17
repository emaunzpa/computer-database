package com.emaunzpa.computerdatabase.exception;

import org.apache.log4j.Logger;

public class ComputerWithoutNameException extends Exception {

	private Logger log = Logger.getLogger(ComputerWithoutNameException.class);
	
	public ComputerWithoutNameException(String message) {
		super(message);
		log.error(message);
		log.info(message);
		
	}
}
