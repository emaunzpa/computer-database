package com.emaunzpa.computerdatabase.DAO;

import java.util.ArrayList;

import com.emaunzpa.computerdatabase.model.Computer;

public interface ComputerDAO {

	/**
	 * Get a computer with the given id
	 * @param id
	 * @return
	 */
	public Computer getComputer(int id);
	
	/**
	 * Add a computer to the database
	 * @param computer
	 */
	public boolean addComputer(Computer computer);
	
	/**
	 * List all computer from the database
	 * @return
	 */
	public ArrayList<Computer> getAllComputers();
	
	/**
	 * Remove computer with the given id from the database 
	 * @param id
	 */
	public boolean removeComputer(int id);
	
	/**
	 * Update the computer with the given id with the new attributes
	 * @param id
	 * @param newName
	 * @param newIntroduced
	 * @param newDiscontinued
	 * @param newManufacturerId
	 */
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId);
	
}
