package com.emaunzpa.computerdatabase.model;

public enum Manufacturers {
	
	ASUS("Asus"),
	ACER("Acer"),
	APPLE("Apple"),
	DELL("Dell");
	
	private String name = "";
	
	Manufacturers(String name) {
		this.name = name;
	}	
	
	public String toString() {
		return name;
	}
}
