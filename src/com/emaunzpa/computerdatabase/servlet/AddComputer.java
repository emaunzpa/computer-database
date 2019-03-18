package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.bdd.ManufacturerDriver;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.util.DatesHandler;

public class AddComputer extends HttpServlet {

	public static final String VUE_FORM_NEW_COMPUTER = "/views/addComputer.jsp";
	public static final String ATT_LIST_MANUFACTURERS = "manufacturers";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		ManufacturerDriver manufacturerDriver = new ManufacturerDriver();
		ArrayList<Manufacturer> manufacturers = manufacturerDriver.getAllManufacturers();
		
		request.setAttribute(ATT_LIST_MANUFACTURERS, manufacturers);
		
		this.getServletContext().getRequestDispatcher(VUE_FORM_NEW_COMPUTER).forward(request, response);
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response ) {
		
		String computerName = request.getParameter("computerName");
		String introducedDateStr = request.getParameter("introducedDate");
		String discontinuedDateStr = request.getParameter("discontinuedDate");
		DatesHandler dh = new DatesHandler();
		java.sql.Date introducedDate = dh.convertStringDateToSqlDate(introducedDateStr);
		java.sql.Date discontinuedDate = dh.convertStringDateToSqlDate(discontinuedDateStr);
		Integer companyId = Integer.valueOf(request.getParameter("companyId"));
		ComputerDriver computerDriver = new ComputerDriver();
		Computer newComputer = new Computer.ComputerBuilder().withName(computerName).withIntroducedDate(introducedDate).withDiscontinuedDate(discontinuedDate).withManufacturerId(companyId).build();
		computerDriver.addComputer(newComputer);
		
	}
	
}
