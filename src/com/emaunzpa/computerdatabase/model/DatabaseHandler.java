package com.emaunzpa.computerdatabase.model;

import java.util.ArrayList;

public class DatabaseHandler {
	
	private static DatabaseHandler instance;
	private ArrayList<Computer> listComputers;
	private Manufacturers manufacturers;
	
	private DatabaseHandler() {
		
	}
	
	public static DatabaseHandler getInstance() {
		if (instance == null) {
			instance = new DatabaseHandler();
			instance.listComputers = new ArrayList<Computer>();
		}
		return instance;
	}

	public ArrayList<Computer> getListComputers() {
		return listComputers;
	}

	public void setListComputers(ArrayList<Computer> listComputers) {
		this.listComputers = listComputers;
	}

	public Manufacturers getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(Manufacturers manufacturers) {
		this.manufacturers = manufacturers;
	}

	public static void setInstance(DatabaseHandler instance) {
		DatabaseHandler.instance = instance;
	}
	
	public void addComputer(Computer computer) {
		this.listComputers.add(computer);
	}
	
	public void removeComputer(Computer computer) {
		this.listComputers.remove(computer);
	}
	
	
}
