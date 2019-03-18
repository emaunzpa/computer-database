package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.model.Computer;
import com.emaunzpa.computerdatabase.util.Pagination;

public class ListComputers extends HttpServlet {

	public static final String VUE_LIST_COMPUTERS = "/views/listComputers.jsp";
	public static final String ATT_LIST_COMPUTERS = "computers";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		ComputerDriver computerDriver = new ComputerDriver();
		Pagination pagination = new Pagination();
		ArrayList<Computer> computers = computerDriver.getAllComputers();
		List<Computer> restrictedListComputers = pagination.showRestrictedComputerList(computers);
		
		request.setAttribute(ATT_LIST_COMPUTERS, restrictedListComputers);
		
		this.getServletContext().getRequestDispatcher(VUE_LIST_COMPUTERS).forward(request, response);

    }
}
