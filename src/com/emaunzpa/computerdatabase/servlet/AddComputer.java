package com.emaunzpa.computerdatabase.servlet;


import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.service.ComputerService;
import com.emaunzpa.computerdatabase.service.ManufacturerService;

public class AddComputer extends HttpServlet {

	public static final String VUE_FORM_NEW_COMPUTER = "/views/addComputer.jsp";
	public static final String VUE_LIST_COMPUTERS = "listComputers";
	public String vueComputerDetails;
	public static final String ATT_LIST_MANUFACTURERS = "manufacturers";
	public static final String ATT_FORM = "form";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ManufacturerService manufacturerService = new ManufacturerService();
		ArrayList<Manufacturer> manufacturers = manufacturerService.getAllManufacturers();
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		
		this.getServletContext().getRequestDispatcher(VUE_FORM_NEW_COMPUTER).forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ComputerService computerService = new ComputerService();
		computerService.addComputer(request);
		
		response.sendRedirect(VUE_LIST_COMPUTERS);
	}
	
}
