package com.emaunzpa.computerdatabase.model;

public class Computer {

	private int id;
	private String name;
	private java.sql.Date introducedDate;
	private java.sql.Date discontinuedDate;
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
	
	public Computer(String name, java.sql.Date introducedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = null;
		this.manufacturerId = null;
	}
	
	public Computer(String name, java.sql.Date introducedDate, java.sql.Date discontinuedDate) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = null;
	}
	
	public Computer(String name, java.sql.Date introducedDate, java.sql.Date discontinuedDate, Integer manufacturerId) {
		this.name = name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.manufacturerId = manufacturerId;
	}
	
	public Computer(int id, String name, java.sql.Date introducedDate, java.sql.Date discontinuedDate, Integer manufacturerId) {
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

	public java.sql.Date getIntroducedDate() {
		return introducedDate;
	}

	public void setIntroducedDate(java.sql.Date introducedDate) {
		this.introducedDate = introducedDate;
	}

	public java.sql.Date getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(java.sql.Date discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	
}
