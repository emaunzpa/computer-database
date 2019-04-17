package com.emaunzpa.computerdatabase.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.DTO.ComputerDTO;
import com.emaunzpa.computerdatabase.util.CasesSorted;
import com.emaunzpa.computerdatabase.util.DatesHandler;
import com.emaunzpa.computerdatabase.util.Pagination;

public class ComputerService {
	
	private Pagination pagination;
	private ComputerDriver computerDriver;
	DatesHandler dh = new DatesHandler();

	public ComputerService() {}
	
	/**
	 * We initiate pagination with parameters passed trough url.
	 * If params are null, the default params are set to 0 and 10 to print the 10 first computers
	 */
	public void initializePagination(HttpServletRequest request, List<ComputerDTO> computers) {
		
		if (request.getParameter("startIndex") != null) {
			pagination.setStartIndex(Integer.valueOf(request.getParameter("startIndex")));
		}
		if (request.getParameter("endIndex") != null) {
			pagination.setEndIndex(Integer.valueOf(request.getParameter("endIndex")));
		}
		
		pagination.initializeIndexes(computers);
		
		
	}
	
	public List<ComputerDTO> getAllComputers(HttpServletRequest request) throws FileNotFoundException, IOException, SQLException{
		ArrayList<Optional<Computer>> computers = new ArrayList<>();
		String searchStr = request.getParameter("search");
		if (searchStr != null && !searchStr.equals("")){
			for (Optional<Computer> computerTested : computerDriver.getAllComputers()) {
				Pattern pattern = Pattern.compile(".*" + searchStr.toLowerCase() + ".*");
				Matcher macherComputerName = pattern.matcher(computerTested.get().getName().toLowerCase());
				
				if (computerTested.get().getManufacturerName() != null && !computerTested.get().getManufacturerName().equals("")) {
					Matcher macherCompanyName = pattern.matcher(computerTested.get().getManufacturerName().toLowerCase());
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
	
	public List<ComputerDTO> restrictedListComputers() throws FileNotFoundException, IOException, SQLException{
		ArrayList<Optional<Computer>> computers = computerDriver.getAllComputers();
		List<Optional<Computer>> restrictedList = pagination.showRestrictedComputerList(computers);
		return getAllDTOs(restrictedList);
	}
	
	public ComputerDTO convertComputerToDTO(Computer computer) {
		
		int companyId = 0;
		if (computer.getmanufacturerId() != null) {
			companyId = (int) computer.getmanufacturerId();
		}
		ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder()
				.withId(computer.getId())
				.withName(computer.getName())
				.withIntroducedDate(dh.convertSqlDateToString(computer.getIntroducedDate()))
				.withDiscontinuedDate(dh.convertSqlDateToString(computer.getDiscontinuedDate()))
				.withManufacturerId(companyId)
				.withManufacturerName(computer.getManufacturerName())
				.build();
		return computerDTO;
	}
	
	public ComputerDTO getComputer(int id) throws NoComputerFoundException, FileNotFoundException, SQLException, IOException {
		return convertComputerToDTO(computerDriver.getComputer(id).get());
	}
	
	public Computer convertDTOtoComputer(ComputerDTO computerDTO) {
		
		Integer companyId = null;
		if (computerDTO.getmanufacturerId() != 0) {
			companyId = (Integer) computerDTO.getmanufacturerId();
		}
		Computer computer = new Computer.ComputerBuilder()
				.withId(computerDTO.getId())
				.withName(computerDTO.getName())
				.withIntroducedDate(dh.convertStringDateToSqlDate(computerDTO.getIntroducedDate()))
				.withDiscontinuedDate(dh.convertStringDateToSqlDate(computerDTO.getDiscontinuedDate()))
				.withManufacturerId(companyId)
				.withManufacturerName(computerDTO.getManufacturerName())
				.build();
		
		return computer;
	}
	
	public void addComputer(ComputerDTO computerDTO) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, SQLException, IOException {
		
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
	
	public void updateComputer(ComputerDTO computerDTO) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, IOException, SQLException {
		
		int computerId = computerDTO.getId();
		String computerName = computerDTO.getName();
		String introducedDateStr = computerDTO.getIntroducedDate();
		String discontinuedDateStr = computerDTO.getDiscontinuedDate();
		Integer companyId = computerDTO.getmanufacturerId();
		if (companyId == 0) {
			companyId = null;
		}
		
		computerDriver.updateComputer(computerId, computerName, dh.convertStringDateToSqlDate(introducedDateStr), dh.convertStringDateToSqlDate(discontinuedDateStr), companyId);
	}
	
	public void deleteComputer(int computerId) throws NoComputerFoundException, FileNotFoundException, IOException, SQLException {
		
		computerDriver.removeComputer(computerId);
		
	}
	
	public void sortComputers(List<ComputerDTO> computers, HttpServletRequest request) {
		
		String sorted = request.getParameter("sorted");
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
