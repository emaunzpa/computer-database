package com.emaunzpa.computerdatabase.DAO;

import java.util.ArrayList;

import com.emaunzpa.computerdatabase.model.Manufacturer;

public interface ManufacturerDAO {

	public Manufacturer getManufacturer(int id);
	
	public ArrayList<Manufacturer> getAllManufacturers();
}
