package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.emaunzpa.computerdatabase.DTO.ComputerDTO;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.service.ComputerService;
import com.emaunzpa.computerdatabase.service.ManufacturerService;

public class EditComputer extends HttpServlet {
	
	private static final String VUE_EDIT_COMPUTER = "/views/editComputer.jsp";
	private static final String VUE_LIST_COMPUTERS = "listComputers";
	private static final String ATT_COMPUTER = "computer";
	private static final String ATT_LIST_MANUFACTURERS = "manufacturers";
	public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("Beans.xml");
	private static Logger log = Logger.getLogger(EditComputer.class);
	private ManufacturerService manufacturerService;
	private ComputerService computerService;
	private WebApplicationContext context;
	
	public void init() throws ServletException {
		super.init();
	    context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	    computerService = (ComputerService) context.getBean("computerService");
	    manufacturerService = (ManufacturerService) context.getBean("manufacturerService");
	}
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		int computerID = Integer.valueOf(request.getParameter("computerID"));

		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			manufacturers = manufacturerService.getAllManufacturers();
		} catch (SQLException e) {
			log.error("Calling manufacturerService method 'getAllManufacturers()' generated an exception : "
					+ e.getMessage());
		}
		ComputerDTO computer = null;
		try {
			computer = computerService.getComputer(computerID);
		} catch (NoComputerFoundException | SQLException e) {
			log.error("Calling computerService method 'getComputer()' generated an exception : "
					+ e.getMessage());
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		request.setAttribute(ATT_COMPUTER, computer);
		
		this.getServletContext().getRequestDispatcher(VUE_EDIT_COMPUTER).forward(request, response);
		
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			computerService.updateComputer(request);
		} catch (NoComputerFoundException e) {
			log.error("Error while updating unexisting computer : "
					+ e.getMessage());
		} catch (IncoherenceBetweenDateException e) {
			log.error("Error while updating computer with incoherence between dates : "
					+ e.getMessage());
		} catch (DiscontinuedBeforeIntroducedException e) {
			log.error("Error while updating computer with discontinuedDate before introducedDate : "
					+ e.getMessage());
		} catch (SQLException e) {
			log.error("Calling computerService method 'updateComputer()' generated an SQL exception : "
					+ e.getMessage());
		}
		
		response.sendRedirect(VUE_LIST_COMPUTERS);
		
	}

	public ManufacturerService getManufacturerService() {
		return manufacturerService;
	}

	public void setManufacturerService(ManufacturerService manufacturerService) {
		this.manufacturerService = manufacturerService;
	}

	public ComputerService getComputerService() {
		return computerService;
	}

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}
	
}
