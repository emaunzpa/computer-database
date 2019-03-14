package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.bdd.ManufacturerDriver;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.util.DatesHandler;

public class TestManufacturer {

	private ManufacturerDriver manufacturerDriver;
	
	@Before
	public void createManufacturerDriver() {
		manufacturerDriver = new ManufacturerDriver();
	}
	
	@Test
	public void getAllManufacturers() {
		assertNotNull(manufacturerDriver.getAllManufacturers());
	}
	
	@Test
	public void getManufacturer() {
		assertEquals("Apple Inc.", manufacturerDriver.getManufacturer(1).getName());
		assertEquals(0, manufacturerDriver.getManufacturer(0).getId());
		assertNull(manufacturerDriver.getManufacturer(0).getName());
	}

}
