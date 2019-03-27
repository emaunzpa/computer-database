package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.util.DatesHandler;

/**
 * Tests for the backend methods of ComputerDAO
 */	
public class TestComputer {
	
	private ComputerDriver computerDriver;
	private DatesHandler datesHandler;

	@Before
	public void createComputerDriver() {
		computerDriver = new ComputerDriver("computer-database-db-test");
		datesHandler = new DatesHandler();
	}
	
	@Test
	public void getComputer() throws NoComputerFoundException {
		Computer testedComputer = computerDriver.getComputer(5).get();
		assertEquals("CM-5", testedComputer.getName());
		assertEquals("1991-01-01", datesHandler.convertSqlDateToString(testedComputer.getIntroducedDate()));
		assertNull(testedComputer.getDiscontinuedDate());
		assertEquals(2, (int) testedComputer.getmanufacturerId());
		Computer testedComputerNull = computerDriver.getComputer(0).get();
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
	public void createComputer() throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		Computer computer1 = new Computer.ComputerBuilder().withName("Computer1").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14")).build();
		Computer computer2 = new Computer.ComputerBuilder().withName("Computer2").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14")).withDiscontinuedDate(null).withManufacturerId(5).build();
		Computer computerWithNullName = new Computer.ComputerBuilder().build();
		Computer computerWithUnexistingCompanyId = new Computer.ComputerBuilder().withName("Computer4").withIntroducedDate(null).withDiscontinuedDate(null).withManufacturerId(1000).build();
		Computer computerWithUncompatibleDates = new Computer.ComputerBuilder().withName("Computer5").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14")).withDiscontinuedDate(datesHandler.convertStringDateToSqlDate("2019-03-13")).withManufacturerId(1).build();
		assertTrue(computerDriver.addComputer(computer1));
		assertTrue(computerDriver.addComputer(computer2));
		assertFalse(computerDriver.addComputer(computerWithNullName));
		assertFalse(computerDriver.addComputer(computerWithUncompatibleDates));
		assertFalse(computerDriver.addComputer(computerWithUnexistingCompanyId));
	}

	@Test
	public void updateComputer() throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		assertTrue(computerDriver.updateComputer(1, "MacBook Pro 15.4 inch", null, null, 1));
		assertTrue(computerDriver.updateComputer(12, "Apple III", datesHandler.convertStringDateToSqlDate("1980-05-01"), datesHandler.convertStringDateToSqlDate("1984-04-01"), 1));
		assertTrue(computerDriver.updateComputer(574, "iphoe 4S", datesHandler.convertStringDateToSqlDate("2011-10-14"), null, 1));
		assertFalse(computerDriver.updateComputer(100000, "Titi acer v5.0", datesHandler.convertStringDateToSqlDate("2019-03-14"), null, 5));
		assertFalse(computerDriver.updateComputer(575, "Titi acer v5.0", datesHandler.convertStringDateToSqlDate("2019-03-14"), datesHandler.convertStringDateToSqlDate("2019-02-14"), 5));
	
	}
	
}
