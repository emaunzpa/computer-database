package com.emaunzpa.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.emaunzpa.db.ComputerDriver;
import com.emaunzpa.dto.ComputerDTO;
import com.emaunzpa.exception.ComputerWithoutNameException;
import com.emaunzpa.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.exception.IncoherenceBetweenDateException;
import com.emaunzpa.exception.NoComputerFoundException;
import com.emaunzpa.model.Computer;
import com.emaunzpa.model.Manufacturer;
import com.emaunzpa.util.CasesSorted;
import com.emaunzpa.util.DatesHandler;
import com.emaunzpa.util.Pagination;

public class ComputerService {
	
	private Pagination pagination;
	private ComputerDriver computerDriver;
	DatesHandler dh = new DatesHandler();

	public ComputerService() {}
	
	/**
	 * We initiate pagination with parameters passed trough url.
	 * If params are null, the default params are set to 0 and 10 to print the 10 first computers
	 */
	public void initializePagination(String startIndex, String endIndex, List<ComputerDTO> computers) {
		
		if (startIndex != null) {
			pagination.setStartIndex(Integer.valueOf(startIndex));
		}
		if (endIndex != null) {
			pagination.setEndIndex(Integer.valueOf(endIndex));
		}
		
		pagination.initializeIndexes(computers);
		
	}
	
	public List<ComputerDTO> getAllComputers(String search) {
		ArrayList<Optional<Computer>> computers = new ArrayList<>();
		String searchStr = search;
		if (searchStr != null && !searchStr.equals("")){
			for (Optional<Computer> computerTested : computerDriver.getAllComputers()) {
				Pattern pattern = Pattern.compile(".*" + searchStr.toLowerCase() + ".*");
				Matcher macherComputerName = pattern.matcher(computerTested.get().getName().toLowerCase());
								
				if (computerTested.get().getManufacturer() != null) {
					Matcher macherCompanyName = pattern.matcher(computerTested.get().getManufacturer().getName().toLowerCase());
					if (macherCompanyName.matches() || macherComputerName.matches()) {
						computers.add(computerTested);
					}
				}
				
				else {
					if (macherComputerName.matches()) {
						computers.add(computerTested);
					}
				}
			}
		}
		else {
			computers = computerDriver.getAllComputers();
		}
		return getAllDTOs(computers);
	}
	
	public List<ComputerDTO> restrictedListComputers() {
		List<ComputerDTO> computers = getAllComputers(null);
		List<ComputerDTO> restrictedList = pagination.showRestrictedComputerList(computers);
		return restrictedList;
	}
	
	public ComputerDTO convertComputerToDTO(Computer computer) {
		
		int companyId = computer.getManufacturer() != null ? computer.getManufacturer().getId() : 0;
		String manufacturerName = computer.getManufacturer() != null ? computer.getManufacturer().getName() : "";

		ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder()
				.withId(computer.getId())
				.withName(computer.getName())
				.withIntroducedDate(dh.convertSqlDateToString(computer.getIntroducedDate()))
				.withDiscontinuedDate(dh.convertSqlDateToString(computer.getDiscontinuedDate()))
				.withManufacturerId(companyId)
				.withManufacturerName(manufacturerName)
				.build();
		return computerDTO;
	}
	
	public ComputerDTO getComputer(int id) throws NoComputerFoundException {
		return convertComputerToDTO(computerDriver.getComputer(id).get());
	}
	
	public Computer convertDTOtoComputer(ComputerDTO computerDTO) {
		
		Manufacturer manufacturer = computerDTO.getManufacturerId() != 0 ? new Manufacturer(computerDTO.getManufacturerId(), computerDTO.getManufacturerName()) : null;
		
		Computer computer = new Computer.ComputerBuilder()
				.withName(computerDTO.getName())
				.withIntroducedDate(dh.convertStringDateToSqlDate(computerDTO.getIntroducedDate()))
				.withDiscontinuedDate(dh.convertStringDateToSqlDate(computerDTO.getDiscontinuedDate()))
				.withManufacturer(manufacturer)
				.build();
		
		return computer;
	}
	
	public void addComputer(ComputerDTO computerDTO) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		Computer newComputer = convertDTOtoComputer(computerDTO);
		computerDriver.addComputer(newComputer);
	}
	
	public List<ComputerDTO> getAllDTOs(List<Optional<Computer>> restrictedList){
		
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		for (Optional<Computer> c : restrictedList) {
			result.add(convertComputerToDTO(c.get()));
		}
		return result;
	}
	
	public void updateComputer(ComputerDTO computerDTO) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		int computerId = computerDTO.getId();
		String computerName = computerDTO.getName();
		String introducedDateStr = computerDTO.getIntroducedDate();
		String discontinuedDateStr = computerDTO.getDiscontinuedDate();
		Manufacturer manufacturer = computerDTO.getManufacturerId() != 0 ? new Manufacturer(computerDTO.getManufacturerId(), computerDTO.getManufacturerName()) : null;
		Integer companyId = computerDTO.getManufacturerId();
		
		computerDriver.updateComputer(computerId, computerName, dh.convertStringDateToSqlDate(introducedDateStr), dh.convertStringDateToSqlDate(discontinuedDateStr), manufacturer);
	}
	
	public void deleteComputer(int computerId) throws NoComputerFoundException {
		
		computerDriver.removeComputer(computerId);
		
	}
	
	public void sortComputers(List<ComputerDTO> computers, String sorted) {
		
		if (sorted != null && !sorted.equals("")) {
			
			switch(generateEnumFromString(sorted)) {
			
				case BY_NAME:
					pagination.sortByName(computers);
					pagination.setSorted(CasesSorted.BY_NAME.getSortType());
					break;
					
				case BY_COMPANY:
					pagination.sortByCompany(computers);
					pagination.setSorted(CasesSorted.BY_COMPANY.getSortType());
					break;
					
				case BY_INTRODUCED:
					pagination.sortByIntroduced(computers);
					pagination.setSorted(CasesSorted.BY_INTRODUCED.getSortType());
					break;
					
				case BY_DISCONTINUED:
					pagination.sortByDiscontinued(computers);
					pagination.setSorted(CasesSorted.BY_DISCONTINUED.getSortType());
					break;
					
				case BY_NAME_REVERSE:
					pagination.sortByName(computers);
					Collections.reverse(computers);
					pagination.setSorted(CasesSorted.BY_NAME_REVERSE.getSortType());
					break;
					
				case BY_COMPANY_REVERSE:
					pagination.sortByCompany(computers);
					Collections.reverse(computers);
					pagination.setSorted(CasesSorted.BY_COMPANY_REVERSE.getSortType());
					break;
					
				case BY_INTRODUCED_REVERSE:
					pagination.sortByIntroduced(computers);
					Collections.reverse(computers);
					pagination.setSorted(CasesSorted.BY_INTRODUCED_REVERSE.getSortType());
					break;
					
				case BY_DISCONTINUED_REVERSE:
					pagination.sortByDiscontinued(computers);
					Collections.reverse(computers);
					pagination.setSorted(CasesSorted.BY_DISCONTINUED_REVERSE.getSortType());
					break;
			}
			
		}
		
	}
	
	public CasesSorted generateEnumFromString(String str) {
		
		for (CasesSorted casesSorted : CasesSorted.values()) {
			if (casesSorted.getSortType().equals(str)) {
				return casesSorted;
			}
		}
		
		return CasesSorted.BY_NAME;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public ComputerDriver getComputerDriver() {
		return computerDriver;
	}

	public void setComputerDriver(ComputerDriver computerDriver) {
		this.computerDriver = computerDriver;
	}

	public DatesHandler getDh() {
		return dh;
	}

	public void setDh(DatesHandler dh) {
		this.dh = dh;
	}
	
	
}
