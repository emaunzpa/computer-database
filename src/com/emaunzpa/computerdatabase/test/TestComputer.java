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
		assertTrue(computers.size() >= 0);
	}
	
	@Test
	public void createComputer() {
		
		Computer computer1 = new Computer.ComputerBuilder().withName("Computer1").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14 00:00:00")).build();
		Computer computer2 = new Computer.ComputerBuilder().withName("Computer2").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14 00:00:00")).withDiscontinuedDate(null).withManufacturerId(5).build();
		Computer computerWithNullName = new Computer.ComputerBuilder().build();
		Computer computerWithUnexistingCompanyId = new Computer.ComputerBuilder().withName("Computer4").withIntroducedDate(null).withDiscontinuedDate(null).withManufacturerId(1000).build();
		
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
