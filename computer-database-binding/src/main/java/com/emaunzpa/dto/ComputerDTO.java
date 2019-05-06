package com.emaunzpa.dto;

public class ComputerDTO {

	private int id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private int manufacturerId;
	private String manufacturerName;
	
	public static class ComputerDTOBuilder {
		
		private int id;
		private String name;
		private String introducedDate;
		private String discontinuedDate;
		private int manufacturerId;
		private String manufacturerName;
		
		public ComputerDTOBuilder() {

		}
		
		public ComputerDTOBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerDTOBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerDTOBuilder withIntroducedDate(String introducedDate) {
			this.introducedDate = introducedDate;
			return this;
		}
		
		public ComputerDTOBuilder withDiscontinuedDate(String discontinuedDate) {
			this.discontinuedDate = discontinuedDate;
			return this;
		}
		
		public ComputerDTOBuilder withManufacturerId(Integer manufacturerId) {
			this.manufacturerId = manufacturerId;
			return this;
		}
		
		public ComputerDTOBuilder withManufacturerName(String manufacturerName) {
			this.manufacturerName = manufacturerName;
			return this;
		}
		
		public ComputerDTO build() {
			ComputerDTO computerDTO = new ComputerDTO(this);
			computerDTO.setId(this.id);
			computerDTO.setName(this.name);
			computerDTO.setIntroducedDate(this.introducedDate);
			computerDTO.setDiscontinuedDate(this.discontinuedDate);
			computerDTO.setmanufacturerId(this.manufacturerId);	
			computerDTO.setManufacturerName(this.manufacturerName);
			return computerDTO;
		}
	}
	
	private ComputerDTO(ComputerDTOBuilder computerDTOBuilder) {
		this.id = computerDTOBuilder.id;
		this.name = computerDTOBuilder.name;
		this.introducedDate = computerDTOBuilder.introducedDate;
		this.discontinuedDate = computerDTOBuilder.discontinuedDate;
		this.manufacturerId = computerDTOBuilder.manufacturerId;
		this.manufacturerName = computerDTOBuilder.manufacturerName;
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

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	
}
