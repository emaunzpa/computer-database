package com.emaunzpa.computerdatabase.model;

public class Computer {

	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private Manufacturer manufacturer;
	
	public Computer(String name) {
		this.name = name;
		this.introducedDate = new String();
		this.discontinuedDate = new String();
		this.manufacturer = new Manufacturer();
	}
	
	public Computer(String name, String introducedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = new String();
		this.manufacturer = new Manufacturer();
	}
	
	public Computer(String name, String introducedDate, String discontinuedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturer = new Manufacturer();
	}
	
	public Computer(String name, String introducedDate, String discontinuedDate, Manufacturer manufacturer) {
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

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
}
