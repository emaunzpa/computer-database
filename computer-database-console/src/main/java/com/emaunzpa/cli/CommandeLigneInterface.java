package com.emaunzpa.cli;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import com.emaunzpa.db.ComputerDriver;
import com.emaunzpa.db.ManufacturerDriver;
import com.emaunzpa.dto.ComputerDTO;
import com.emaunzpa.exception.ComputerWithoutNameException;
import com.emaunzpa.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.exception.IncoherenceBetweenDateException;
import com.emaunzpa.exception.NoComputerFoundException;
import com.emaunzpa.exception.NoManufacturerFoundException;
import com.emaunzpa.model.Computer;
import com.emaunzpa.model.Manufacturer;
import com.emaunzpa.service.ComputerService;
import com.emaunzpa.util.CasesCLI;
import com.emaunzpa.util.ComputerFormValidator;
import com.emaunzpa.util.DatesHandler;
import com.emaunzpa.util.Pagination;

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
	private ComputerService computerService;
	private ComputerFormValidator computerFormValidator = new ComputerFormValidator();
	private ClientConfig clientConfig;
	private Client client;
	private WebTarget webTarget;
	
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
	    this.clientConfig = new ClientConfig();
		this.client = ClientBuilder.newClient(clientConfig);
	    this.webTarget = client.target(getBaseURI());
	}
	
	private static URI getBaseURI() {  
	    return UriBuilder.fromUri("http://localhost:8080/computer-database-webapp/api").build();  
	 }
	
	/**
	 * Show the list of all computers
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void showAllComputer() {
		System.out.println(actionResult + "\n");
		List<ComputerDTO> computers = webTarget.path("computer").request().accept(MediaType.APPLICATION_JSON).get(new GenericType<List<ComputerDTO>>(){});
		pagination.displayComputers(computers);
		System.out.println();
	}
	
	/**
	 * Show the list of all companies
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void showAllCompanies() {
		System.out.println(actionResult + "\n");
		List<Manufacturer> manufacturers = webTarget.path("manufacturer").request().accept(MediaType.APPLICATION_JSON).get(new GenericType<List<Manufacturer>>(){});
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
	public ComputerDTO showComputerDetails() throws NoComputerFoundException {
		System.out.println();
		System.out.println("ENTER a computer id...");
		int computerId = scIn.nextInt();
		scIn.nextLine();
		ComputerDTO computer = null;
		try {
			computer = webTarget.path("computer").path("get").path(String.valueOf(computerId)).request().accept(MediaType.APPLICATION_JSON).get(ComputerDTO.class);
		} catch (BadRequestException e) {
			System.out.println(e.getMessage());
			System.out.println("No computer found with this id " + computerId);
		}
		if (computer != null) {
			System.out.println();
			System.out.println("Following are the details of the selected computer \n");
			String computerDetails = "Id : " + computer.getId() + " | Name : " + computer.getName() + " | Introduced : " + computer.getIntroducedDate() + " | Discontinued : " + computer.getDiscontinuedDate() + " | Company_id : " + computer.getManufacturerId();
			System.out.println(computerDetails);
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
	 * @throws NoManufacturerFoundException 
	 */
	public Computer newComputerForm() throws ParseException, NoManufacturerFoundException {
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
	public void updateComputer() throws ParseException, NoComputerFoundException, ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		ComputerDTO computerDTO = showComputerDetails();
		System.out.println("Do you want to update the computer name ? (y/n)");
		String answer = scIn.next();
		scIn.nextLine();
		String newName = computerDTO.getName();
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new name for your computer ...");
				newName = scIn.nextLine();
				computerDTO.setName(newName);
				break;
			case "n" :
				break;
		}
		System.out.println("Do you want to update the computer introduced date ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		java.sql.Date newIntroducedDate = datesHandler.convertStringDateToSqlDate(computerDTO.getIntroducedDate());
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new introduced date for your computer (format : YYYY-MM-DD)");
				String newIntroducedDateStr = scIn.nextLine() + " 00:00:00";
            	newIntroducedDate = datesHandler.convertStringDateToSqlDate(newIntroducedDateStr);
            	computerDTO.setIntroducedDate(newIntroducedDateStr);
				break;
			case "n" :
				break;
		}
		System.out.println("Do you want to update the computer discontinued date ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		java.sql.Date newDiscontinuedDate = datesHandler.convertStringDateToSqlDate(computerDTO.getDiscontinuedDate());
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new discontinued date for your computer (format : YYYY-MM-DD)");
				String newDiscontinuedDateStr = scIn.nextLine() + " 00:00:00";
				newDiscontinuedDate = datesHandler.convertStringDateToSqlDate(newDiscontinuedDateStr);
				computerDTO.setDiscontinuedDate(newDiscontinuedDateStr);
				break;
			case "n" :
				break;
		}
		System.out.println("Do you want to update the company ID ? (y/n)");
		answer = scIn.next();
		scIn.nextLine();
		Integer newManufacturerId = computerDTO.getManufacturerId();
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new company ID for your computer ...");
				newManufacturerId = scIn.nextInt();
				computerDTO.setManufacturerId(newManufacturerId);
				scIn.nextLine();
				break;
			case "n" :
				break;
		}
		Computer computer = computerService.convertDTOtoComputer(computerDTO);
		if (!computerFormValidator.newComputerHasName(computer)) {
			System.out.println("The computer name is mandatory, please try again");
			System.out.println();
		}
		if (!computerFormValidator.introducedBeforeDiscontinued(computer)) {
			System.out.println("Introduced date must be before discontinued date");
			System.out.println();
		}
		else {
			System.out.println(Entity.json(computerDTO));
			webTarget.path("computer").path("edit").path(String.valueOf(computerDTO.getId())).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(computerDTO));
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
	public void removeComputer() throws NoComputerFoundException {
		ComputerDTO computerToRemove = showComputerDetails();
		if (computerToRemove != null) {
			System.out.println("You are gonna remove this computer from the database, are you sure ? (y/n)");
			String answer = scIn.next();
			scIn.nextLine();
			System.out.println();
			switch(answer) {
				case "y" :
						webTarget.path("computer").path("delete").path(String.valueOf(computerToRemove.getId())).request().accept(MediaType.TEXT_PLAIN).get(String.class);
						System.out.println("The computer was well removed from database !");
					break;
				case "n" :
					System.out.println("Remove request canceled");
					break;
			}
		}
		else {
			System.out.println("Remove request canceled");
		}
		
		System.out.println();
	}
	
	public Manufacturer showCompanyDetails() throws NoManufacturerFoundException {
		System.out.println();
		System.out.println("ENTER a company id...");
		int companyId = scIn.nextInt();
		scIn.nextLine();
		Manufacturer manufacturer = null;
		try {
			manufacturer = webTarget.path("manufacturer").path("get").path(String.valueOf(companyId)).request().accept(MediaType.APPLICATION_JSON).get(Manufacturer.class);
		} catch (InternalServerErrorException e) {
			System.out.println(e.getCause().getMessage());
			System.out.println("No manufacturer found with this id " + companyId);
		}
		if (manufacturer != null) {
			System.out.println();
			System.out.println("Following are the details of the selected company \n");
			String companyDetails = "Id : " + manufacturer.getId() + " | Name : " + manufacturer.getName();
			System.out.println(companyDetails);
			System.out.println();
		}
		
		return manufacturer;
	}
	
	private void deleteCompany() throws NoComputerFoundException, NoManufacturerFoundException {
		Manufacturer manufacturerToRemove = showCompanyDetails();
		if (manufacturerToRemove.getName() != null && !manufacturerToRemove.getName().equals("")) {
			System.out.println("You are gonna remove this company and its related " + getCompanyComputers(manufacturerToRemove.getId()).size() + " computers from the database, are you sure ? (y/n)");
			String answer = scIn.next();
			scIn.nextLine();
			System.out.println();
			switch(answer) {
				case "y" :
						webTarget.path("manufacturer").path("delete").path(String.valueOf(manufacturerToRemove.getId())).request().accept(MediaType.TEXT_PLAIN).get(String.class);
						System.out.println("The company was well removed from database !");
					break;
				case "n" :	
					System.out.println("Remove request canceled");
					break;
			}
		}
		
		System.out.println();
		
	}
	
	private List<ComputerDTO> getCompanyComputers(int id) throws NoManufacturerFoundException {
		
		List<ComputerDTO> companyComputers = new ArrayList<ComputerDTO>();
		
		List<ComputerDTO> computers = computerService.getAllComputers(null);
		for (ComputerDTO computerDTO : computers) {
			if (computerDTO.getManufacturerId() == id) {
				companyComputers.add(computerDTO);
			}
		}
		
		return companyComputers;
	}

	public void run() throws ParseException, ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, NoComputerFoundException, NoManufacturerFoundException {
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
					ComputerDTO newComputerDTO = computerService.convertComputerToDTO(newComputer);
					if (validateNewComputer(newComputer)) {
						webTarget.path("computer").path("add").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(newComputerDTO));
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
	
	public boolean validateNewComputer(Computer newComputer) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		if (!computerFormValidator.newComputerHasName(newComputer)) {
			System.out.println("The computer name is mandatory, please try again");
			System.out.println();
			return false;
		}
		else if (!computerFormValidator.introducedBeforeDiscontinued(newComputer)) {
			System.out.println("Introduced date must be before discontinued date");
			System.out.println();
			return false;
		}
		else {
			Integer manufacturerId = newComputer.getManufacturer() == null ? null : newComputer.getManufacturer().getId();
			String newComputerDetails = "A new Computer was created with following attributes : \n" + "Name : " + newComputer.getName() + " | Introduced : " + newComputer.getIntroducedDate() + " | Discontinued : " + newComputer.getDiscontinuedDate() + " | Company_id : " + manufacturerId;
			System.out.println(newComputerDetails);
			System.out.println();
			return true;
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

	public ComputerService getComputerService() {
		return computerService;
	}

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

}
