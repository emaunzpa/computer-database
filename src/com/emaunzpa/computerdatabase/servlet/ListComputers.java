package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.model.Computer;

public class ListComputers extends HttpServlet {

	public static final String VUE_LIST_COMPUTERS = "/views/listComputers.jsp";
	public static final String ATT_LIST_COMPUTERS = "computers";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		ComputerDriver computerDriver = new ComputerDriver();
		ArrayList<Computer> computers = computerDriver.getAllComputers();
		
		request.setAttribute(ATT_LIST_COMPUTERS, computers);
		
		this.getServletContext().getRequestDispatcher(VUE_LIST_COMPUTERS).forward(request, response);

    }
}
