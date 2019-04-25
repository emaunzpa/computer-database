package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.util.DatesHandler;

/**
 * Tests for the backend methods of ComputerDAO
 */	
public class TestComputer {
	
	private ComputerDriver computerDriver;
	private DatesHandler datesHandler;
	public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("Beans.xml");

	@Before
	public void createComputerDriver() {
		computerDriver = (ComputerDriver) CONTEXT.getBean("computerDriver");
		datesHandler = new DatesHandler();
	}
	
	@Test
	public void getComputer() throws NoComputerFoundException, FileNotFoundException, SQLException, IOException {
		Computer testedComputer = computerDriver.getComputer(5).get();
		assertEquals("CM-5", testedComputer.getName());
		assertEquals("1991-01-01", datesHandler.convertSqlDateToString(testedComputer.getIntroducedDate()));
		assertNull(testedComputer.getDiscontinuedDate());
		assertEquals(2, (int) testedComputer.getManufacturer().getId());
		try {
			Computer testedComputerNull = computerDriver.getComputer(0).get();
			assertEquals(0, testedComputerNull.getId());
			assertNull(testedComputerNull.getName());
			assertNull(testedComputerNull.getIntroducedDate());
			assertNull(testedComputerNull.getDiscontinuedDate());
			assertNull(testedComputerNull.getManufacturer().getId());
		} catch (Exception e) {
			assertTrue(e.getClass().equals(NoComputerFoundException.class));
		}
		
	}
	
	@Test
	public void getAllComputers() throws FileNotFoundException, IOException, SQLException {
		ArrayList<Optional<Computer>> computers = computerDriver.getAllComputers();
		assertTrue(computers.size() >= 0);
	}
	
	@Test
	public void createComputer() throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, SQLException, IOException {
		
		Computer computer1 = new Computer.ComputerBuilder().withName("Computer1").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14")).build();
		Computer computer2 = new Computer.ComputerBuilder().withName("Computer2").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14")).withDiscontinuedDate(null).withManufacturer(new Manufacturer(5, "Tandy Corporation")).build();
		Computer computerWithNullName = new Computer.ComputerBuilder().build();
		Computer computerWithUnexistingCompanyId = new Computer.ComputerBuilder().withName("Computer4").withIntroducedDate(null).withDiscontinuedDate(null).withManufacturer(new Manufacturer(1000, "Wrong ID manufacturer")).build();
		Computer computerWithUncompatibleDates = new Computer.ComputerBuilder().withName("Computer5").withIntroducedDate(datesHandler.convertStringDateToSqlDate("2019-03-14")).withDiscontinuedDate(datesHandler.convertStringDateToSqlDate("2019-03-13")).withManufacturer(new Manufacturer(1, "Apple Inc.")).build();
		assertTrue(computerDriver.addComputer(computer1));
		assertTrue(computerDriver.addComputer(computer2));
		try {
			assertFalse(computerDriver.addComputer(computerWithNullName));
		} catch (Exception e) {
			assertTrue(e.getClass().equals(ComputerWithoutNameException.class));
		}
		try {
			assertFalse(computerDriver.addComputer(computerWithUncompatibleDates));
		} catch (Exception e) {
			assertTrue(e.getClass().equals(DiscontinuedBeforeIntroducedException.class));
		}
		
		assertFalse(computerDriver.addComputer(computerWithUnexistingCompanyId));
		
	}

	@Test
	public void updateComputer() throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, IOException, SQLException {
		
		assertTrue(computerDriver.updateComputer(1, "MacBook Pro 15.4 inch", null, null, new Manufacturer(1, "Apple Inc.")));
		assertTrue(computerDriver.updateComputer(12, "Apple III", datesHandler.convertStringDateToSqlDate("1980-05-01"), datesHandler.convertStringDateToSqlDate("1984-04-01"), new Manufacturer(1, "Apple Inc.")));
		try {
			assertFalse(computerDriver.updateComputer(100000, "Titi acer v5.0", datesHandler.convertStringDateToSqlDate("2019-03-14"), null, new Manufacturer(5, "Tandy Corporation")));
		} catch (Exception e) {
			assertTrue(e.getClass().equals(NoComputerFoundException.class));
		}
		
	}
	
}
