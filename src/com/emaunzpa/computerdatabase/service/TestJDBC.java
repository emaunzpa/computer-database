package com.emaunzpa.computerdatabase.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.emaunzpa.computerdatabase.UI.CommandeLigneInterface;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class TestJDBC {

	public static void main(String[] args) throws ParseException {
				
		CommandeLigneInterface cli = new CommandeLigneInterface();
		cli.run();
		
	}

}
