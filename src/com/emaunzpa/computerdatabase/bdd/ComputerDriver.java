package com.emaunzpa.computerdatabase.bdd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.emaunzpa.computerdatabase.DAO.ComputerDAO;
import com.emaunzpa.computerdatabase.exception.ComputerWithoutNameException;
import com.emaunzpa.computerdatabase.exception.DiscontinuedBeforeIntroducedException;
import com.emaunzpa.computerdatabase.exception.IncoherenceBetweenDateException;
import com.emaunzpa.computerdatabase.exception.NoComputerFoundException;
import com.emaunzpa.computerdatabase.model.*;
import com.emaunzpa.computerdatabase.util.ComputerFormValidator;
import com.emaunzpa.computerdatabase.util.DatesHandler;

public class ComputerDriver implements ComputerDAO {
	
	private Statement statement;
    private ResultSet resultat;
    private PreparedStatement prepareStatement;
    private Integer statut;
    private DatesHandler dh = new DatesHandler();
    private ComputerFormValidator computerFormValidator = new ComputerFormValidator();
    
	private static Logger log;
	private static String databaseName;
	private static String _ADD_COMPUTER_ = "insert into computer (name, introduced, discontinued, company_id) values (?,?,?,?)";
	private static String _GET_ALL_COMPUTERS_ = "select computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name from computer left join company on computer.company_id = company.id order by computer.id";
	private static String _GET_COMPUTER_ = "select id, name, introduced, discontinued, company_id from computer where id = ";
	private static String _UPDATE_COMPUTER_ = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";
	private static String _DELETE_COMPUTER_ = "delete from computer where id = ";
	
    /**
     * Empty creator without params
     * @throws IOException 
     */
	public ComputerDriver(String databaseName) {
		ComputerDriver.databaseName = databaseName;
		log = Logger.getLogger(ComputerDriver.class);
	}

	@Override
	public Optional<Computer> getComputer(int id) throws NoComputerFoundException, SQLException, FileNotFoundException, IOException {
		
//		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
//		connectionDriver.initializeConnection();
		HikariConnection hikariConnection = HikariConnection.getInstance(databaseName);
		Optional<Computer> computer = Optional.empty();
		
		Integer searchId = Integer.valueOf(id);
		// Test if computer exists
		if(!computerFormValidator.computerFound(getAllComputers(), searchId)) {
			return computer;
		}
		
		try {
//	        statement = connectionDriver.getConnection().createStatement();
			statement = hikariConnection.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request = _GET_COMPUTER_ + id;
	        resultat = statement.executeQuery( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        if(resultat.first()) {
	        	int idComputer = resultat.getInt( "id" );
	        	String nameComputer = resultat.getString( "name" );
	            String introducedStr = resultat.getString( "introduced" );
	            String discontinuedStr = resultat.getString( "discontinued" );
	            java.sql.Date introducedDate = dh.convertStringDateToSqlDate(introducedStr);
	            java.sql.Date discontinuedDate = dh.convertStringDateToSqlDate(discontinuedStr);
	            Integer manufacturerId = resultat.getInt( "company_id" );
	            computer = Optional.of(new Computer.ComputerBuilder().build());
	            computer.get().setId(idComputer);
		        computer.get().setName(nameComputer);
		        computer.get().setIntroducedDate(introducedDate);
		        computer.get().setDiscontinuedDate(discontinuedDate);
		        computer.get().setmanufacturerId(manufacturerId);
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
		
//		connectionDriver.finalizeConnection();
		hikariConnection.finalizeConnection();
		log.info("Fin de la connexion");
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
		
		boolean result = false;
//		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
//		connectionDriver.initializeConnection();
		HikariConnection hikariConnection = HikariConnection.getInstance(databaseName);
		
		try {
			String request = _ADD_COMPUTER_;
	        log.info( "Objet requête créé !" );
//			prepareStatement = connectionDriver.getConnection().prepareStatement( request );
	        prepareStatement = hikariConnection.getConnection().prepareStatement(request);
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
		
//		connectionDriver.finalizeConnection();
		hikariConnection.finalizeConnection();
		log.info("Fin de la connexion");
		return result;
	}

	@Override
	public ArrayList<Computer> getAllComputers() throws FileNotFoundException, IOException, SQLException {
		
		ArrayList<Computer> computers = new ArrayList<Computer>();
//		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
//		connectionDriver.initializeConnection();
		HikariConnection hikariConnection = HikariConnection.getInstance(databaseName);
		
		try {
//	        statement = connectionDriver.getConnection().createStatement();
			statement = hikariConnection.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request = _GET_ALL_COMPUTERS_;
	        resultat = statement.executeQuery( request );
	        log.info( "Requête -- " + request + " -- effectuée !" );
	        while ( resultat.next() ) {
	            int idComputer = resultat.getInt( "id" );
	            String nameComputer = resultat.getString( "name" );
	            String introducedStr = resultat.getString( "introduced" );
	            String discontinuedStr = resultat.getString( "discontinued" );
	            String manufacturerName = resultat.getString( "company_name" );
	            java.sql.Date introducedDate = dh.convertStringDateToSqlDate(introducedStr);
	            java.sql.Date discontinuedDate = dh.convertStringDateToSqlDate(discontinuedStr);
	            Integer idCompany = resultat.getInt( "company_id" );
	            computers.add(new Computer.ComputerBuilder().withId(idComputer).withName(nameComputer).withIntroducedDate(introducedDate).withDiscontinuedDate(discontinuedDate).withManufacturerId(idCompany).withManufacturerName(manufacturerName).build());
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
		hikariConnection.finalizeConnection();
//		connectionDriver.finalizeConnection();
		log.info("Fin de la connexion.");
		return computers;
	}

	@Override
	public boolean removeComputer(int id) throws NoComputerFoundException, FileNotFoundException, IOException, SQLException {
		
		boolean result = false;
//		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
//		connectionDriver.initializeConnection();
		HikariConnection hikariConnection = HikariConnection.getInstance(databaseName);
		
		Integer searchId = Integer.valueOf(id);
		if (!computerFormValidator.computerFound(getAllComputers(), searchId)) {
			return false;
		}
		
		try {
//	        statement = connectionDriver.getConnection().createStatement();
			statement = hikariConnection.getConnection().createStatement();
	        log.info( "Objet requête créé !" );
	        String request =  _DELETE_COMPUTER_ + id;
	        statut = statement.executeUpdate( request );
	        log.info( "Requête -- "+ request + " -- effectuée !" );
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
		
//		connectionDriver.finalizeConnection();
		hikariConnection.finalizeConnection();
		log.info("Fin de la connexion.");
		return result;
	}

	@Override
	public boolean updateComputer(int id, String newName, java.sql.Date newIntroduced, java.sql.Date newDiscontinued, Integer newManufacturerId) throws NoComputerFoundException, IncoherenceBetweenDateException, DiscontinuedBeforeIntroducedException, FileNotFoundException, IOException, SQLException {
		
		boolean result = false;
//		ConnectionDriver connectionDriver = new ConnectionDriver(databaseName);
//		connectionDriver.initializeConnection();
		HikariConnection hikariConnection = HikariConnection.getInstance(databaseName);
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
		
		try {
	        String request = _UPDATE_COMPUTER_ ;
	        log.info( "Objet requête créé !" );
//	        prepareStatement = connectionDriver.getConnection().prepareStatement( request );
	        prepareStatement = hikariConnection.getConnection().prepareStatement(request);
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
		
//		connectionDriver.finalizeConnection();
		hikariConnection.finalizeConnection();

		log.info("Fin de connexion.");
		return result;
	}
	
}
