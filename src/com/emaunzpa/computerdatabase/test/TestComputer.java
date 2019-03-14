package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.util.DatesHandler;

public class TestComputer {
	
	private ComputerDriver computerDriver;
	private DatesHandler datesHandler;

	@Before
	public void createComputerDriver() {
		computerDriver = new ComputerDriver();
		datesHandler = new DatesHandler();
	}
	
	@Test
	public void getComputer() {
		Computer testedComputer = computerDriver.getComputer(5);
		assertEquals("CM-5", testedComputer.getName());
		assertEquals("1991-01-01 00:00:00", datesHandler.convertSqlDateToString(testedComputer.getIntroducedDate()));
		assertNull(testedComputer.getDiscontinuedDate());
		assertEquals(2, (int) testedComputer.getmanufacturerId());
		Computer testedComputerNull = computerDriver.getComputer(0);
		assertEquals(0, testedComputerNull.getId());
		assertNull(testedComputerNull.getName());
		assertNull(testedComputerNull.getIntroducedDate());
		assertNull(testedComputerNull.getDiscontinuedDate());
		assertNull(testedComputerNull.getmanufacturerId());
	}
	
	@Test
	public void getAllComputers() {
		ArrayList<Computer> computers = computerDriver.getAllComputers();
		assertNotNull(computers);
	}
	
	@Test
	public void createComputer() {
		
		Computer computer1 = new Computer("Computer1", datesHandler.convertStringDateToSqlDate("2019-03-14 00:00:00"));
		Computer computer2 = new Computer("Computer2", datesHandler.convertStringDateToSqlDate("2019-03-14 00:00:00"), null, 5);
		Computer computerWithNullName = new Computer();
		Computer computerWithUnexistingCompanyId = new Computer("Computer4", null, null, 1000);
		
		assertTrue(computerDriver.addComputer(computer1));
		assertTrue(computerDriver.addComputer(computer2));
		assertFalse(computerDriver.addComputer(computerWithNullName));
		assertFalse(computerDriver.addComputer(computerWithUnexistingCompanyId));
	}

	@Test
	public void updateComputer() {
		
		assertTrue(computerDriver.updateComputer(1, "MacBook Pro 15.4 inch", null, null, 1));
		assertTrue(computerDriver.updateComputer(12, "Apple III", datesHandler.convertStringDateToSqlDate("1980-05-01 00:00:00"), datesHandler.convertStringDateToSqlDate("1984-04-01 00:00:00"), 1));
		assertTrue(computerDriver.updateComputer(575, "Titi acer v5.0", datesHandler.convertStringDateToSqlDate("2019-03-14 00:00:00"), null, 5));
		assertFalse(computerDriver.updateComputer(100000, "Titi acer v5.0", datesHandler.convertStringDateToSqlDate("2019-03-14 00:00:00"), null, 5));
	}
}
