package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emaunzpa.computerdatabase.model.ComputerDTO;
import com.emaunzpa.computerdatabase.service.ComputerService;

public class ListComputers extends HttpServlet {

	public static final String VUE_LIST_COMPUTERS = "/views/listComputers.jsp";
	public static final String ATT_LIST_COMPUTERS = "computers";
	public static final String ATT_LIST_COMPUTERS_RESTRICTED = "printedComputers";
	public static final String ATT_PAGINATION = "pagination";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		ComputerService computerService = new ComputerService();
		computerService.initializePagination(request);
		
		List<ComputerDTO> computers = computerService.getAllComputers();
		List<ComputerDTO> restrictedListComputers = computerService.restrictedListComputers();
		
		request.setAttribute(ATT_LIST_COMPUTERS, computers);
		request.setAttribute(ATT_LIST_COMPUTERS_RESTRICTED, restrictedListComputers);
		request.setAttribute(ATT_PAGINATION, computerService.getPagination());
		
		this.getServletContext().getRequestDispatcher(VUE_LIST_COMPUTERS).forward(request, response);
		
    }
}
