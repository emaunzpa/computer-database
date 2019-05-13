package com.emaunzpa.util.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.emaunzpa.dto.ComputerDTO;
import com.emaunzpa.model.Manufacturer;
import com.emaunzpa.util.Pagination;

public class TestPagination {

	private Pagination pagination = new Pagination();
	
	@Test
	public void checkIndexes() {
		assertEquals(pagination.getStartIndex(), 0);
		assertEquals(pagination.getEndIndex(), 10);
	}
	
	@Test
	public void restrictedComputers() {
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		computers.add(new ComputerDTO.ComputerDTOBuilder().withName("c1").build());
		computers.add(new ComputerDTO.ComputerDTOBuilder().withName("c2").build());
		computers.add(new ComputerDTO.ComputerDTOBuilder().withName("c3").build());
		computers.add(new ComputerDTO.ComputerDTOBuilder().withName("c4").build());
		pagination.setStartIndex(2);
		pagination.setEndIndex(4);
		List<ComputerDTO> restrictedComputers = pagination.showRestrictedComputerList(computers);
		
		assertTrue(restrictedComputers.size() == 2);
		assertTrue(restrictedComputers.get(0).getName().equals("c3"));
	}
	
	@Test
	public void restrictedCompanies() {
		List<Manufacturer> manufacturers = new ArrayList<>();
		manufacturers.add(new Manufacturer("m1"));
		manufacturers.add(new Manufacturer("m2"));
		manufacturers.add(new Manufacturer("m3"));
		manufacturers.add(new Manufacturer("m4"));
		pagination.setStartIndex(0);
		pagination.setEndIndex(1);
		List<Manufacturer> restrictedManufacturers = pagination.showRestrictedManufacturerList(manufacturers);
		
		assertTrue(restrictedManufacturers.size() == 1);
		assertTrue(restrictedManufacturers.get(0).getName().equals("m1"));
	}

}
