package com.emaunzpa.computerdatabase.service;

import java.util.ArrayList;

import com.emaunzpa.computerdatabase.bdd.ManufacturerDriver;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class ManufacturerService {

	private ManufacturerDriver manufacturerDriver;
	
	public ManufacturerService() {
		
		manufacturerDriver = new ManufacturerDriver("computer-database-db");
		
	}
	
	public ArrayList<Manufacturer> getAllManufacturers(){
		return manufacturerDriver.getAllManufacturers();
	}
	
	
}
