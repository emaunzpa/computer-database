package com.emaunzpa.computerdatabase.UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.service.ComputerDriver;
import com.emaunzpa.computerdatabase.service.ManufacturerDriver;
import com.emaunzpa.computerdatabase.util.CasesCLI;
import com.emaunzpa.computerdatabase.util.DatesHandler;

public class CommandeLigneInterface {

	private String welcome;
	private String actionsHeader;
	private ArrayList<String> availableActions;
	private String goodBye;
	private int actualActionId;
	private String actionResult;
	private Scanner scIn = new Scanner(System.in);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ManufacturerDriver manufacturerDriver = new ManufacturerDriver();
	private ComputerDriver computerDriver = new ComputerDriver();
	private DatesHandler datesHandler = new DatesHandler();
	
	/**
	 * Creator to initialize various messages print by the controller
	 */
	public CommandeLigneInterface() {
		this.actualActionId = 0;
		this.actionResult = "Following is the result of your request : ";
		this.welcome = "-------- Welcome in computer-database ! --------";
		this.goodBye = "-------- Thank you for visiting us and see you next time ! --------";
		this.actionsHeader = "Which action would you like to realize ? (ENTER the corresponding number)";
		this.availableActions = new ArrayList<String>( Arrays.asList("1) List all computers", "2) List all companies", "3) Show computer details", "4) Create a new computer", "5) Update a computer", "6) Delete a computer", "7) Leave computer-database"));
	}
	
	/**
	 * Show the list of all computers
	 */
	public void showAllComputer() {
		System.out.println(actionResult + "\n");
		ArrayList<Computer> computers = computerDriver.getAllComputers();
		for(Computer computer : computers) {
			String computerDetails = "Id : " + computer.getId() + " | Name : " + computer.getName() + " | Introduced : " + computer.getIntroducedDate() + " | Discontinued : " + computer.getDiscontinuedDate() + " | Company_id : " + computer.getmanufacturerId();
			System.out.println(computerDetails);
		}
		System.out.println();
	}
	
	/**
	 * Show the list of all companies
	 */
	public void showAllCompanies() {
		System.out.println(actionResult + "\n");
		ArrayList<Manufacturer> manufacturers = manufacturerDriver.getAllManufacturers();
		for(Manufacturer manufacturer : manufacturers) {
			String manufacturerDetails = "Id : " + manufacturer.getId() + " | Name : " + manufacturer.getName();
			System.out.println(manufacturerDetails);
		}
		System.out.println();
	}
	
	/**
	 * Show the details of a computer after listening to user entered id 
	 * @return computer
	 */
	public Computer showComputerDetails() {
		System.out.println("ENTER a computer id...");
		int computerId = scIn.nextInt();
		scIn.nextLine();
		Computer computer = computerDriver.getComputer(computerId);
		System.out.println("This is the details of the selected computer \n");
		String computerDetails = "Id : " + computer.getId() + " | Name : " + computer.getName() + " | Introduced : " + computer.getIntroducedDate() + " | Discontinued : " + computer.getDiscontinuedDate() + " | Company_id : " + computer.getmanufacturerId();
		System.out.println(computerDetails);
		System.out.println();
		return computer;
	}
	
	/**
	 * Create a new computer with the parameters entered by the user
	 * @return newComputer
	 * @throws ParseException
	 */
	public Computer newComputerForm() throws ParseException {
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
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a company ID ...");
				companyId = scIn.nextInt();
				scIn.nextLine();
				break;
			case "n" :
				break;
		}
		Computer newComputer = new Computer(computerName, introducedDate, discontinuedDate, companyId);
		String newComputerDetails = "A new Computer was created with following attributes : \n" + "Name : " + newComputer.getName() + " | Introduced : " + newComputer.getIntroducedDate() + " | Discontinued : " + newComputer.getDiscontinuedDate() + " | Company_id : " + newComputer.getmanufacturerId();
		System.out.println(newComputerDetails);
		System.out.println();
		return newComputer;
	}
	
	/**
	 * Update the computer selected by the user with user's entered parameters
	 * @throws ParseException
	 */
	public void updateComputer() throws ParseException {
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
		Integer newManufacturerId = computer.getmanufacturerId();
		switch(answer) {
			case "y" :
				System.out.println("Please ENTER a new company ID for your computer ...");
				newManufacturerId = scIn.nextInt();
				scIn.nextLine();
				break;
			case "n" :
				break;
		}
		computerDriver.updateComputer(computer.getId(), newName, newIntroducedDate, newDiscontinuedDate, newManufacturerId);
		String updateDetails = "Name : " + newName + " | Introduced : " + newIntroducedDate + " | Discontinued : " + newDiscontinuedDate + " | Company_id : " + newManufacturerId;
		System.out.println("The computer was update with the following parameters :\n " + updateDetails);
		System.out.println();
	}
	
	/**
	 * Remove the computer with the id selected by the user
	 */
	public void removeComputer() {
		Computer computerToRemove = showComputerDetails();
		System.out.println("You are gonna remove this computer from the database, are you sure ? (y/n)");
		String answer = scIn.next();
		scIn.nextLine();
		switch(answer) {
			case "y" :
				computerDriver.removeComputer(computerToRemove.getId());
				System.out.println("The computer was well removed from database !");
				break;
			case "n" :
				System.out.println("Remove request canceled");
				break;
		}
		System.out.println();
	}
	
	public void run() throws ParseException {
		System.out.println(welcome + "\n");
		while(actualActionId != 7) {
			System.out.println(actionsHeader);
			for(String action : availableActions) {
				System.out.println(action);
			}
			actualActionId = scIn.nextInt();
			scIn.nextLine();
			CasesCLI casesCLI = null;
			for (CasesCLI loopCase : CasesCLI.values()) {
				if (loopCase.getChoice() == actualActionId) {
					casesCLI = loopCase;
				}
			}
			switch(casesCLI) {
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
					computerDriver.addComputer(newComputer);
					System.out.println("The new computer was well added to the computer-database !");
					break;
				case UPDATE_COMPUTER :
					updateComputer();
					break;
				case DELETE_COMPUTER :
					removeComputer();
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
}
