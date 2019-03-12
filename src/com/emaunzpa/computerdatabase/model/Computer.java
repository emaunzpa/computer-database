package com.emaunzpa.computerdatabase.model;

public class Computer {

	private int id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private Integer manufacturerId;
	
	public Computer() {
		this.name = null;
		this.introducedDate = null;
		this.discontinuedDate = null;
		this.manufacturerId = null;
	}
	
	public Computer(String name) {
		this.name = name;
		this.introducedDate = null;
		this.discontinuedDate = null;
		this.manufacturerId = null;
	}
	
	public Computer(String name, String introducedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = null;
		this.manufacturerId = null;
	}
	
	public Computer(String name, String introducedDate, String discontinuedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = null;
	}
	
	public Computer(String name, String introducedDate, String discontinuedDate, Integer manufacturerId) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = manufacturerId;
	}
	
	public Computer(int id, String name, String introducedDate, String discontinuedDate, Integer manufacturerId) {
		this.id = id;
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = manufacturerId;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getmanufacturerId() {
		return manufacturerId;
	}

	public void setmanufacturerId(Integer manufacturerId) {
		this.manufacturerId = manufacturerId;
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
	
}
