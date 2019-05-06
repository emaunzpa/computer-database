package com.emaunzpa.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.emaunzpa.db.ManufacturerDriver;
import com.emaunzpa.model.Manufacturer;

public class ManufacturerService {

	private ManufacturerDriver manufacturerDriver;
	
	public ManufacturerService() {}
	
	public ArrayList<Manufacturer> getAllManufacturers() throws FileNotFoundException, IOException, SQLException{
		return manufacturerDriver.getAllManufacturers();
	}

	public ManufacturerDriver getManufacturerDriver() {
		return manufacturerDriver;
	}

	public void setManufacturerDriver(ManufacturerDriver manufacturerDriver) {
		this.manufacturerDriver = manufacturerDriver;
	}
	
	
}
