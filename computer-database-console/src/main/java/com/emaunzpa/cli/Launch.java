package com.emaunzpa.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emaunzpa.exception.ComputerWithoutNameException;
import com.emaunzpa.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.exception.IncoherenceBetweenDateException;
import com.emaunzpa.exception.NoComputerFoundException;
import com.emaunzpa.exception.NoManufacturerFoundException;

public class Launch {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	public static void main(String[] args) throws ParseException, ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, NoComputerFoundException, FileNotFoundException, IOException, SQLException, NoManufacturerFoundException {
				
		CommandeLigneInterface cli = (CommandeLigneInterface) context.getBean("commandeLigneInterface");
		cli.run();
		
	}

}
