package com.emaunzpa.util.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.emaunzpa.exception.ComputerWithoutNameException;
import com.emaunzpa.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.exception.IncoherenceBetweenDateException;
import com.emaunzpa.exception.NoComputerFoundException;
import com.emaunzpa.exception.NoManufacturerFoundException;
import com.emaunzpa.model.Computer;
import com.emaunzpa.model.Manufacturer;
import com.emaunzpa.util.CompanyFormValidator;
import com.emaunzpa.util.ComputerFormValidator;
import com.emaunzpa.util.ComputerSpringValidator;

public class TestValidators {

	private CompanyFormValidator companyFormValidator = new CompanyFormValidator();
	private ComputerFormValidator computerFormValidator = new ComputerFormValidator();
	private ComputerSpringValidator computerSpringValidator = new ComputerSpringValidator();
	
	@Test
	public void companyValidator() {
		ArrayList<Manufacturer> manufacturers = new ArrayList<>();
		manufacturers.add(new Manufacturer(1, "m1"));
		manufacturers.add(new Manufacturer(2, "m2"));
		try {
			assertTrue(companyFormValidator.companyFound(manufacturers, 1));
		} catch (NoManufacturerFoundException e) {
			e.printStackTrace();
		}
		try {
			assertFalse(companyFormValidator.companyFound(manufacturers, 10));
		} catch (NoManufacturerFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void computerValidator() {
		
		ArrayList<Optional<Computer>> computers = new ArrayList<>();
		computers.add(Optional.of(new Computer.ComputerBuilder().withName("c1").withId(1).build()));
		computers.add(Optional.of(new Computer.ComputerBuilder().withName("c2").withId(2).build()));
		try {
			assertTrue(computerFormValidator.computerFound(computers, 1));
		} catch (NoComputerFoundException e) {
			e.printStackTrace();
		}
		try {
			assertFalse(computerFormValidator.computerFound(computers, 10));
		} catch (NoComputerFoundException e) {
			e.printStackTrace();
		}
		
		Computer computerWithName = new Computer.ComputerBuilder().withName("computerWithName").build();
		Computer computerWithoutName = new Computer.ComputerBuilder().build();
		Computer computerWithDatesIncoherence = new Computer.ComputerBuilder()
				.withIntroducedDate(Date.valueOf("2019-06-03"))
				.withDiscontinuedDate(Date.valueOf("2019-05-04"))
				.build();
		Computer computerWithoutIncoherence = new Computer.ComputerBuilder()
				.withName("cleanComputer")
				.build();
		
		try {
			assertTrue(computerFormValidator.newComputerHasName(computerWithName));
		} catch (ComputerWithoutNameException e) {
			e.printStackTrace();
		}
		try {
			assertFalse(computerFormValidator.newComputerHasName(computerWithoutName));
		} catch (ComputerWithoutNameException e) {
			e.printStackTrace();
		}
		try {
			assertTrue(computerFormValidator.introducedBeforeDiscontinued(computerWithoutIncoherence));
		} catch (IncoherenceBetweenDateException | DiscontinuedBeforeIntroducedException e) {
			e.printStackTrace();
		} 
		try {
			assertFalse(computerFormValidator.introducedBeforeDiscontinued(computerWithDatesIncoherence));
		} catch (IncoherenceBetweenDateException | DiscontinuedBeforeIntroducedException e) {
			e.printStackTrace();
		}
	}

}
