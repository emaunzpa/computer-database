package com.emaunzpa.computerdatabase.launch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emaunzpa.computerdatabase.UI.CommandeLigneInterface;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.exception.NoManufacturerFoundException;

public class TestJDBC {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

	public static void main(String[] args) throws ParseException, ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, NoComputerFoundException, FileNotFoundException, IOException, SQLException, NoManufacturerFoundException {
				
		CommandeLigneInterface cli = (CommandeLigneInterface) context.getBean("commandeLigneInterface");
		cli.run();
		
	}

}
