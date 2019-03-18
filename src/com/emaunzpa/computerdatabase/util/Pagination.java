package com.emaunzpa.computerdatabase.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class Pagination {

	private int startIndex;
	private int endIndex;
	private Scanner scIn = new Scanner(System.in);
	
	public Pagination(){
		this.startIndex = 0;
		this.endIndex = 10;
	}
	
	public List<Computer> showRestrictedComputerList(ArrayList<Computer> computers){
		
		return computers.subList(startIndex, endIndex);
		
	}
	
	public List<Manufacturer> showRestrictedManufacturerList(ArrayList<Manufacturer> manufacturers){
		
		return manufacturers.subList(startIndex, endIndex);
		
	}
	
	public void displayComputers(ArrayList<Computer> computers) {
		
		String displayChoice = "";
		while(!displayChoice.equals("q")) {
			String actualDisplay = (startIndex + 1) + " to " + endIndex;
			System.out.println();
			System.out.println("                   --------- Type q to exit display ---------");
			System.out.println();
			for (Computer computer : showRestrictedComputerList(computers)) {
				String computerDetails = "Id : " + computer.getId() + " | Name : " + computer.getName() + " | Introduced : " + computer.getIntroducedDate() + " | Discontinued : " + computer.getDiscontinuedDate() + " | Company_id : " + computer.getmanufacturerId();
				System.out.println(computerDetails);
			}
			System.out.println();
			System.out.println("              previous (p) <--------- " + actualDisplay + " ---------> next (n)");
			displayChoice = scIn.next();
			System.out.println(displayChoice);
			if (displayChoice.equals("p")) {
				if (startIndex > 10) {
					startIndex -= 10;
					endIndex -= 10;
				}
				else {
					while(startIndex > 0) {
						startIndex--;
						endIndex--;
					}
				}
			}
			else if (displayChoice.equals("n")) {
				if (endIndex + 10 < computers.size()) {
					endIndex += 10;
					startIndex += 10;
				}
				else {
					while(endIndex <= computers.size() - 1) {
						endIndex++;
						startIndex++;
					}
				}
			}
			scIn.nextLine();
		}
	}
	
	public void displayManufacturers(ArrayList<Manufacturer> manufacturers) {
		String displayChoice = "";
		while(!displayChoice.equals("q")) {
			String actualDisplay = (startIndex + 1) + " to " + endIndex;
			System.out.println();
			System.out.println("    --------- Type q to exit display ---------");
			System.out.println();
			for (Manufacturer manufacturer : showRestrictedManufacturerList(manufacturers)) {
				String manufacturerDetails = "Id : " + manufacturer.getId() + " | Name : " + manufacturer.getName();
				System.out.println(manufacturerDetails);
			}
			System.out.println();
			System.out.println(" previous (p) <--------- " + actualDisplay + " ---------> next (n)");
			displayChoice = scIn.next();
			System.out.println(displayChoice);
			if (displayChoice.equals("p")) {
				if (startIndex > 10) {
					startIndex -= 10;
					endIndex -= 10;
				}
				else {
					while(startIndex > 0) {
						startIndex--;
						endIndex--;
					}
				}
			}
			else if (displayChoice.equals("n")) {
				if (endIndex + 10 < manufacturers.size()) {
					endIndex += 10;
					startIndex += 10;
				}
				else {
					while(endIndex <= manufacturers.size() - 1) {
						endIndex++;
						startIndex++;
					}
				}
			}
			scIn.nextLine();
		}
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	
}
