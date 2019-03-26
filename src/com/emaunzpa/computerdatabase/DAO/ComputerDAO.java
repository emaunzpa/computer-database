package com.emaunzpa.computerdatabase.DAO;

import java.util.ArrayList;
import java.util.Optional;

import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.Computer;

public interface ComputerDAO {

	/**
	 * Get a computer with the given id
	 * @param id
	 * @return
	 * @throws NoComputerFoundException 
	 */
	public Optional<Computer> getComputer(int id) throws NoComputerFoundException;
	
	/**
	 * Add a computer to the database
	 * @param computer
	 * @throws ComputerWithoutNameException 
	 * @throws DiscontinuedBeforeIntroducedException 
	 * @throws IncoherenceBetweenDateException 
	 */
	public boolean addComputer(Computer computer) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException;
	
	/**
	 * List all computer from the database
	 * @return
	 */
	public ArrayList<Computer> getAllComputers();
	
	/**
	 * Remove computer with the given id from the database 
	 * @param id
	 * @throws NoComputerFoundException 
	 */
	public boolean removeComputer(int id) throws NoComputerFoundException;
	
	/**
	 * Update the computer with the given id with the new attributes
	 * @param id
	 * @param newName
	 * @param newIntroduced
	 * @param newDiscontinued
	 * @param newManufacturerId
	 * @throws NoComputerFoundException 
	 * @throws DiscontinuedBeforeIntroducedException 
	 * @throws IncoherenceBetweenDateException 
	 */
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException;
	
}
