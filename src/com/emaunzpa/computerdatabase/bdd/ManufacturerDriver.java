package com.emaunzpa.computerdatabase.bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.emaunzpa.computerdatabase.DAO.ManufacturerDAO;
import com.emaunzpa.computerdatabase.exception.NoManufacturerFoundException;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.util.CompanyFormValidator;

public class ManufacturerDriver implements ManufacturerDAO{

	private static Logger log;
    private CompanyFormValidator companyFormValidator = new CompanyFormValidator();
    private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction = null;
	
    private static String _DELETE_COMPANY = "delete from Manufacturer where id = :id";
    private static String _DELETE_COMPUTERS_BY_COMPANY_ID = "delete from Computer where company_id = :cid";
    
	public ManufacturerDriver() {

		log = Logger.getLogger(ManufacturerDriver.class);

	}
	
	@Override
	public Manufacturer getManufacturer(int id) throws FileNotFoundException, IOException, SQLException {
		
		Manufacturer manufacturer = null;
		
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();
			manufacturer = session.get(Manufacturer.class, id);
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
		}

		return manufacturer;
	}

	@Override
	public ArrayList<Manufacturer> getAllManufacturers() throws FileNotFoundException, IOException, SQLException {
		
		ArrayList<Manufacturer> manufacturers = new ArrayList<>();
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();	
			List rsCompanies = session.createQuery("FROM Manufacturer").list();
			for (Iterator iterator = rsCompanies.iterator(); iterator.hasNext();){
	            Manufacturer manufacturer = (Manufacturer) iterator.next();
	            manufacturers.add(manufacturer);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			log.error(e.getMessage());
		} finally {
			session.close();
		}

		return manufacturers;
	}
	
	public boolean removeManufacturer(int id) throws FileNotFoundException, IOException, SQLException, NoManufacturerFoundException {
						
		Integer searchId = Integer.valueOf(id);
		
		if (!companyFormValidator.companyFound(getAllManufacturers(), searchId)) {
			log.error("Company not found !");
			return false;
		}
        
		session = sessionFactory.openSession();
		try {
			transaction = session.beginTransaction();	
			Query query = session.createQuery(_DELETE_COMPUTERS_BY_COMPANY_ID).setParameter("cid", id);
			query.executeUpdate();
			query = session.createQuery(_DELETE_COMPANY).setParameter("id", id);
			query.executeUpdate();
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
	
}
