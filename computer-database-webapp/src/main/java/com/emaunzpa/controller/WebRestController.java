package com.emaunzpa.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.emaunzpa.dto.ComputerDTO;
import com.emaunzpa.exception.ComputerWithoutNameException;
import com.emaunzpa.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.exception.IncoherenceBetweenDateException;
import com.emaunzpa.exception.NoComputerFoundException;
import com.emaunzpa.exception.NoManufacturerFoundException;
import com.emaunzpa.model.Manufacturer;
import com.emaunzpa.model.User;
import com.emaunzpa.service.ComputerService;
import com.emaunzpa.service.ManufacturerService;
import com.emaunzpa.service.UserService;
import com.emaunzpa.util.ComputerSpringValidator;

@RestController
@RequestMapping(value= "/api", produces = "application/json")
public class WebRestController {
	
	public static final String ATT_LIST_COMPUTERS = "computers";
	public static final String ATT_PAGINATION = "pagination";
	public static final String ATT_SORTED = "sorted";
	public static final String ATT_SEARCH = "search";
	public static final String ATT_LIST_MANUFACTURERS = "manufacturers";
	
	private ComputerService computerService;
	private ManufacturerService manufacturerService;
	private ComputerSpringValidator computerSpringValidator;
	private UserService userService;
	
	public WebRestController(ComputerService computerService, ComputerSpringValidator computerSpringValidator, ManufacturerService manufacturerService, UserService userService) {
		this.computerService = computerService;
		this.manufacturerService = manufacturerService;
		this.computerSpringValidator = computerSpringValidator;		
		this.userService = userService;
	}
	
	@RequestMapping({"/", "/computer"})
	public List<ComputerDTO> listComputers(@RequestParam(value= "search", defaultValue= "") String search,
			@RequestParam(value= "startIndex", defaultValue= "0") String startIndex,
			@RequestParam(value= "endIndex", defaultValue= "10") String endIndex,
			@RequestParam(value= "sorted", defaultValue= "") String sorted) throws IOException, SQLException {
		
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		
		computers = computerService.getAllComputers(search);
		
		computerService.initializePagination(startIndex, endIndex, computers);
		computerService.sortComputers(computers, sorted);
		
		return computers;
	}
	
	@RequestMapping(value="/computer/add", method=RequestMethod.POST, produces="application/json")
	public ComputerDTO addComputer(@RequestBody ComputerDTO computerDTO) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		try {
			computerService.addComputer(computerDTO);
		} catch (ComputerWithoutNameException e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (IncoherenceBetweenDateException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (DiscontinuedBeforeIntroducedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		
		return computerDTO;
	}
	
	@RequestMapping(value="/computer/delete/{id}", produces = "text/plain")
	public String deleteComputer(@PathVariable int id) throws NoComputerFoundException, IOException, SQLException {
		
		try {
			computerService.deleteComputer(id);
		} catch (NoComputerFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		
		return "Computer was well deleted from database !";
	}
	
	@RequestMapping("/computer/get/{id}")
	public ComputerDTO getComputer(@PathVariable("id") int id) throws NoComputerFoundException, SQLException, IOException {
		
		ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder().build();
		
		try {
			computerDTO = computerService.getComputer(id);
		} catch (NoComputerFoundException e) {
			System.out.println(e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		
		return computerDTO;
	}
	
	@RequestMapping(value="/computer/edit/{id}", method=RequestMethod.POST)
	public ComputerDTO editComputer(@PathVariable("id") int id, @RequestBody ComputerDTO computerDTO) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException {
		
		try {
			computerService.updateComputer(computerDTO);
		} catch (NoComputerFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (IncoherenceBetweenDateException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		} catch (DiscontinuedBeforeIntroducedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		
		return computerDTO;
	}
	
	@RequestMapping("/manufacturer")
	public List<Manufacturer> getAllManufacturers() {
		
		List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		
		manufacturers = manufacturerService.getAllManufacturers();
		
		return manufacturers;
	}
	
	@RequestMapping("/manufacturer/get/{id}")
	public Manufacturer getManufacturer(@PathVariable("id") int id) {
		
		Manufacturer manufacturer = new Manufacturer();
		
		try {
			manufacturer = getManufacturerService().getManufacturerDriver().getManufacturer(id);
		} catch (NoManufacturerFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		
		return manufacturer;
	}
	
	@RequestMapping(value="/manufacturer/delete/{id}", produces="text/plain")
	public String deleteManufacturer(@PathVariable int id) throws NoManufacturerFoundException {
		
		try {
			manufacturerService.getManufacturerDriver().removeManufacturer(id);
		} catch (NoManufacturerFoundException e) {
			new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
		
		return "Manufacturer was well deleted from database !";
	}
	
	@RequestMapping("/404")
	public String get404() {	
		return "404";
	}
	
	@RequestMapping("/500")
	public String get500() {
		return "500";
	}
	
	@RequestMapping("/403")
	public String get403() {
		return "403";
	}

	public ComputerService getComputerService() {
		return computerService;
	}

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

	public ManufacturerService getManufacturerService() {
		return manufacturerService;
	}

	public void setManufacturerService(ManufacturerService manufacturerService) {
		this.manufacturerService = manufacturerService;
	}

	public ComputerSpringValidator getComputerSpringValidator() {
		return computerSpringValidator;
	}

	public void setComputerSpringValidator(ComputerSpringValidator computerSpringValidator) {
		this.computerSpringValidator = computerSpringValidator;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
    
}
