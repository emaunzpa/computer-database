package com.emaunzpa.computerdatabase.bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

public class ComputerDriver implements ComputerDAO {
	
	private DriverManagerDataSource dataSource;
    private ComputerFormValidator computerFormValidator = new ComputerFormValidator();
	private JdbcTemplate jdbcTemplate;
	private ManufacturerDriver manufacturerDriver;
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction = null;
    
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
			
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			computer = Optional.of(session.get(Computer.class, id));
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
		}
		
//		log.info( "Objet requête créé !" );
//		String request = _GET_COMPUTER_ ;
//		computer = (Optional<Computer>) jdbcTemplate.queryForObject(
//				request, new Object[]{id}, new ComputerMapper());
//		log.info( "Requête -- " + request + " -- effectuée !" );
//		
//		dataSource.getConnection().close();
//		log.info("Fin de la connexion");
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
		
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			session.save(computer);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
	    }		
		
//		String request = _ADD_COMPUTER_;
//		jdbcTemplate.update(request, new Object[]{computer.getName(), computer.getIntroducedDate(), computer.getDiscontinuedDate(), computer.getmanufacturerId()});
//		log.info( "Requête -- " + request + " -- effectuée !" );
		
		return true;
		
	}

	@Override
	public ArrayList<Optional<Computer>> getAllComputers() throws FileNotFoundException, IOException, SQLException {
		
		ArrayList<Optional<Computer>> computers = new ArrayList<>();
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();	
			List rsComputers = session.createQuery("FROM Computer").list();
			for (Iterator iterator = rsComputers.iterator(); iterator.hasNext();){
	            Optional<Computer> computer = Optional.of((Computer) iterator.next());
//	            int manufacturerId = computer.get().getmanufacturerId() != null ? computer.get().getmanufacturerId() : 0;
//	            Manufacturer manufacturer = manufacturerDriver.getManufacturer(manufacturerId);
//	            String manufacturerName = manufacturer != null ? manufacturer.getName() : "";
//	            computer.get().setManufacturerName(manufacturerName);
	            computers.add(computer);
			}
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
		}
		
//		String request = _GET_ALL_COMPUTERS_;
//		ArrayList<Optional<Computer>> computers = (ArrayList<Optional<Computer>>) jdbcTemplate.query(
//				request, new MCMapper());
//		log.info( "Requête -- " + request + " -- effectuée !" );
		
		return computers;
	}

	@Override
	public boolean removeComputer(int id) throws NoComputerFoundException, FileNotFoundException, IOException, SQLException {
				
		Integer searchId = Integer.valueOf(id);
		if (!computerFormValidator.computerFound(getAllComputers(), searchId)) {
			return false;
		}
		
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			Computer computer = session.get(Computer.class, id);
			session.delete(computer);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
	    }
		
//        String request =  _DELETE_COMPUTER_ ;
//		jdbcTemplate.update(request, new Object[]{id});
//        log.info( "Requête -- "+ request + " -- effectuée !" );
        
		return true;
		
	}

	@Override
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Manufacturer newManufacturer) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, IOException, SQLException {
		
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
		
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			computer.get().setIntroducedDate(newIntroduced);
			computer.get().setDiscontinuedDate(newDiscontinued);
			computer.get().setName(newName);
			computer.get().setManufacturer(newManufacturer);
			session.update(computer.get());
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
	    }
		
//        String request = _UPDATE_COMPUTER_ ;
//		jdbcTemplate.update(request, new Object[]{newName, newIntroduced, newDiscontinued, newManufacturerId, id});
//        log.info( "Requête -- " + request + " -- effectuée !" );

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

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ManufacturerDriver getManufacturerDriver() {
		return manufacturerDriver;
	}

	public void setManufacturerDriver(ManufacturerDriver manufacturerDriver) {
		this.manufacturerDriver = manufacturerDriver;
	}
	
}
