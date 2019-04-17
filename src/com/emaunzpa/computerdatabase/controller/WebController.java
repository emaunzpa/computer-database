package com.emaunzpa.computerdatabase.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.emaunzpa.computerdatabase.DTO.ComputerDTO;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.service.ComputerService;
import com.emaunzpa.computerdatabase.service.ManufacturerService;
import com.emaunzpa.computerdatabase.validator.ComputerSpringValidator;

@Controller
public class WebController {
	
	public static final String ATT_LIST_COMPUTERS = "computers";
	public static final String ATT_PAGINATION = "pagination";
	public static final String ATT_SORTED = "sorted";
	public static final String ATT_SEARCH = "search";
	private static final String ATT_COMPUTER = "computer";
	public static final String ATT_LIST_MANUFACTURERS = "manufacturers";
	private static Logger log = Logger.getLogger(WebController.class);
	private ComputerService computerService;
	private ManufacturerService manufacturerService;
	private ComputerSpringValidator computerSpringValidator;
	
	public WebController(ComputerService computerService, ManufacturerService manufacturerService, ComputerSpringValidator computerSpringValidator) {
		this.computerService = computerService;
		this.manufacturerService = manufacturerService;
		this.computerSpringValidator = computerSpringValidator;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(computerSpringValidator);
	}
	
	@ModelAttribute
	public ComputerDTO initComputerDTO() {
		return new ComputerDTO.ComputerDTOBuilder()
				.build();
	}
	
	@GetMapping({"/", "/listComputers"})
	public String listComputers(HttpServletRequest request) {
		
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		try {
			computers = computerService.getAllComputers(request);
		} catch (SQLException | IOException e) {
			log.error("Calling computerService method 'getAllComputers()' generated an exception : "
					+ e.getMessage());
		}
		computerService.initializePagination(request, computers);
		computerService.sortComputers(computers, request);
		
		request.setAttribute(ATT_SEARCH, request.getParameter("search"));
		request.setAttribute(ATT_SORTED, request.getParameter("sorted"));
		request.setAttribute(ATT_LIST_COMPUTERS, computers);
		request.setAttribute(ATT_PAGINATION, computerService.getPagination());
		
		return "listComputers";
	}
	
	@PostMapping({"/", "/listComputers"})
	public RedirectView deleteComputer(HttpServletRequest request) {
		
		String[] checkboxValues = request.getParameterValues("cb");
		List<String> idToDelete = Arrays.asList(checkboxValues);
		
		for (String idStr : idToDelete) {
			Integer id = Integer.valueOf(idStr);
			try {
				computerService.deleteComputer(id);
			} catch (NoComputerFoundException | SQLException | IOException e) {
				log.error("Calling computerService method 'deleteComputer()' generated an exception : "
						+ e.getMessage());
			}
		}
		
		return new RedirectView("listComputers");
	}
	
	@GetMapping("/addComputer")
	public String addComputerGet(HttpServletRequest request) {
		
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			manufacturers = manufacturerService.getAllManufacturers();
		} catch (SQLException | IOException e) {
			log.error("Calling manufacturerService method 'getAllManufacturers()' generated an exception : "
					+ e.getMessage());
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		
		return "addComputer";
	}
	
	@PostMapping("/addComputer")
	public RedirectView addComputerPost(@ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO, BindingResult result) {
		
		if (result.hasErrors()) {
	         return new RedirectView("addComputer");
	    }
		
		try {
			computerService.addComputer(computerDTO);
		} catch (ComputerWithoutNameException e) {
			log.error("Error while adding a computer without any name : "
					+ e.getMessage());
		} catch (IncoherenceBetweenDateException e) {
			log.error("Error while adding a computer with incoherence between dates : "
					+ e.getMessage());
		} catch (DiscontinuedBeforeIntroducedException e) {
			log.error("Error while adding a computer with discontinuedDate before introducedDate : "
					+ e.getMessage());
			e.printStackTrace();
		} catch (SQLException | IOException e) {
			log.error("Calling computerService method 'addComputer()' generated an exception : "
					+ e.getMessage());
		}
		
		return new RedirectView("listComputers");
	}
	
	@GetMapping("/editComputer")
	public String editComputerGet(HttpServletRequest request) {
		
		int computerID = Integer.valueOf(request.getParameter("computerID"));

		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			manufacturers = manufacturerService.getAllManufacturers();
		} catch (SQLException | IOException e) {
			log.error("Calling manufacturerService method 'getAllManufacturers()' generated an exception : "
					+ e.getMessage());
		}
		ComputerDTO computer = null;
		try {
			computer = computerService.getComputer(computerID);
		} catch (NoComputerFoundException | SQLException | IOException e) {
			log.error("Calling computerService method 'getComputer()' generated an exception : "
					+ e.getMessage());
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		request.setAttribute(ATT_COMPUTER, computer);
		
		return "editComputer";
	}
	
	@PostMapping("/editComputer")
	public RedirectView editComputerPost(@ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO, BindingResult result) {
		
		if (result.hasErrors()) {
	         return new RedirectView("editComputer");
	    }
		
		try {
			computerService.updateComputer(computerDTO);
		} catch (NoComputerFoundException e) {
			log.error("Error while updating unexisting computer : "
					+ e.getMessage());
		} catch (IncoherenceBetweenDateException e) {
			log.error("Error while updating computer with incoherence between dates : "
					+ e.getMessage());
		} catch (DiscontinuedBeforeIntroducedException e) {
			log.error("Error while updating computer with discontinuedDate before introducedDate : "
					+ e.getMessage());
		} catch (SQLException | IOException e) {
			log.error("Calling computerService method 'updateComputer()' generated an exception : "
					+ e.getMessage());
		}
		
		return new RedirectView("listComputers");
	}
	
}
