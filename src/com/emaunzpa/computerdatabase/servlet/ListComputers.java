package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emaunzpa.computerdatabase.DTO.ComputerDTO;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.service.ComputerService;

public class ListComputers extends HttpServlet {

	public static final String VUE_LIST_COMPUTERS = "/views/listComputers.jsp";
	public static final String ATT_LIST_COMPUTERS = "computers";
	public static final String ATT_PAGINATION = "pagination";
	public static final String ATT_SEARCH = "search";
	public static final String REDIRECT_DASHBOARD = "listComputers";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		ComputerService computerService = new ComputerService();
		computerService.initializePagination(request);
		
		List<ComputerDTO> computers = computerService.getAllComputers(request);
		
		request.setAttribute(ATT_SEARCH, request.getParameter("search"));
		request.setAttribute(ATT_LIST_COMPUTERS, computers);
		request.setAttribute(ATT_PAGINATION, computerService.getPagination());
		
		this.getServletContext().getRequestDispatcher(VUE_LIST_COMPUTERS).forward(request, response);
		
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String[] checkboxValues = request.getParameterValues("cb");
		System.out.println(checkboxValues);
		List<String> idToDelete = Arrays.asList(checkboxValues);
		ComputerService computerService = new ComputerService();
		
		for (String idStr : idToDelete) {
			Integer id = Integer.valueOf(idStr);
			try {
				computerService.deleteComputer(id);
			} catch (NoComputerFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.sendRedirect(REDIRECT_DASHBOARD);
		
	}
}
