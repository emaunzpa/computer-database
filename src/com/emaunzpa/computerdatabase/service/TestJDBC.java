package com.emaunzpa.computerdatabase.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.emaunzpa.computerdatabase.UI.CommandeLigneInterface;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class TestJDBC {

	public static void main(String[] args) {
		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		
//		ComputerDriver cptDriver = new ComputerDriver();
//		ManufacturerDriver mfDriver = new ManufacturerDriver();
//		
//		LocalDateTime currentTime = LocalDateTime.now();
//		LocalDateTime dateTime = currentTime.withYear(currentTime.getYear() + 2);
//		String currentDateTime = "'" + currentTime.format(formatter) +"'";
//		String currentDateTimeModified = "'" + dateTime.format(formatter) + "'";
//		
//		Manufacturer manu = mfDriver.getManufacturer(33);
//	    cptDriver.updateComputer(575, "'Titi acer v3.0'", currentDateTime, currentDateTimeModified, 1);
//		
//		System.out.println(manu.getName());
		
		CommandeLigneInterface cli = new CommandeLigneInterface();
		cli.run();
	}

}
