package com.emaunzpa.computerdatabase.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
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
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Optional<Computer> getComputer(int id) throws NoComputerFoundException, SQLException, FileNotFoundException, IOException;
	
	/**
	 * Add a computer to the database
	 * @param computer
	 * @throws ComputerWithoutNameException 
	 * @throws DiscontinuedBeforeIntroducedException 
	 * @throws IncoherenceBetweenDateException 
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public boolean addComputer(Computer computer) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, SQLException, FileNotFoundException, IOException;
	
	/**
	 * List all computer from the database
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 */
	public ArrayList<Computer> getAllComputers() throws FileNotFoundException, IOException, SQLException;
	
	/**
	 * Remove computer with the given id from the database 
	 * @param id
	 * @throws NoComputerFoundException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 */
	public boolean removeComputer(int id) throws NoComputerFoundException, FileNotFoundException, IOException, SQLException;
	
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
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 */
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, IOException, SQLException;
	
}
