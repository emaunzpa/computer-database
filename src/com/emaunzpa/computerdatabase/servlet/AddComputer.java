package com.emaunzpa.computerdatabase.servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.service.ComputerService;
import com.emaunzpa.computerdatabase.service.ManufacturerService;

public class AddComputer extends HttpServlet {

	public static final String VUE_FORM_NEW_COMPUTER = "/views/addComputer.jsp";
	public static final String VUE_LIST_COMPUTERS = "listComputers";
	public String vueComputerDetails;
	public static final String ATT_LIST_MANUFACTURERS = "manufacturers";
	public static final String ATT_FORM = "form";
	public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("Beans.xml");
	private static Logger log = Logger.getLogger(AddComputer.class);
	// TODO Change static access of services
	private static ManufacturerService manufacturerService;
	private static ComputerService computerService;
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			manufacturers = manufacturerService.getAllManufacturers();
		} catch (SQLException e) {
			log.error("Calling manufacturerService method 'getAllManufacturers()' generated an SQL exception : "
					+ e.getMessage());
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		
		this.getServletContext().getRequestDispatcher(VUE_FORM_NEW_COMPUTER).forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		try {
			computerService.addComputer(request);
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
		} catch (SQLException e) {
			log.error("Calling computerService method 'addComputer()' generated an SQL exception : "
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
