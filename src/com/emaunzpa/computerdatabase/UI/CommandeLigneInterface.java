package com.emaunzpa.computerdatabase.UI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.service.ComputerDriver;
import com.emaunzpa.computerdatabase.service.ManufacturerDriver;

public class CommandeLigneInterface {

	private String welcome;
	private String actionsHeader;
	private ArrayList<String> availableActions;
	private String goodBye;
	private int actualActionId;
	private String actionResult;
	private Scanner scIn = new Scanner(System.in);
	
	public CommandeLigneInterface() {
		this.actualActionId = 0;
		this.actionResult = "Following is the result of your request : ";
		this.welcome = "-------- Welcome in computer-database ! --------";
		this.goodBye = "-------- Thank you for visiting us and see you next time ! --------";
		this.actionsHeader = "Which action would you like to realize ? (ENTER the corresponding number)";
		this.availableActions = new ArrayList<String>( Arrays.asList("1) List all computers", "2) List all companies", "3) Show computer details", "4) Create a new computer", "5) Update a computer", "6) Delete a computer", "7) Leave computer-database"));
	}
	
	public void run() {
		System.out.println(welcome + "\n");
		while(actualActionId != 7) {
			System.out.println(actionsHeader);
			for(String action : availableActions) {
				System.out.println(action);
			}
			actualActionId = scIn.nextInt();
			System.out.println(actionResult + "\n");
			switch(actualActionId) {
				case 1 :
					ComputerDriver computerDriver = new ComputerDriver();
					ArrayList<Computer> computers = computerDriver.getAllComputers();
					for(Computer computer : computers) {
						String computerDetails = "Id : " + computer.getId() + "Name : " + computer.getName() + " | Introduced : " + computer.getIntroducedDate() + " | Discontinued : " + computer.getDiscontinuedDate() + " | Company_id : " + computer.getmanufacturerId();
						System.out.println(computerDetails);
					}
					System.out.println();
					break;
				case 2 :
					ManufacturerDriver manufacturerDriver = new ManufacturerDriver();
					ArrayList<Manufacturer> manufacturers = manufacturerDriver.getAllManufacturers();
					for(Manufacturer manufacturer : manufacturers) {
						String manufacturerDetails = "Id : " + manufacturer.getId() + " | Name : " + manufacturer.getName();
						System.out.println(manufacturerDetails);
					}
					System.out.println();
					break;
				case 7 : 
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
