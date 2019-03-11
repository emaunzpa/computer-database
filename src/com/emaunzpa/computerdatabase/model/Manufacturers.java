package com.emaunzpa.computerdatabase.model;

public enum Manufacturers {
	
	ASUS("Asus", 1),
	ACER("Acer", 2),
	APPLE("Apple", 3),
	DELL("Dell", 4);
	
	private String name = null;
	private Integer id = null;
	
	Manufacturers(String name, Integer id) {
		this.name = name;
		this.id = id;
	}	
	
	public String toString() {
		return name;
	}
}
