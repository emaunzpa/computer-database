package com.emaunzpa.computerdatabase.model;

public class Computer {
	
	private int id;
	private String name;
	private java.sql.Date introducedDate;
	private java.sql.Date discontinuedDate;
	private Integer manufacturerId;
	
	public static class ComputerBuilder {
		
		private int id;
		private String name;
		private java.sql.Date introducedDate;
		private java.sql.Date discontinuedDate;
		private Integer manufacturerId;
		
		public ComputerBuilder() {

		}
		
		public ComputerBuilder withId(int id) {
			this.id = id;
			return this;
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
		
		public ComputerBuilder withManufacturerId(Integer manufacturerId) {
			this.manufacturerId = manufacturerId;
			return this;
		}
		
		public Computer build() {
			Computer computer = new Computer(this);
			computer.setId(this.id);
			computer.setName(this.name);
			computer.setIntroducedDate(this.introducedDate);
			computer.setDiscontinuedDate(this.discontinuedDate);
			computer.setmanufacturerId(this.manufacturerId);	
			return computer;
		}
	}
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introducedDate = builder.introducedDate;
		this.discontinuedDate = builder.discontinuedDate;
		this.manufacturerId = builder.manufacturerId;
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
