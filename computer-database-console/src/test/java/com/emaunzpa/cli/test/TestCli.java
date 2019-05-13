package com.emaunzpa.cli.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emaunzpa.cli.CommandeLigneInterface;
import com.emaunzpa.dto.ComputerDTO;
import com.emaunzpa.exception.NoComputerFoundException;

public class TestCli {
	
	public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("consoleContext.xml");
	private CommandeLigneInterface commandeLigneInterface;
	
	@Before
	public void init() {
		this.commandeLigneInterface = (CommandeLigneInterface) CONTEXT.getBean("commandeLigneInterface");
	}

	@Test
	public void showAllComputers() {
		assertEquals(commandeLigneInterface.showAllComputer().get(0).getName(), "MacBook Pro 15.4 inch");
		assertTrue(commandeLigneInterface.showAllComputer().size() > 0);
		commandeLigneInterface.getScIn().next("q");
	}
	
	@Test
	public void showAllCompanies() {
		assertEquals(commandeLigneInterface.showAllCompanies().get(0).getName(), "Apple Inc.");
		assertTrue(commandeLigneInterface.showAllCompanies().size() > 0);
		commandeLigneInterface.getScIn().next("q");
	}
	
	@Test
	public void showComputerDetails() throws NoComputerFoundException {
		ComputerDTO computerDTO =  commandeLigneInterface.showComputerDetails();
		commandeLigneInterface.getScIn().nextInt(1);
		commandeLigneInterface.getScIn().nextLine();
		assertEquals(computerDTO.getName(), "MacBook Pro 15.4 inch");
	}

}
