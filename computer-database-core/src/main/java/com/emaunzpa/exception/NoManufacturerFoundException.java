package com.emaunzpa.exception;

import org.apache.log4j.Logger;

public class NoManufacturerFoundException extends Exception {

	private Logger log = Logger.getLogger(NoComputerFoundException.class);
	
	public NoManufacturerFoundException(String message) {
		super(message);
		log.error(message);
		log.info(message);
		
	}
}
