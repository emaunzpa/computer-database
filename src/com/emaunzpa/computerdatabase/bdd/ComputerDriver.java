package com.emaunzpa.computerdatabase.bdd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.emaunzpa.computerdatabase.DAO.ComputerDAO;
import com.emaunzpa.computerdatabase.model.*;

public class ComputerDriver implements ComputerDAO {
	
	private Statement statement;
    private ResultSet resultat;
    private PreparedStatement prepareStatement;
    private Integer statut;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
    /**
     * Empty creator without params
     */
	public ComputerDriver() {

	}

	@Override
	public Computer getComputer(int id) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		Computer computer = new Computer.ComputerBuilder().build();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "select * from computer where id = " + id;
	        resultat = statement.executeQuery( request );
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
	        if(resultat.first()) {
	        	int idComputer = resultat.getInt( "id" );
	        	String nameComputer = resultat.getString( "name" );
	            String introducedStr = resultat.getString( "introduced" );
	            String discontinuedStr = resultat.getString( "discontinued" );
	            java.sql.Date introducedDate = null;
	            java.sql.Date discontinuedDate = null;
	            if (introducedStr != null) {
	            	java.util.Date introducedUtilDate = sdf.parse(introducedStr);
	            	introducedDate = new java.sql.Date(introducedUtilDate.getTime());
	            }
	            if (discontinuedStr != null) {
	            	java.util.Date discontinuedUtilDate = sdf.parse(discontinuedStr);
	            	discontinuedDate = new java.sql.Date(discontinuedUtilDate.getTime());
	            }
	            Integer manufacturerId = resultat.getInt( "company_id" );
	            computer.setId(idComputer);
		        computer.setName(nameComputer);
		        computer.setIntroducedDate(introducedDate);
		        computer.setDiscontinuedDate(discontinuedDate);
		        computer.setmanufacturerId(manufacturerId);
	        }
	    } catch ( SQLException | ParseException e ) {
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
		return computer; 
	}

	@Override
	public boolean addComputer(Computer computer) {
		
		// Impossible to create a computer without a name
		if(computer.getName() == null) {
			return false;
		}
		
		boolean result = false;
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
			String request = "insert into computer (name, introduced, discontinued, company_id) values (?,?,?,?)";
	        System.out.println( "Objet requête créé !" );
			prepareStatement = connectionDriver.getConnection().prepareStatement( request );
	        prepareStatement.setString(1, computer.getName());
	        prepareStatement.setDate(2, computer.getIntroducedDate());
	        prepareStatement.setDate(3, computer.getDiscontinuedDate());
	        System.out.println("Manu ID --> " + computer.getmanufacturerId());
	        prepareStatement.setObject(4, computer.getmanufacturerId());
	        prepareStatement.executeUpdate();
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
	        result = true;
	    } catch ( SQLException e ) {
	        System.out.println( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	System.out.println( "Réinitialisation du statut" );
	        if ( statut != null ) {
	            statut = null;
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
		return result;
	}

	@Override
	public ArrayList<Computer> getAllComputers() {
		
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "select * from computer";
	        resultat = statement.executeQuery( request );
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
	        while ( resultat.next() ) {
	            int idComputer = resultat.getInt( "id" );
	            String nameComputer = resultat.getString( "name" );
	            String introducedStr = resultat.getString( "introduced" );
	            String discontinuedStr = resultat.getString( "discontinued" );
	            java.sql.Date introducedDate = null;
	            java.sql.Date discontinuedDate = null;
	            if (introducedStr != null) {
	            	java.util.Date introducedUtilDate = sdf.parse(introducedStr);
	            	introducedDate = new java.sql.Date(introducedUtilDate.getTime());
	            }
	            if (discontinuedStr != null) {
	            	java.util.Date discontinuedUtilDate = sdf.parse(discontinuedStr);
	            	discontinuedDate = new java.sql.Date(discontinuedUtilDate.getTime());
	            }
	            Integer idCompany = resultat.getInt( "company_id" );
	            computers.add(new Computer.ComputerBuilder().withId(idComputer).withName(nameComputer).withIntroducedDate(introducedDate).withDiscontinuedDate(discontinuedDate).withManufacturerId(idCompany).build());
	        }
	    } catch ( SQLException | ParseException e ) {
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
		return computers;
	}

	@Override
	public boolean removeComputer(int id) {
		
		boolean result = false;
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "delete from computer where id = " + id;
	        statut = statement.executeUpdate( request );
	        System.out.println( "Requête -- delete from computer where id = " + id + " -- effectuée !" );
	        result = true;
	    } catch ( SQLException e ) {
	        System.out.println( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	System.out.println( "Réinitialisation du statut" );
	        if ( statut != null ) {
	           statut = null;
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
		return result;
	}

	@Override
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId) {
		
		boolean result = false;
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		// Cannot update a unexisting computer
		if (getComputer(id).getName() == null) {
			return false;
		}
		
		try {
	        String request = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
	        System.out.println( "Objet requête créé !" );
	        prepareStatement = connectionDriver.getConnection().prepareStatement( request );
	        prepareStatement.setString(1, newName);
	        prepareStatement.setDate(2, newIntroduced);
	        prepareStatement.setDate(3, newDiscontinued);
	        prepareStatement.setObject(4, newManufacturerId);
	        prepareStatement.setInt(5, id); 
	        prepareStatement.executeUpdate();
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
	        result = true;
	    } catch ( SQLException e ) {
	        System.out.println( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	System.out.println( "Réinitialisation du statut" );
	        if ( statut != null ) {
	            statut = null;
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
		return result;
	}
	
}
