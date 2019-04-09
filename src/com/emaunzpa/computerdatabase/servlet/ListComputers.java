package com.emaunzpa.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emaunzpa.computerdatabase.DTO.ComputerDTO;
import com.emaunzpa.computerdatabase.bdd.ComputerDriver;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.HelloWorld;
import com.emaunzpa.computerdatabase.service.ComputerService;

public class ListComputers extends HttpServlet {

	public static final String VUE_LIST_COMPUTERS = "/views/listComputers.jsp";
	public static final String ATT_LIST_COMPUTERS = "computers";
	public static final String ATT_PAGINATION = "pagination";
	public static final String ATT_SORTED = "sorted";
	public static final String ATT_SEARCH = "search";
	public static final String REDIRECT_DASHBOARD = "listComputers";
	public static final ApplicationContext CONTEXT = new ClassPathXmlApplicationContext("Beans.xml");
	private static Logger log = Logger.getLogger(ListComputers.class);
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		ComputerService computerService = (ComputerService) CONTEXT.getBean("computerService");
		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		try {
			computers = computerService.getAllComputers(request);
		} catch (SQLException e) {
			log.error("Calling computerService method 'getAllComputers()' generated an SQL exception : "
					+ e.getMessage());
		}
		computerService.initializePagination(request, computers);
		computerService.sortComputers(computers, request);
		
		request.setAttribute(ATT_SEARCH, request.getParameter("search"));
		request.setAttribute(ATT_SORTED, request.getParameter("sorted"));
		request.setAttribute(ATT_LIST_COMPUTERS, computers);
		request.setAttribute(ATT_PAGINATION, computerService.getPagination());
		
		this.getServletContext().getRequestDispatcher(VUE_LIST_COMPUTERS).forward(request, response);
		
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String[] checkboxValues = request.getParameterValues("cb");
		List<String> idToDelete = Arrays.asList(checkboxValues);
		ComputerService computerService = (ComputerService) CONTEXT.getBean("computerService");
		
		for (String idStr : idToDelete) {
			Integer id = Integer.valueOf(idStr);
			try {
				computerService.deleteComputer(id);
			} catch (NoComputerFoundException | SQLException e) {
				log.error("Calling computerService method 'deleteComputer()' generated an exception : "
						+ e.getMessage());
			}
		}
		
		response.sendRedirect(REDIRECT_DASHBOARD);
		
	}
}
