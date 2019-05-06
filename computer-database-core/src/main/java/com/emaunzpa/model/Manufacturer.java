package com.emaunzpa.model;

public class Manufacturer {

	private int id;
	private String name;
	
	public Manufacturer() {
		
	}
	
	public Manufacturer(String name) {
		this.name = name;
	}
	
	public Manufacturer(int id, String name) {
		this.name = name;
		this.id = id;
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
	
	
}
