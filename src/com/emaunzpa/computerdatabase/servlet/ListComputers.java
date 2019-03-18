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
	public static final String ATT_LIST_COMPUTERS_RESTRICTED = "printedComputers";
	public static final String ATT_PAGINATION = "pagination";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		/**
		 * We initiate pagination with parameters passed trough url.
		 * If params are null, the default params are set to 0 and 10 to print the 10 first computers
		 */
		Pagination pagination = new Pagination();
		if (request.getParameter("startIndex") != null) {
			pagination.setStartIndex(Integer.valueOf(request.getParameter("startIndex")));
		}
		if (request.getParameter("endIndex") != null) {
			pagination.setEndIndex(Integer.valueOf(request.getParameter("endIndex")));
		}

		ComputerDriver computerDriver = new ComputerDriver();
		ArrayList<Computer> computers = computerDriver.getAllComputers();
		List<Computer> restrictedListComputers = pagination.showRestrictedComputerList(computers);
		
		request.setAttribute(ATT_LIST_COMPUTERS, computers);
		request.setAttribute(ATT_LIST_COMPUTERS_RESTRICTED, restrictedListComputers);
		request.setAttribute(ATT_PAGINATION, pagination);
		
		this.getServletContext().getRequestDispatcher(VUE_LIST_COMPUTERS).forward(request, response);

    }
}
