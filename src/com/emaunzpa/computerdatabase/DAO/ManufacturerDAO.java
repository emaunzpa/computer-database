package com.emaunzpa.computerdatabase.DAO;

import java.util.ArrayList;

import com.emaunzpa.computerdatabase.model.Manufacturer;

public interface ManufacturerDAO {

	/**
	 * Get the manufacturer with the given id
	 * @param id
	 * @return
	 */
	public Manufacturer getManufacturer(int id);
	
	/**
	 * List all companies from the databaseeeeeeee
	 * @return
	 */
	public ArrayList<Manufacturer> getAllManufacturers();
}
