package com.emaunzpa.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.emaunzpa.dto.ComputerDTO;
import com.emaunzpa.exception.ComputerWithoutNameException;
import com.emaunzpa.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.exception.IncoherenceBetweenDateException;
import com.emaunzpa.exception.NoComputerFoundException;
import com.emaunzpa.model.Manufacturer;
import com.emaunzpa.service.ComputerService;
import com.emaunzpa.service.ManufacturerService;
import com.emaunzpa.util.ComputerSpringValidator;

@Controller
public class WebController {
	
	public static final String ATT_LIST_COMPUTERS = "computers";
	public static final String ATT_PAGINATION = "pagination";
	public static final String ATT_SORTED = "sorted";
	public static final String ATT_SEARCH = "search";
	private static final String ATT_COMPUTER = "computer";
	private static final String ATT_ERROR_MESSAGE = "errorMessage";
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
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return get500(request);
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
				request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
				return new RedirectView(get500(request));
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
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return get500(request);
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		
		return "addComputer";
	}
	
	@PostMapping("/addComputer")
	public RedirectView addComputerPost(HttpServletRequest request, @ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO, BindingResult result) {
		
		if (result.hasErrors()) {
	         return new RedirectView("addComputer");
	    }
		
		try {
			computerService.addComputer(computerDTO);
		} catch (ComputerWithoutNameException e) {
			log.error("Error while adding a computer without any name : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
		} catch (IncoherenceBetweenDateException e) {
			log.error("Error while adding a computer with incoherence between dates : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
		} catch (DiscontinuedBeforeIntroducedException e) {
			log.error("Error while adding a computer with discontinuedDate before introducedDate : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
		} catch (SQLException | IOException e) {
			log.error("Calling computerService method 'addComputer()' generated an exception : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
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
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return get500(request);
		}
		ComputerDTO computer = null;
		try {
			computer = computerService.getComputer(computerID);
		} catch (NoComputerFoundException | SQLException | IOException e) {
			log.error("Calling computerService method 'getComputer()' generated an exception : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return get500(request);
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		request.setAttribute(ATT_COMPUTER, computer);
		
		return "editComputer";
	}
	
	@PostMapping("/editComputer")
	public RedirectView editComputerPost(HttpServletRequest request, @ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO, BindingResult result) {
		
		if (result.hasErrors()) {
	         return new RedirectView("editComputer?computerID=" + request.getParameter("id"));
	    }
		
		try {
			computerService.updateComputer(computerDTO);
		} catch (NoComputerFoundException e) {
			log.error("Error while updating unexisting computer : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
		} catch (IncoherenceBetweenDateException e) {
			log.error("Error while updating computer with incoherence between dates : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
		} catch (DiscontinuedBeforeIntroducedException e) {
			log.error("Error while updating computer with discontinuedDate before introducedDate : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
		} catch (SQLException | IOException e) {
			log.error("Calling computerService method 'updateComputer()' generated an exception : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return new RedirectView(get500(request));
		}
		
		return new RedirectView("listComputers");
	}
	
	@GetMapping("/404")
	public String get404(HttpServletRequest request) {	
		return "404";
	}
	
	@GetMapping("/500")
	public String get500(HttpServletRequest request) {
		return "500";
	}
	
}
