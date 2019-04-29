package com.emaunzpa.exception;

import org.apache.log4j.Logger;

public class NoComputerFoundException extends Exception {

	private Logger log = Logger.getLogger(NoComputerFoundException.class);
	
	public NoComputerFoundException(String message) {
		super(message);
		log.error(message);
		log.info(message);
	}
}
