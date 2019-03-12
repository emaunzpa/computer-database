package com.emaunzpa.computerdatabase.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.emaunzpa.computerdatabase.DAO.ComputerDAO;
import com.emaunzpa.computerdatabase.model.Computer;

public class ComputerDriver implements ComputerDAO {
	
	private Statement statement;
    private ResultSet resultat;
    private Integer statut;
	
	public ComputerDriver() {

	}

	@Override
	public Computer getComputer(int id) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		Computer computer = new Computer();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "select * from computer where id = " + id;
	        resultat = statement.executeQuery( request );
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
	        if(resultat.first()) {
	        	int idComputer = resultat.getInt( "id" );
	        	String nameComputer = resultat.getString( "name" );
	            String introduced = resultat.getString( "introduced" );
	            String discontinued = resultat.getString( "discontinued" );
	            Integer manufacturerId = resultat.getInt( "company_id" );
	            computer.setId(idComputer);
		        computer.setName(nameComputer);
		        computer.setIntroducedDate(introduced);
		        computer.setDiscontinuedDate(discontinued);
		        computer.setmanufacturerId(manufacturerId);
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
		return computer; 
	}

	@Override
	public void addComputer(Computer computer) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "insert into computer (name, introduced, discontinued, company_id) values (" + computer.getName() + ", " + computer.getIntroducedDate() + ", " + computer.getDiscontinuedDate() + ", " + computer.getmanufacturerId() + ")";
	        statut = statement.executeUpdate( request );
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
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
	            String introduced = resultat.getString( "introduced" );
	            String discontinued = resultat.getString( "discontinued" );
	            Integer idCompany = resultat.getInt( "company_id" );
	            computers.add(new Computer(idComputer, nameComputer, introduced, discontinued, idCompany));
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
		return computers;
	}

	@Override
	public void removeComputer(int id) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "delete from computer where id = " + id;
	        statut = statement.executeUpdate( request );
	        System.out.println( "Requête -- delete from computer where id = " + id + " -- effectuée !" );
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
		
	}

	@Override
	public void updateComputer(int id, String newName, String newIntroduced, String newDiscontinued, Integer newManufacturerId) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        System.out.println( "Objet requête créé !" );
	        String request = "update computer set name = " + newName + ", introduced = " + newIntroduced + ", discontinued = " + newDiscontinued + ", company_id = " + newManufacturerId + " where id = " + id;
	        statut = statement.executeUpdate( request );
	        System.out.println( "Requête -- " + request + " -- effectuée !" );
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
		
	}
	
}
