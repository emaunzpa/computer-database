package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		int computerID = Integer.valueOf(request.getParameter("computerID"));

		ManufacturerService manufacturerService = new ManufacturerService();
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			manufacturers = manufacturerService.getAllManufacturers();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ComputerService computerService = new ComputerService();
		ComputerDTO computer = null;
		try {
			computer = computerService.getComputer(computerID);
		} catch (NoComputerFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		request.setAttribute(ATT_COMPUTER, computer);
		
		this.getServletContext().getRequestDispatcher(VUE_EDIT_COMPUTER).forward(request, response);
		
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ComputerService computerService = new ComputerService();
		try {
			computerService.updateComputer(request);
		} catch (NoComputerFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IncoherenceBetweenDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DiscontinuedBeforeIntroducedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect(VUE_LIST_COMPUTERS);
		
	}
}
