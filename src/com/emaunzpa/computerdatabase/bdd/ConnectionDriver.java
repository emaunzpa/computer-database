package com.emaunzpa.computerdatabase.bdd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

public class ConnectionDriver {

	private static Properties prop = new Properties();
	private static InputStream dbInput;
	private static String _URL_;
    private static String _USER_;
    private static String _MDP_;
    private static Logger log;	  
    
    private Connection connection;
    
    public ConnectionDriver(String databaseName) {
    	this.connection = null;
    	PropertyConfigurator.configure("resources/log4j.properties");
    	log = Logger.getLogger(ConnectionDriver.class.getName());
    	
    	try {
    		/**
    		 * The file database.properties has to be added to the /root/resources/ path with 
    		 * parameters url, user and password for accessing the database.
    		 * TODO Change path to relative path
    		 */
			ConnectionDriver.dbInput = new FileInputStream("/home/emaunzpa/excilys/computer-database/resources/"+databaseName+".properties");
			ConnectionDriver.prop.load(dbInput);
		} catch (FileNotFoundException e) {
			log.error("Erreur lors du chargement du fichier properties " + e.getMessage());
		} catch (IOException e) {
			log.error("Erreur lors du chargement du fichier properties " + e.getMessage());
		}
    	ConnectionDriver._URL_= prop.getProperty("url");
    	ConnectionDriver._USER_= prop.getProperty("user");
    	ConnectionDriver._MDP_= prop.getProperty("pwd");
    }
    
    public void initializeConnection() {
    	try {
	    	log.info( "Chargement du driver..." );
	        Class.forName( "com.mysql.jdbc.Driver" );
	        log.info( "Driver chargé !" );
	    } catch ( ClassNotFoundException e ) {
	    	log.error( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! "
	                + e.getMessage() );
	    }
	    
	    try {
	    	log.info( "Connexion à la base de données..." );
	        connection = DriverManager.getConnection( _URL_, _USER_, _MDP_ );
	        log.info( "Connexion réussie !" );	        
	    } catch ( SQLException e ) {
	    	log.error( "Erreur lors de la connexion : "
	                + e.getMessage() );
	   	}
    }
    
    public void finalizeConnection() {
		if ( connection != null ) {
            try {
                connection.close();
            } catch ( SQLException ignore ) {
            }
        }
	}

	public static String get_URL_() {
		return _URL_;
	}

	public static void set_URL_(String _URL_) {
		ConnectionDriver._URL_ = _URL_;
	}

	public static String get_USER_() {
		return _USER_;
	}

	public static void set_USER_(String _USER_) {
		ConnectionDriver._USER_ = _USER_;
	}

	public static String get_MDP_() {
		return _MDP_;
	}

	public static void set_MDP_(String _MDP_) {
		ConnectionDriver._MDP_ = _MDP_;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
    
    
}
