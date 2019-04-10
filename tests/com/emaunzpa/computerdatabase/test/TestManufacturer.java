package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

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
		manufacturerDriver = new ManufacturerDriver();
	}
	
	@Test
	public void getAllManufacturers() throws FileNotFoundException, IOException, SQLException {
		assertTrue(manufacturerDriver.getAllManufacturers().size() >= 0);
	}
	
	@Test
	public void getManufacturer() throws FileNotFoundException, IOException, SQLException {
		assertEquals("Apple Inc.", manufacturerDriver.getManufacturer(1).get().getName());
		assertEquals(0, manufacturerDriver.getManufacturer(0).get().getId());
		assertNull(manufacturerDriver.getManufacturer(0).get().getName());
	}

}
