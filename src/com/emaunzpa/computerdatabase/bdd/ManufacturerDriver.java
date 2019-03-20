package com.emaunzpa.computerdatabase.bdd;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import com.emaunzpa.computerdatabase.DAO.ManufacturerDAO;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class ManufacturerDriver implements ManufacturerDAO{

	private Statement statement;
    private ResultSet resultat;
    private static Logger log = Logger.getLogger(ManufacturerDriver.class.getName());
    private static HTMLLayout htmlLayout = new HTMLLayout();
    private static String databaseName;
    
	public ManufacturerDriver(String databaseName) {

		this.databaseName = databaseName;
		RollingFileAppender rollingfileAppender = null;
		try {
			rollingfileAppender = new RollingFileAppender(htmlLayout, "logging/log4j/ManufacturerDriverLogger.html");
			log.addAppender(rollingfileAppender);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@Override
	public Manufacturer getManufacturer(int id) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
		connectionDriver.initializeConnection();
		Manufacturer manufacturer = new Manufacturer();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request = "select * from company where id = " + id;
	        resultat = statement.executeQuery( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        if(resultat.first()) {
	            int idManufacturer = resultat.getInt( "id" );
	            String nameManufacturer = resultat.getString( "name" );
	            manufacturer.setId(idManufacturer);
	            manufacturer.setName(nameManufacturer);
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
	        String request = "select * from company";
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
