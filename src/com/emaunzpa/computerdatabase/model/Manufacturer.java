package com.emaunzpa.computerdatabase.model;

public class Manufacturer {

	private String name;
	
	public Manufacturer() {
		this.name = new String();
	}
	
	public Manufacturer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
