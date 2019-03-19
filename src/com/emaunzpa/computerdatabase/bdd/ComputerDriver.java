package com.emaunzpa.computerdatabase.bdd;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import com.emaunzpa.computerdatabase.DAO.ComputerDAO;
import com.emaunzpa.computerdatabase.model.*;

public class ComputerDriver implements ComputerDAO {
	
	private Statement statement;
    private ResultSet resultat;
    private PreparedStatement prepareStatement;
    private Integer statut;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	private static Logger log = Logger.getLogger(ComputerDriver.class);
	private static HTMLLayout htmlLayout = new HTMLLayout();
	
    /**
     * Empty creator without params
     */
	public ComputerDriver() {

		RollingFileAppender rollingfileAppender = null;
		try {
			rollingfileAppender = new RollingFileAppender(htmlLayout, "logging/log4j/ComputerDriverLogger.html");
			log.addAppender(rollingfileAppender);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public Computer getComputer(int id) {
		
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		Computer computer = new Computer.ComputerBuilder().build();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request = "select * from computer where id = " + id;
	        resultat = statement.executeQuery( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
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
	        log.error( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	log.info( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            	log.warn( "La fermeture de l'objet ResultSet a généré une exception" );
	            }
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            	log.warn( "La fermeture de l'objet Statement a généré une exception" );
	            }
	        }
	       	        
	    }
		
		connectionDriver.finalizeConnection();
		log.info("Fin de la connexion");
		return computer; 
	}

	@Override
	public boolean addComputer(Computer computer) {
		
		// Impossible to add a computer without a name
		if(computer.getName() == null) {
			log.error("Impossible to add a computer without any name to the database. Request cancelled.");
			return false;
		}
		
		boolean result = false;
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
			String request = "insert into computer (name, introduced, discontinued, company_id) values (?,?,?,?)";
	        log.info( "Objet requête créé !" );
			prepareStatement = connectionDriver.getConnection().prepareStatement( request );
	        prepareStatement.setString(1, computer.getName());
	        prepareStatement.setDate(2, computer.getIntroducedDate());
	        prepareStatement.setDate(3, computer.getDiscontinuedDate());
	        prepareStatement.setObject(4, computer.getmanufacturerId());
	        prepareStatement.executeUpdate();
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        result = true;
	    } catch ( SQLException e ) {
	        log.error( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	log.info( "Réinitialisation du statut." );
	        if ( statut != null ) {
	            statut = null;
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            	log.warn("La fermeture du Statement a généré une exception.");
	            }
	        }
	       	        
	    }
		
		connectionDriver.finalizeConnection();
		log.info("Fin de la connexion");
		return result;
	}

	@Override
	public ArrayList<Computer> getAllComputers() {
		
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request = "select computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name from computer left join company on computer.company_id = company.id order by computer.id";
	        resultat = statement.executeQuery( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        while ( resultat.next() ) {
	            int idComputer = resultat.getInt( "id" );
	            String nameComputer = resultat.getString( "name" );
	            String introducedStr = resultat.getString( "introduced" );
	            String discontinuedStr = resultat.getString( "discontinued" );
	            String manufacturerName = resultat.getString( "company_name" );
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
	            computers.add(new Computer.ComputerBuilder().withId(idComputer).withName(nameComputer).withIntroducedDate(introducedDate).withDiscontinuedDate(discontinuedDate).withManufacturerId(idCompany).withManufacturerName(manufacturerName).build());
	        }
	    } catch ( SQLException | ParseException e ) {
	        log.error( "Erreur lors de la connexion : <br/>"
	                + e.getMessage() );
	    } finally {
	    	log.info( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            	log.warn("La fermeture de l'objet Resultset a provoqué une exception.");
	            }
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            	log.warn("La fermeture de l'objet Statement a provoqué une exception.");
	            }
	        }
	       	        
	    }
		
		connectionDriver.finalizeConnection();
		log.info("Fin de la connexion.");
		return computers;
	}

	@Override
	public boolean removeComputer(int id) {
		
		boolean result = false;
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		try {
	        statement = connectionDriver.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request = "delete from computer where id = " + id;
	        statut = statement.executeUpdate( request );
	        log.info( "Requête -- delete from computer where id = " + id + " -- effectuée !" );
	        result = true;
	    } catch ( SQLException e ) {
	        log.error( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	log.info( "Réinitialisation du statut" );
	        if ( statut != null ) {
	           statut = null;
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            	log.warn("La fermeture de l'objet Statement a provoqué une exception.");
	            }
	        }
	       	        
	    }
		
		connectionDriver.finalizeConnection();
		log.info("Fin de la connexion.");
		return result;
	}

	@Override
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId) {
		
		boolean result = false;
		ConnectionDriver connectionDriver = new ConnectionDriver();
		connectionDriver.initializeConnection();
		
		// Cannot update a unexisting computer
		if (getComputer(id).getName() == null) {
			log.error("Impossible to update a unexisting computer. Request cancelled.");
			return false;
		}
		
		try {
	        String request = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
	        log.info( "Objet requête créé !" );
	        prepareStatement = connectionDriver.getConnection().prepareStatement( request );
	        prepareStatement.setString(1, newName);
	        prepareStatement.setDate(2, newIntroduced);
	        prepareStatement.setDate(3, newDiscontinued);
	        prepareStatement.setObject(4, newManufacturerId);
	        prepareStatement.setInt(5, id); 
	        prepareStatement.executeUpdate();
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        result = true;
	    } catch ( SQLException e ) {
	        log.error( "Erreur lors de la connexion : "
	                + e.getMessage() );
	    } finally {
	    	log.info( "Réinitialisation du statut" );
	        if ( statut != null ) {
	            statut = null;
	        }
	        log.info( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            	log.warn("La fermeture de l'objet Statement a provoqué une exception.");
	            }
	        }
	       	        
	    }
		
		connectionDriver.finalizeConnection();
		log.info("Fin de connexion.");
		return result;
	}
	
}
