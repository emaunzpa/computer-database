package com.emaunzpa.computerdatabase.launch;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import java.io.IOException;
import java.sql.SQLException;

public class TestLog4j {

	static Logger log = Logger.getLogger(TestLog4j.class.getName());
	   
	   public static void main(String[] args)throws IOException,SQLException{
		   
		  HTMLLayout htmlLayout = new HTMLLayout();
		  RollingFileAppender rollingfileAppender = new RollingFileAppender(htmlLayout, "log4j/test.html");
		  log.addAppender(rollingfileAppender);
	      log.debug("Hello this is a debug message");
	      log.info("Hello this is an info message");	
	   }

}
