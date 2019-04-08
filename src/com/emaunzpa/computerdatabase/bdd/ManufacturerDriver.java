package com.emaunzpa.computerdatabase.bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.emaunzpa.computerdatabase.DAO.ManufacturerDAO;
import com.emaunzpa.computerdatabase.exception.NoManufacturerFoundException;
import com.emaunzpa.computerdatabase.model.Manufacturer;
import com.emaunzpa.computerdatabase.util.CompanyFormValidator;

public class ManufacturerDriver implements ManufacturerDAO{

	private Statement statement;
    private ResultSet resultat;
    private Integer statut;
    private static Logger log;
    private CompanyFormValidator companyFormValidator;
    private static String databaseName;
    private static String _GET_COMPANY_ = "select id, name from company where id = ";
    private static String _GET_ALL_COMPANIES = "select id, name from company";
    private static String _DELETE_COMPANY = "delete from company where id = ";
    private static String _DELETE_COMPUTERS_BY_COMPANY_ID = "delete from computer where company_id = ";
    
	public ManufacturerDriver(String databaseName) {

		ManufacturerDriver.databaseName = databaseName;
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(prop);
		log = Logger.getLogger(ConnectionDriver.class.getName());
    	companyFormValidator = new CompanyFormValidator();
		
	}
	
	@Override
	public Optional<Manufacturer> getManufacturer(int id) throws FileNotFoundException, IOException, SQLException {
		
		HikariConnection hikariConnection = new HikariConnection(databaseName);
		Optional<Manufacturer> manufacturer = Optional.of(new Manufacturer());
		
		try {
			statement = hikariConnection.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request =  _GET_COMPANY_ + id;
	        resultat = statement.executeQuery( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        if(resultat.first()) {
	            int idManufacturer = resultat.getInt( "id" );
	            String nameManufacturer = resultat.getString( "name" );
	            manufacturer.get().setId(idManufacturer);
	            manufacturer.get().setName(nameManufacturer);
	        }
	    } catch ( SQLException e ) {
	        log.error( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	log.info( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	       	        
	    }
		
		hikariConnection.finalizeConnection();
		log.info("Fin de connexion.");
		return manufacturer;
	}

	@Override
	public ArrayList<Manufacturer> getAllManufacturers() throws FileNotFoundException, IOException, SQLException {
		
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		HikariConnection hikariConnection = new HikariConnection(databaseName);
		
		try {
	        statement = hikariConnection.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request = _GET_ALL_COMPANIES;
	        resultat = statement.executeQuery( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        while ( resultat.next() ) {
	            int idManufacturer = resultat.getInt( "id" );
	            String nameManufacturer = resultat.getString( "name" );
	            manufacturers.add(new Manufacturer(idManufacturer, nameManufacturer));
	        }
	    } catch ( SQLException e ) {
	        log.error( "Erreur lors de la connexion : <br/>"
	                + e.getMessage() );
	    } finally {
	    	log.info( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	       	        
	    }
		
		hikariConnection.finalizeConnection();
		log.info("Fin de connexion.");
		return manufacturers;
	}
	
	public boolean removeManufacturer(int id) throws FileNotFoundException, IOException, SQLException, NoManufacturerFoundException {
		
		boolean result = false;
		
		HikariConnection hikariConnection = new HikariConnection(databaseName);
		
		Integer searchId = Integer.valueOf(id);
		if (!companyFormValidator.companyFound(getAllManufacturers(), searchId)) {
			System.out.println("Company not found !!!!");
			return false;
		}
		
		try {
			hikariConnection.getConnection().setAutoCommit(false);
			statement = hikariConnection.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request =  _DELETE_COMPUTERS_BY_COMPANY_ID + id;
	        statut = statement.executeUpdate( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        System.out.println("Computers deleted !!");
	        		
			statement = hikariConnection.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        request =  _DELETE_COMPANY + id;
	        statut = statement.executeUpdate( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        System.out.println("Company deleted !!");
			
	        hikariConnection.getConnection().commit();
	        result = true;
	    } catch ( SQLException e ) {
	        log.error( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	log.info( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	        log.info( "Réinitialisation du Statut." );
	        if ( statut != null ) {
	            statut = null;
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	       	        
	    }
		
		hikariConnection.finalizeConnection();
		return result;
	}
	
}
