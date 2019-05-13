package com.emaunzpa.controller;

import java.io.IOException;
import java.security.Principal;
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
import com.emaunzpa.model.User;
import com.emaunzpa.service.ComputerService;
import com.emaunzpa.service.ManufacturerService;
import com.emaunzpa.service.UserService;
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
	private static final String USER = "user";
	private static Logger log = Logger.getLogger(WebController.class);
	private ComputerService computerService;
	private ManufacturerService manufacturerService;
	private ComputerSpringValidator computerSpringValidator;
	private UserService userService;
	
	public WebController(ComputerService computerService, ManufacturerService manufacturerService, UserService userService, ComputerSpringValidator computerSpringValidator) {
		this.computerService = computerService;
		this.manufacturerService = manufacturerService;
		this.computerSpringValidator = computerSpringValidator;		
		this.userService = userService;
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
	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@GetMapping({"/", "/listComputers"})
	public String listComputers(HttpServletRequest request, Principal principal) {
		
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		computers = computerService.getAllComputers(request.getParameter("search"));
		computerService.initializePagination(request.getParameter("startIndex"), request.getParameter("endIndex"), computers);
		computerService.sortComputers(computers, request.getParameter("sorted"));
		
		request.setAttribute(ATT_SEARCH, request.getParameter("search"));
		request.setAttribute(ATT_SORTED, request.getParameter("sorted"));
		request.setAttribute(USER, principal.getName());
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
			} catch (NoComputerFoundException e) {
				log.error("Calling computerService method 'deleteComputer()' generated an exception : "
						+ e.getMessage());
				request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
				return new RedirectView(get500(request));
			}
		}
		
		return new RedirectView("listComputers");
	}
	
	@GetMapping("/addComputer")
	public String addComputerGet(HttpServletRequest request, Principal principal) {
		
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		manufacturers = manufacturerService.getAllManufacturers();
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		request.setAttribute(USER, principal.getName());
		
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
		}
		
		return new RedirectView("listComputers");
	}
	
	@GetMapping("/addUser")
	public String addUserGet(HttpServletRequest request, Principal principal) {
		
		request.setAttribute(USER, principal.getName());
		
		return "addUser";
	}
	
	@PostMapping("/addUser")
	public RedirectView addUserPost(HttpServletRequest request) {
		
		String username = request.getParameter("userName");
		String userPassword = request.getParameter("password");
		String role = request.getParameter("role");
		
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(userPassword);
		newUser.setEnabled(true);
		
		userService.addUser(newUser, role);
		
		return new RedirectView("listComputers");
	}
	
	@GetMapping("/editComputer")
	public String editComputerGet(HttpServletRequest request, Principal principal) {
		
		int computerID = Integer.valueOf(request.getParameter("computerID"));

		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		manufacturers = manufacturerService.getAllManufacturers();

		ComputerDTO computer = null;
		try {
			computer = computerService.getComputer(computerID);
		} catch (NoComputerFoundException e) {
			log.error("Calling computerService method 'getComputer()' generated an exception : "
					+ e.getMessage());
			request.setAttribute(ATT_ERROR_MESSAGE, e.getMessage());
			return get500(request);
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		request.setAttribute(ATT_COMPUTER, computer);
		request.setAttribute(USER, principal.getName());
		
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
	
	@GetMapping("/403")
	public String get403(HttpServletRequest request) {
		return "403";
	}
	
}
