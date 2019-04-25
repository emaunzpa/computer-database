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
import com.emaunzpa.computerdatabase.DAO.ComputerDAO;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.*;
import com.emaunzpa.computerdatabase.util.ComputerFormValidator;

public class ComputerDriver implements ComputerDAO {
	
    private ComputerFormValidator computerFormValidator = new ComputerFormValidator();
	private ManufacturerDriver manufacturerDriver;
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction = null;
	private static Logger log;
	
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
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
		}
		
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
	            computers.add(computer);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
		}
		
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

		return true;
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
