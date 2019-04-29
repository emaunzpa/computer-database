package com.emaunzpa.model;

public class Computer {
	
	private int id;
	private String name;
	private java.sql.Date introducedDate;
	private java.sql.Date discontinuedDate;
	private Manufacturer manufacturer;
	
	public Computer() {
		
	}
	
	public static class ComputerBuilder {
		
		private int id;
		private String name;
		private java.sql.Date introducedDate;
		private java.sql.Date discontinuedDate;
		private Manufacturer manufacturer;
		
		public ComputerBuilder() {

		}
		
		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerBuilder withIntroducedDate(java.sql.Date introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}
		
		public ComputerBuilder withDiscontinuedDate(java.sql.Date discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}
		
		public ComputerBuilder withManufacturer(Manufacturer manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}
		
		public Computer build() {
			Computer computer = new Computer(this);
			computer.setId(this.id);
			computer.setName(this.name);
			computer.setIntroducedDate(this.introducedDate);
			computer.setDiscontinuedDate(this.discontinuedDate);
			computer.setManufacturer(this.manufacturer);
			return computer;
		}
	}
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.manufacturer = builder.manufacturer;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	
}
