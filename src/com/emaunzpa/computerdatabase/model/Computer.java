package com.emaunzpa.computerdatabase.model;

public class Computer {

	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private String manufacturer;
	
	public Computer(String name) {
		this.name = name;
		this.introducedDate = new String();
		this.discontinuedDate = new String();
		this.manufacturer = new String();
	}
	
	public Computer(String name, String introducedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = new String();
		this.manufacturer = new String();
	}
	
	public Computer(String name, String introducedDate, String discontinuedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturer = new String();
	}
	
	public Computer(String name, String introducedDate, String discontinuedDate, String manufacturer) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(String introducedDate) {
		this.introducedDate = introducedDate;
	}

	public String getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
}
