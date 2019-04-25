package com.emaunzpa.computerdatabase.UI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.bdd.ManufacturerDriver;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.exception.NoManufacturerFoundException;
import com.emaunzpa.computerdatabase.util.CasesCLI;
import com.emaunzpa.computerdatabase.util.ComputerFormValidator;
import com.emaunzpa.computerdatabase.util.DatesHandler;
import com.emaunzpa.computerdatabase.util.Pagination;

public class CommandeLigneInterface {

	private String welcome;
	private String actionsHeader;
	private ArrayList<String> availableActions;
	private String goodBye;
	private int actualActionId;
	private String actionResult;
	private Scanner scIn = new Scanner(System.in);
	private ManufacturerDriver manufacturerDriver;
	private ComputerDriver computerDriver;
	private DatesHandler datesHandler = new DatesHandler();
	private Pagination pagination;
	private ComputerFormValidator computerFormValidator = new ComputerFormValidator();
	
	/**
	 * Creator to initialize various messages print by the controller
	 */
	public CommandeLigneInterface() {
		this.actualActionId = 0;
		this.actionResult = "Following is the result of your request : ";
		this.welcome = "-------- Welcome in computer-database ! --------";
		this.goodBye = "-------- Thank you for visiting us and see you next time ! --------";
		this.actionsHeader = "Which action would you like to realize ? (ENTER the corresponding number)\n";
		this.availableActions = new ArrayList<String>( Arrays.asList("1) List all computers", "2) List all companies", "3) Show computer details", "4) Create a new computer", "5) Update a computer", "6) Delete a computer", "7) Delete a company and its computers", "8) Leave computer-database"));
	}
	
	/**
	 * Show the list of all computers
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void showAllComputer() throws FileNotFoundException, IOException, SQLException {
		System.out.println(actionResult + "\n");
		ArrayList<Optional<Computer>> computers = computerDriver.getAllComputers();
		pagination.displayComputers(computers);
		System.out.println();
	}
	
	/**
	 * Show the list of all companies
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void showAllCompanies() throws FileNotFoundException, IOException, SQLException {
		System.out.println(actionResult + "\n");
		ArrayList<Manufacturer> manufacturers = manufacturerDriver.getAllManufacturers();
		pagination.displayManufacturers(manufacturers);
		System.out.println();
	}
	
	/**
	 * Show the details of a computer after listening to user entered id 
	 * @return computer
	 * @throws NoComputerFoundException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	public Computer showComputerDetails() throws NoComputerFoundException, FileNotFoundException, SQLException, IOException {
		System.out.println();
		System.out.println("ENTER a computer id...");
		int computerId = scIn.nextInt();
		scIn.nextLine();
		Computer computer;
		if (computerDriver.getComputer(computerId).isPresent()) {
			computer = computerDriver.getComputer(computerId).get();
			System.out.println();
			System.out.println("Following are the details of the selected computer \n");
			String computerDetails = "Id : " + computer.getId() + " | Name : " + computer.getName() + " | Introduced : " + computer.getIntroducedDate() + " | Discontinued : " + computer.getDiscontinuedDate() + " | Company_id : " + computer.getManufacturer().getId();
			System.out.println(computerDetails);
			System.out.println();
		}
		else {
			computer = new Computer.ComputerBuilder().build();
			System.out.println();
			System.out.println("Computer not found with this id : " + computerId);
			System.out.println();
		}
		
		return computer;
	}
	
	/**
	 * Create a new computer with the parameters entered by the user
	 * @return newComputer
	 * @throws ParseException
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public Computer newComputerForm() throws ParseException, FileNotFoundException, IOException, SQLException {
		System.out.println("You just chose to create a new Computer");
		System.out.println("Please ENTER a name for the new computer ...");
		String computerName = scIn.nextLine();
		System.out.println("Do you want to add an introduced date ? (y/n)");
		String answer = scIn.next();
		scIn.nextLine();
		java.sql.Date introducedDate = null;
		switch(answer) {
		case "y" :
			System.out.println("Please ENTER an introduced date ... (format : 'YYYY-MM-DD)");
			String introducedDateStr = scIn.nextLine() + " 00:00:00'";
			introducedDate = datesHandler.convertStringDateToSqlDate(introducedDateStr);
			break;
		case "n" :
			break;
		}
		System.out.println("Do you want to add an discontinued date ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		java.sql.Date discontinuedDate = null;
		switch(answer) {
		case "y" :
			System.out.println("Please ENTER an discontinued date ... (format : 'YYYY-MM-DD)");
			String discontinuedDateStr = scIn.nextLine() + " 00:00:00'";
			discontinuedDate = datesHandler.convertStringDateToSqlDate(discontinuedDateStr);
			break;
		case "n" :
			break;
		}
		System.out.println("Do you want to add a company ID ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		Integer companyId = null;
		Manufacturer manufacturer = null;
		switch(answer) {
		case "y" :
			System.out.println("Please ENTER a company ID ...");
			companyId = scIn.nextInt();
			scIn.nextLine();
			manufacturer = manufacturerDriver.getManufacturer(companyId);
			break;
		case "n" :
			break;
		}
		Computer newComputer = new Computer.ComputerBuilder().withName(computerName).withIntroducedDate(introducedDate).withDiscontinuedDate(discontinuedDate).withManufacturer(manufacturer).build();
		
		return newComputer;
	}
	
	/**
	 * Update the computer selected by the user with user's entered parameters
	 * @throws ParseException
	 * @throws NoComputerFoundException 
	 * @throws ComputerWithoutNameException 
	 * @throws DiscontinuedBeforeIntroducedException 
	 * @throws IncoherenceBetweenDateException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	public void updateComputer() throws ParseException, NoComputerFoundException, ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, SQLException, IOException {
		Computer computer = showComputerDetails();
		System.out.println("Do you want to update the computer name ? (y/n)");
		String answer = scIn.next();
		scIn.nextLine();
		String newName = computer.getName();
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new name for your computer ...");
				newName = scIn.nextLine();
				break;
			case "n" :
				break;
		}
		System.out.println("Do you want to update the computer introduced date ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		java.sql.Date newIntroducedDate = computer.getIntroducedDate();
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new introduced date for your computer (format : YYYY-MM-DD)");
				String newIntroducedDateStr = scIn.nextLine() + " 00:00:00";
            	newIntroducedDate = datesHandler.convertStringDateToSqlDate(newIntroducedDateStr);
				break;
			case "n" :
				break;
		}
		System.out.println("Do you want to update the computer discontinued date ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		java.sql.Date newDiscontinuedDate = computer.getDiscontinuedDate();
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new discontinued date for your computer (format : YYYY-MM-DD)");
				String newDiscontinuedDateStr = scIn.nextLine() + " 00:00:00";
				newDiscontinuedDate = datesHandler.convertStringDateToSqlDate(newDiscontinuedDateStr);
				break;
			case "n" :
				break;
		}
		System.out.println("Do you want to update the company ID ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		Integer newManufacturerId = computer.getManufacturer().getId();
		Manufacturer newManufacturer = manufacturerDriver.getManufacturer(newManufacturerId);
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new company ID for your computer ...");
				newManufacturerId = scIn.nextInt();
				scIn.nextLine();
				break;
			case "n" :
				break;
		}
		if (!computerFormValidator.newComputerHasName(computer)) {
			System.out.println("The computer name is mandatory, please try again");
			System.out.println();
		}
		if (!computerFormValidator.introducedBeforeDiscontinued(computer)) {
			System.out.println("Introduced date must be before discontinued date");
			System.out.println();
		}
		else {
			computerDriver.updateComputer(computer.getId(), newName, newIntroducedDate, newDiscontinuedDate, newManufacturer);
			String updateDetails = "Name : " + newName + " | Introduced : " + newIntroducedDate + " | Discontinued : " + newDiscontinuedDate + " | Company_id : " + newManufacturerId;
			System.out.println("The computer was update with the following parameters :\n " + updateDetails);
			System.out.println();
		}
		
	}
	
	/**
	 * Remove the computer with the id selected by the user
	 * @throws NoComputerFoundException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	public void removeComputer() throws NoComputerFoundException, FileNotFoundException, SQLException, IOException {
		Computer computerToRemove = showComputerDetails();
		if (computerToRemove.getName() != null && !computerToRemove.getName().equals("")) {
			System.out.println("You are gonna remove this computer from the database, are you sure ? (y/n)");
			String answer = scIn.next();
			scIn.nextLine();
			System.out.println();
			switch(answer) {
				case "y" :
						computerDriver.removeComputer(computerToRemove.getId());
						System.out.println("The computer was well removed from database !");
					break;
				case "n" :	
					System.out.println("Remove request canceled");
					break;
			}
		}
		
		System.out.println();
	}
	
	public Manufacturer showCompanyDetails() throws FileNotFoundException, IOException, SQLException {
		System.out.println();
		System.out.println("ENTER a company id...");
		int companyId = scIn.nextInt();
		scIn.nextLine();
		Manufacturer manufacturer;
		if (manufacturerDriver.getManufacturer(companyId) != null) {
			manufacturer = manufacturerDriver.getManufacturer(companyId);
			System.out.println();
			System.out.println("Following are the details of the selected company \n");
			String companyDetails = "Id : " + manufacturer.getId() + " | Name : " + manufacturer.getName();
			System.out.println(companyDetails);
			System.out.println();
		}
		else {
			manufacturer = new Manufacturer();
			System.out.println();
			System.out.println("Company not found with this id : " + companyId);
			System.out.println();
		}
		
		return manufacturer;
	}
	
	private void deleteCompany() throws FileNotFoundException, IOException, SQLException, NoComputerFoundException, NoManufacturerFoundException {
		Manufacturer manufacturerToRemove = showCompanyDetails();
		if (manufacturerToRemove.getName() != null && !manufacturerToRemove.getName().equals("")) {
			System.out.println("You are gonna remove this company and its related " + getCompanyComputers(manufacturerToRemove.getId()).size() + " computers from the database, are you sure ? (y/n)");
			String answer = scIn.next();
			scIn.nextLine();
			System.out.println();
			switch(answer) {
				case "y" :
						manufacturerDriver.removeManufacturer(manufacturerToRemove.getId());
						System.out.println("The company was well removed from database !");
					break;
				case "n" :	
					System.out.println("Remove request canceled");
					break;
			}
		}
		
		System.out.println();		
		
	}
	
	private List<Optional<Computer>> getCompanyComputers(int id) throws FileNotFoundException, IOException, SQLException {
		
		List<Optional<Computer>> companyComputers = new ArrayList<Optional<Computer>>();
		
		List<Optional<Computer>> computers = computerDriver.getAllComputers();
		for (Optional<Computer> computer : computers) {
			if (computer.get().getManufacturer().getId() == id) {
				companyComputers.add(computer);
			}
		}
		
		return companyComputers;
	}

	public void run() throws ParseException, ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, NoComputerFoundException, FileNotFoundException, IOException, SQLException, NoManufacturerFoundException {
		System.out.println(welcome + "\n");
		while(actualActionId != 8) {
			System.out.println(actionsHeader);
			for(String action : availableActions) {
				System.out.println(action);
			}
			actualActionId = scIn.nextInt();
			scIn.nextLine();
			
			switch(CasesCLI.values()[actualActionId - 1]) {
				case LIST_COMPUTERS :
					showAllComputer();
					break;
				case LIST_COMPANIES :
					showAllCompanies();
					break;
				case SHOW_COMPUTER_DETAILS :
					showComputerDetails();
					break;
				case CREATE_COMPUTER :
					Computer newComputer = newComputerForm();
					if (!computerFormValidator.newComputerHasName(newComputer)) {
						System.out.println("The computer name is mandatory, please try again");
						System.out.println();
					}
					else if (!computerFormValidator.introducedBeforeDiscontinued(newComputer)) {
						System.out.println("Introduced date must be before discontinued date");
						System.out.println();
					}
					else {
						String newComputerDetails = "A new Computer was created with following attributes : \n" + "Name : " + newComputer.getName() + " | Introduced : " + newComputer.getIntroducedDate() + " | Discontinued : " + newComputer.getDiscontinuedDate() + " | Company_id : " + newComputer.getManufacturer().getId();
						System.out.println(newComputerDetails);
						System.out.println();
						computerDriver.addComputer(newComputer);
						System.out.println("The new computer was well added to the computer-database !\n");
					}
					break;
				case UPDATE_COMPUTER :
					updateComputer();
					break;
				case DELETE_COMPUTER :
					removeComputer();
					break;
				case DELETE_COMPANY :
					deleteCompany();
					break;
				case EXIT : 
					System.out.println(goodBye);
					break;
				default :
					System.out.println("Pas d'action");
					System.out.println();
					break;	
			}
		}
	}

	public ManufacturerDriver getManufacturerDriver() {
		return manufacturerDriver;
	}

	public void setManufacturerDriver(ManufacturerDriver manufacturerDriver) {
		this.manufacturerDriver = manufacturerDriver;
	}

	public ComputerDriver getComputerDriver() {
		return computerDriver;
	}

	public void setComputerDriver(ComputerDriver computerDriver) {
		this.computerDriver = computerDriver;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

}
