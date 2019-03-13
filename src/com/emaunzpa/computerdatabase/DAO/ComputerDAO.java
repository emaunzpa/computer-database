package com.emaunzpa.computerdatabase.DAO;

import java.util.ArrayList;

import com.emaunzpa.computerdatabase.model.Computer;

public interface ComputerDAO {

	public Computer getComputer(int id);
	
	public void addComputer(Computer computer);
	
	public ArrayList<Computer> getAllComputers();
	
	public void removeComputer(int id);
	
	public void updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId);
	
}
