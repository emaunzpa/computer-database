package com.emaunzpa.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.emaunzpa.dto.ComputerDTO;
import com.emaunzpa.model.Computer;
import com.emaunzpa.model.Manufacturer;

public class Pagination {

	private int startIndex;
	private int endIndex;
	private int next10Index;
	private int next50Index;
	private int previous10Index;
	private int previous50Index;
	private int toEndIndex;
	private int toBeginIndex;
	private String sorted;
	private Scanner scIn = new Scanner(System.in);
	
	public Pagination(){
		this.startIndex = 0;
		this.endIndex = 10;
		this.sorted = "";
	}
	
	public List<Optional<Computer>> showRestrictedComputerList(ArrayList<Optional<Computer>> computers){
		
		return computers.subList(startIndex, endIndex);
		
	}
	
	public List<Manufacturer> showRestrictedManufacturerList(ArrayList<Manufacturer> manufacturers){
		
		return manufacturers.subList(startIndex, endIndex);
		
	}
	
	public void displayComputers(ArrayList<Optional<Computer>> computers) {
		
		String displayChoice = "";
		while(!displayChoice.equals("q")) {
			String actualDisplay = (startIndex + 1) + " to " + endIndex;
			System.out.println();
			System.out.println("                   --------- Type q to exit display ---------");
			System.out.println();
			for (Optional<Computer> computer : showRestrictedComputerList(computers)) {
				String companyName = computer.get().getManufacturer() != null ? computer.get().getManufacturer().getName() : "";
				String computerDetails = "Id : " + computer.get().getId() + " | Name : " + computer.get().getName() + " | Introduced : " + computer.get().getIntroducedDate() + " | Discontinued : " + computer.get().getDiscontinuedDate() + " | Company_name : " + companyName;
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

	public int getNext10Index() {
		return next10Index;
	}

	public void setNext10Index(int next10Index) {
		this.next10Index = next10Index;
	}

	public int getNext50Index() {
		return next50Index;
	}

	public void setNext50Index(int next50Index) {
		this.next50Index = next50Index;
	}

	public int getPrevious10Index() {
		return previous10Index;
	}

	public void setPrevious10Index(int previous10Index) {
		this.previous10Index = previous10Index;
	}

	public int getPrevious50Index() {
		return previous50Index;
	}

	public void setPrevious50Index(int previous50Index) {
		this.previous50Index = previous50Index;
	}

	public int getToEndIndex() {
		return toEndIndex;
	}

	public void setToEndIndex(int toEndIndex) {
		this.toEndIndex = toEndIndex;
	}

	public int getToBeginIndex() {
		return toBeginIndex;
	}

	public void setToBeginIndex(int toBeginIndex) {
		this.toBeginIndex = toBeginIndex;
	}

	public Scanner getScIn() {
		return scIn;
	}

	public void setScIn(Scanner scIn) {
		this.scIn = scIn;
	}

	public void initializeIndexes(List<ComputerDTO> computers) {
		
		this.setToBeginIndex(0);
		this.setPrevious50Index(differenceOrZero(50, this.getStartIndex()));
		this.setPrevious10Index(differenceOrZero(10, this.getStartIndex()));
		
		if (this.getEndIndex() + 10 <= computers.size()) {
			this.setNext10Index(this.getEndIndex() + 10);
		}
		else {
			if (computers.size() > 10) {
				this.setNext10Index(computers.size());
				this.setToEndIndex(computers.size());
			}
			else {
				this.setNext10Index(10);
				this.setToEndIndex(10);
			}
		}
		
		if (this.getEndIndex() + 50 <= computers.size()) {
			this.setNext50Index(this.getEndIndex() + 50);
		}
		else {
			if (computers.size() > 10) {
				this.setNext50Index(computers.size());
			}
			else {
				this.setNext50Index(10);
			}
		}
		
		if (computers.size() > 10) {
			this.setToEndIndex(computers.size());
		}
		else {
			this.setToEndIndex(10);
		}
		
	}
	
	public int differenceOrZero(int a, int b) {
		if (b - a > 0) {
			return b-a;
		}
		else {
			return 0;
		}
	}
	
	public void sortByName(List<ComputerDTO> computers) {
		
		computers.sort(new Comparator<ComputerDTO>() {

			@Override
			public int compare(ComputerDTO computer1, ComputerDTO computer2) {
				
				if (computer2.getName() == null) {
					return 1;
				}
				
				else if (computer1.getName() == null ){
					return -1;
				}
				
				else {
					return computer1.getName().compareTo(computer2.getName());
				}
			}
			
		});

	}
	
	public void sortByCompany(List<ComputerDTO> computers) {
		
		computers.sort(new Comparator<ComputerDTO>() {

			@Override
			public int compare(ComputerDTO computer1, ComputerDTO computer2) {
				
				if (computer2.getManufacturerName() == null) {
					return -1;
				}
				
				else if (computer1.getManufacturerName() == null ){
					return 1;
				}
				
				else {
					return computer1.getManufacturerName().compareTo(computer2.getManufacturerName());
				}
			}
			
		});

	}
	
	public void sortByIntroduced(List<ComputerDTO> computers) {
		
		computers.sort(new Comparator<ComputerDTO>() {

			@Override
			public int compare(ComputerDTO computer1, ComputerDTO computer2) {
				
				if (computer2.getIntroducedDate() == null) {
					return -1;
				}
				
				else if (computer1.getIntroducedDate() == null ){
					return 1;
				}
				
				else {
					return computer1.getIntroducedDate().compareTo(computer2.getIntroducedDate());
				}
			}
			
		});

	}
	
	public void sortByDiscontinued(List<ComputerDTO> computers) {
		
		computers.sort(new Comparator<ComputerDTO>() {

			@Override
			public int compare(ComputerDTO computer1, ComputerDTO computer2) {
				
				if (computer2.getDiscontinuedDate() == null) {
					return -1;
				}
				
				else if (computer1.getDiscontinuedDate() == null ){
					return 1;
				}
				
				else {
					return computer1.getDiscontinuedDate().compareTo(computer2.getDiscontinuedDate());
				}
			}
			
		});

	}

	public String getSorted() {
		return sorted;
	}

	public void setSorted(String sorted) {
		this.sorted = sorted;
	}
	
}
