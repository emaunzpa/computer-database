package com.emaunzpa.computerdatabase.bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.emaunzpa.computerdatabase.DAO.ManufacturerDAO;
import com.emaunzpa.computerdatabase.exception.NoManufacturerFoundException;
import com.emaunzpa.computerdatabase.mapper.ManufacturerMapper;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.util.CompanyFormValidator;

public class ManufacturerDriver implements ManufacturerDAO{

	private static Logger log;
	private JdbcTemplate jdbcTemplate;
    private DriverManagerDataSource dataSource;
    private DataSourceTransactionManager txManager;
    private CompanyFormValidator companyFormValidator = new CompanyFormValidator();
    private static String _GET_COMPANY_ = "select id, name from company where id = ?";
    private static String _GET_ALL_COMPANIES = "select id, name from company";
    private static String _DELETE_COMPANY = "delete from company where id = ?";
    private static String _DELETE_COMPUTERS_BY_COMPANY_ID = "delete from computer where company_id = ?";
    
	public ManufacturerDriver() {

		log = Logger.getLogger(ManufacturerDriver.class);

	}
	
	@Override
	public Manufacturer getManufacturer(int id) throws FileNotFoundException, IOException, SQLException {
		
        String request =  _GET_COMPANY_;
		Manufacturer manufacturer = jdbcTemplate.queryForObject(
				request, new Object[]{id}, new ManufacturerMapper());
        log.info( "Requête -- " + request + " -- effectuée !" );

		return manufacturer;
	}

	@Override
	public ArrayList<Manufacturer> getAllManufacturers() throws FileNotFoundException, IOException, SQLException {
		
        String request = _GET_ALL_COMPANIES;
		ArrayList<Manufacturer> manufacturers = (ArrayList<Manufacturer>) jdbcTemplate.query(
				   request, new ManufacturerMapper());
        log.info( "Requête -- " + request + " -- effectuée !" );

		return manufacturers;
	}
	
	public boolean removeManufacturer(int id) throws FileNotFoundException, IOException, SQLException, NoManufacturerFoundException {
						
		Integer searchId = Integer.valueOf(id);
		
		if (!companyFormValidator.companyFound(getAllManufacturers(), searchId)) {
			log.error("Company not found !");
			return false;
		}
        String request =  _DELETE_COMPUTERS_BY_COMPANY_ID;
        jdbcTemplate.update( request, new Object[]{id} );
        request =  _DELETE_COMPANY;
        jdbcTemplate.update( request, new Object[]{id} );
        
		return true;
	}

	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
