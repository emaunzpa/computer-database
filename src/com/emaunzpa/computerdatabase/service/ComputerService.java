package com.emaunzpa.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;
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
import com.emaunzpa.computerdatabase.util.DatesHandler;
import com.emaunzpa.computerdatabase.util.Pagination;

public class ComputerService {
	
	private Pagination pagination;
	private ComputerDriver computerDriver;
	DatesHandler dh = new DatesHandler();

	public ComputerService() {
		pagination = new Pagination();
		computerDriver = new ComputerDriver("computer-database-db");
	}
	
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
	
	public List<ComputerDTO> getAllComputers(HttpServletRequest request){
		ArrayList<Computer> computers = new ArrayList<>();
		String searchStr = request.getParameter("search");
		if (searchStr != null && !searchStr.equals("")){
			for (Computer computerTested : computerDriver.getAllComputers()) {
				Pattern pattern = Pattern.compile(".*" + searchStr.toLowerCase() + ".*");
				Matcher macherComputerName = pattern.matcher(computerTested.getName().toLowerCase());
				
				if (computerTested.getManufacturerName() != null && !computerTested.getManufacturerName().equals("")) {
					Matcher macherCompanyName = pattern.matcher(computerTested.getManufacturerName().toLowerCase());
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
	
	public List<ComputerDTO> restrictedListComputers(){
		ArrayList<Computer> computers = computerDriver.getAllComputers();
		List<Computer> restrictedList = pagination.showRestrictedComputerList(computers);
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
	
	public ComputerDTO getComputer(int id) throws NoComputerFoundException {
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
	
	public void addComputer(HttpServletRequest request) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		String computerName = request.getParameter("computerName");
		String introducedDateStr = request.getParameter("introducedDate");
		String discontinuedDateStr = request.getParameter("discontinuedDate");
		int companyId = Integer.valueOf(request.getParameter("companyId"));
		
		ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder()
				.withName(computerName)
				.withIntroducedDate(introducedDateStr)
				.withDiscontinuedDate(discontinuedDateStr)
				.withManufacturerId(companyId)
				.build();
		
		Computer newComputer = convertDTOtoComputer(computerDTO);
		computerDriver.addComputer(newComputer);
	}
	
	public List<ComputerDTO> getAllDTOs(List<Computer> computers){
		
		List<ComputerDTO> result = new ArrayList<ComputerDTO>();
		for (Computer c : computers) {
			result.add(convertComputerToDTO(c));
		}
		return result;
	}
	
	public void updateComputer(HttpServletRequest request) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		int computerId = Integer.valueOf(request.getParameter("computerId"));
		String computerName = request.getParameter("computerName");
		String introducedDateStr = request.getParameter("introducedDate");
		String discontinuedDateStr = request.getParameter("discontinuedDate");
		Integer companyId = Integer.valueOf(request.getParameter("companyId"));
		
		computerDriver.updateComputer(computerId, computerName, dh.convertStringDateToSqlDate(introducedDateStr), dh.convertStringDateToSqlDate(discontinuedDateStr), companyId);
	}
	
	public void deleteComputer(int computerId) throws NoComputerFoundException {
		
		computerDriver.removeComputer(computerId);
		
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
