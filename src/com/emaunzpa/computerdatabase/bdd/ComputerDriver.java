package com.emaunzpa.computerdatabase.bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.emaunzpa.computerdatabase.DAO.ComputerDAO;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.mapper.ComputerMapper;
import com.emaunzpa.computerdatabase.mapper.MCMapper;
import com.emaunzpa.computerdatabase.model.*;
import com.emaunzpa.computerdatabase.util.ComputerFormValidator;

public class ComputerDriver implements ComputerDAO {
	
	private DriverManagerDataSource dataSource;
    private ComputerFormValidator computerFormValidator = new ComputerFormValidator();
	private JdbcTemplate jdbcTemplate;
    
	private static Logger log;
	private static String _ADD_COMPUTER_ = "insert into computer (name, introduced, discontinued, company_id) values (?,?,?,?)";
	private static String _GET_ALL_COMPUTERS_ = "select computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name from computer left join company on computer.company_id = company.id order by computer.id";
	private static String _GET_COMPUTER_ = "select id, name, introduced, discontinued, company_id from computer where id = ?";
	private static String _UPDATE_COMPUTER_ = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
	private static String _DELETE_COMPUTER_ = "delete from computer where id = ?";
	
    /**
     * Empty creator without params
     * @throws IOException 
     */
	public ComputerDriver() {
		log = Logger.getLogger(ComputerDriver.class);
	}

	@Override
	public Optional<Computer> getComputer(int id) throws NoComputerFoundException, SQLException, FileNotFoundException, IOException {
		
		Optional<Computer> computer = Optional.empty();		
		Integer searchId = Integer.valueOf(id);
		
		// Test if computer exists
		if(!computerFormValidator.computerFound(getAllComputers(), searchId)) {
			return computer;
		}
		
		log.info( "Objet requête créé !" );
		String request = _GET_COMPUTER_ ;
		computer = (Optional<Computer>) jdbcTemplate.queryForObject(
				request, new Object[]{id}, new ComputerMapper());
		log.info( "Requête -- " + request + " -- effectuée !" );
		
		dataSource.getConnection().close();
		log.info("Fin de la connexion");
		return computer;
	}

	@Override
	public boolean addComputer(Computer computer) throws ComputerWithoutNameException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, SQLException, FileNotFoundException, IOException {
		
		// Impossible to add a computer without a name
		if (!computerFormValidator.newComputerHasName(computer)) {
			return false;
		}
		
		// Discontinued date must be after introduced date
		if (!computerFormValidator.introducedBeforeDiscontinued(computer)) {
			return false;
		}
		
		String request = _ADD_COMPUTER_;
		jdbcTemplate.update(request, new Object[]{computer.getName(), computer.getIntroducedDate(), computer.getDiscontinuedDate(), computer.getmanufacturerId()});
		log.info( "Requête -- " + request + " -- effectuée !" );
		
		return true;
		
	}

	@Override
	public ArrayList<Optional<Computer>> getAllComputers() throws FileNotFoundException, IOException, SQLException {
		
		String request = _GET_ALL_COMPUTERS_;
		ArrayList<Optional<Computer>> computers = (ArrayList<Optional<Computer>>) jdbcTemplate.query(
				request, new MCMapper());
		log.info( "Requête -- " + request + " -- effectuée !" );
		return computers;
		
	}

	@Override
	public boolean removeComputer(int id) throws NoComputerFoundException, FileNotFoundException, IOException, SQLException {
				
		Integer searchId = Integer.valueOf(id);
		if (!computerFormValidator.computerFound(getAllComputers(), searchId)) {
			return false;
		}
		
        String request =  _DELETE_COMPUTER_ ;
		jdbcTemplate.update(request, new Object[]{id});
        log.info( "Requête -- "+ request + " -- effectuée !" );
        
		return true;
		
	}

	@Override
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, IOException, SQLException {
		
		boolean result = false;
		Optional<Computer> computer;
		
		// Cannot update a unexisting computer
		if (!getComputer(id).isPresent()) {
			log.error("Impossible to update a unexisting computer. Request cancelled.");
			return false;
		}
		else {
			computer = getComputer(id);
		}
		
		// Discontinued date must be after introduced date
		if (!computerFormValidator.introducedBeforeDiscontinued(computer.get())){
			return false;
		}
		
        String request = _UPDATE_COMPUTER_ ;
		jdbcTemplate.update(request, new Object[]{newName, newIntroduced, newDiscontinued, newManufacturerId, id});
        log.info( "Requête -- " + request + " -- effectuée !" );

		return result;
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
