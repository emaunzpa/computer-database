package com.emaunzpa.computerdatabase.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.emaunzpa.computerdatabase.DAO.ManufacturerDAO;
import com.emaunzpa.computerdatabase.model.Manufacturer;

public class ManufacturerDriver implements ManufacturerDAO{

	private Statement statement;
    private ResultSet resultat;
    
	public ManufacturerDriver() {

	}
	
	@Override
	public Manufacturer getManufacturer(int id) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		Manufacturer manufacturer = new Manufacturer();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "select * from company where id = " + id;
	        resultat = statement.executeQuery( request );
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
	        if(resultat.first()) {
	            int idManufacturer = resultat.getInt( "id" );
	            String nameManufacturer = resultat.getString( "name" );
	            manufacturer.setId(idManufacturer);
	            manufacturer.setName(nameManufacturer);
	        }
	    } catch ( SQLException e ) {
	        System.out.println( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	System.out.println( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	        System.out.println( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	       	        
	    }
		
		connectionDriver.finalizeConnection();
		return manufacturer;
	}

	@Override
	public ArrayList<Manufacturer> getAllManufacturers() {
		
		ArrayList<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "select * from company";
	        resultat = statement.executeQuery( request );
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
	        while ( resultat.next() ) {
	            int idManufacturer = resultat.getInt( "id" );
	            String nameManufacturer = resultat.getString( "name" );
	            manufacturers.add(new Manufacturer(idManufacturer, nameManufacturer));
	        }
	    } catch ( SQLException e ) {
	        System.out.println( "Erreur lors de la connexion : <br/>"
	                + e.getMessage() );
	    } finally {
	    	System.out.println( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	        System.out.println( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	       	        
	    }
		
		connectionDriver.finalizeConnection();
		return manufacturers;
	}
	
}
