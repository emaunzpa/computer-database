package com.emaunzpa.computerdatabase.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.emaunzpa.computerdatabase.model.Manufacturer;

public interface ManufacturerDAO {

	/**
	 * Get the manufacturer with the given id
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 */
	public Optional<Manufacturer> getManufacturer(int id) throws FileNotFoundException, IOException, SQLException;
	
	/**
	 * List all companies from the databaseeeeeeee
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 */
	public ArrayList<Manufacturer> getAllManufacturers() throws FileNotFoundException, IOException, SQLException;
}
