package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		int computerID = Integer.valueOf(request.getParameter("computerID"));

		ManufacturerService manufacturerService = (ManufacturerService) CONTEXT.getBean("manufacturerService");
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			manufacturers = manufacturerService.getAllManufacturers();
		} catch (SQLException e) {
			log.error("Calling manufacturerService method 'getAllManufacturers()' generated an exception : "
					+ e.getMessage());
		}
		ComputerService computerService = (ComputerService) CONTEXT.getBean("computerService");
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
		
		ComputerService computerService = (ComputerService) CONTEXT.getBean("computerService");
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
}
