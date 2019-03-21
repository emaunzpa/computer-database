package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.emaunzpa.computerdatabase.bdd.ManufacturerDriver;


/**
 * Tests for the backend methods of ManufacturerDAO
 */
public class TestManufacturer {

	private ManufacturerDriver manufacturerDriver;
	
	@Before
	public void createManufacturerDriver() {
		manufacturerDriver = new ManufacturerDriver("computer-database-db-test");
	}
	
	@Test
	public void getAllManufacturers() {
		assertTrue(manufacturerDriver.getAllManufacturers().size() >= 0);
	}
	
	@Test
	public void getManufacturer() {
		assertEquals("Apple Inc.", manufacturerDriver.getManufacturer(1).get().getName());
		assertEquals(0, manufacturerDriver.getManufacturer(0).get().getId());
		assertNull(manufacturerDriver.getManufacturer(0).get().getName());
	}

}
