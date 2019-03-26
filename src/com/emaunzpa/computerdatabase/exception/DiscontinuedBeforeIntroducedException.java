package com.emaunzpa.computerdatabase.exception;

import org.apache.log4j.Logger;

public class DiscontinuedBeforeIntroducedException extends Exception {

	private Logger log = Logger.getLogger(DiscontinuedBeforeIntroducedException.class);
	
	public DiscontinuedBeforeIntroducedException(String message) {
		
		log.info(message);
		log.error(message);
		
	}
}
