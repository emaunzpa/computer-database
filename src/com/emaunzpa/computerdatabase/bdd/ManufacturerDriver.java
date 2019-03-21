package com.emaunzpa.computerdatabase.bdd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import com.emaunzpa.computerdatabase.DAO.ManufacturerDAO;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class ManufacturerDriver implements ManufacturerDAO{

	private Statement statement;
    private ResultSet resultat;
    private static Logger log;
    private static HTMLLayout htmlLayout = new HTMLLayout();
    private static String databaseName;
    private static String _GET_COMPANY_ = "select id, name from company where id = ";
    private static String _GET_ALL_COMPANIES = "select id, name from company";
    
	public ManufacturerDriver(String databaseName) {

		this.databaseName = databaseName;
    	log = Logger.getLogger(ConnectionDriver.class.getName());
		
	}
	
	@Override
	public Optional<Manufacturer> getManufacturer(int id) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
		connectionDriver.initializeConnection();
		Optional<Manufacturer> manufacturer = Optional.of(new Manufacturer());
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
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
		
		connectionDriver.finalizeConnection();
		log.info("Fin de connexion.");
		return manufacturer;
	}

	@Override
	public ArrayList<Manufacturer> getAllManufacturers() {
		
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
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
		
		connectionDriver.finalizeConnection();
		log.info("Fin de connexion.");
		return manufacturers;
	}
	
}
