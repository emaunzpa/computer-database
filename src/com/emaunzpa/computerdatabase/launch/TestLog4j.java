package com.emaunzpa.computerdatabase.launch;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;

public class TestLog4j {

	static Logger log = Logger.getLogger(TestLog4j.class.getName());
	   
	   public static void main(String[] args)throws IOException,SQLException{
	      log.debug("Hello this is a debug message");
	      log.info("Hello this is an info message");
	   }

}
