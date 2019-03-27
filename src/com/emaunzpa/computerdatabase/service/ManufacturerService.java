package com.emaunzpa.computerdatabase.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.emaunzpa.computerdatabase.bdd.ManufacturerDriver;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class ManufacturerService {

	private ManufacturerDriver manufacturerDriver;
	
	public ManufacturerService() {
		
		manufacturerDriver = new ManufacturerDriver("computer-database-db");
		
	}
	
	public ArrayList<Manufacturer> getAllManufacturers() throws FileNotFoundException, IOException, SQLException{
		return manufacturerDriver.getAllManufacturers();
	}
	
	
}
