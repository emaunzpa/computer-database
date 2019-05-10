package com.emaunzpa.service;

import java.util.ArrayList;
import com.emaunzpa.db.ManufacturerDriver;
import com.emaunzpa.model.Manufacturer;

public class ManufacturerService {

	private ManufacturerDriver manufacturerDriver;
	
	public ManufacturerService() {}
	
	public ArrayList<Manufacturer> getAllManufacturers() {
		return manufacturerDriver.getAllManufacturers();
	}

	public ManufacturerDriver getManufacturerDriver() {
		return manufacturerDriver;
	}

	public void setManufacturerDriver(ManufacturerDriver manufacturerDriver) {
		this.manufacturerDriver = manufacturerDriver;
	}
	
	
}
