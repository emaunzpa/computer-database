package com.emaunzpa.computerdatabase.exception;

import org.apache.log4j.Logger;

public class IncoherenceBetweenDateException extends Exception {

	private Logger log = Logger.getLogger(IncoherenceBetweenDateException.class);
	
	public IncoherenceBetweenDateException(String message) {
		
		log.info(message);
		log.error(message);
		
	}
}
