package com.emaunzpa.computerdatabase.bdd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.emaunzpa.computerdatabase.servlet.ListComputers;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnection {

	private static Properties prop = new Properties();
    private static HikariDataSource ds;
    private Connection connection;
    private static InputStream dbInput;
    private static Logger log; 
    
    public HikariConnection(String databaseName) {
    	log = Logger.getLogger(HikariConnection.class);
    	connection = null;
    	ds = new HikariDataSource();
    	
    	try {
    		/**
    		 * The file database.properties has to be added to the /root/resources/ path with 
    		 * parameters url, user and password for accessing the database.
    		 * TODO Change path to relative path
    		 */
    		HikariConnection.dbInput = new FileInputStream("/home/emaunzpa/excilys/computer-database/resources/"+databaseName+".properties");
			HikariConnection.prop.load(dbInput);
		} catch (FileNotFoundException e) {
			log.error("Erreur lors du chargement du fichier properties " + e.getMessage());
		} catch (IOException e) {
			log.error("Erreur lors du chargement du fichier properties " + e.getMessage());
		}
    	
    	ds.setJdbcUrl(prop.getProperty("url"));
    	ds.setUsername(prop.getProperty("user"));
    	ds.setPassword(prop.getProperty("pwd"));
    	
    }
    
    public void initializeConnection() {
    	
    	try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			log.error("Erreur lors de l'initialisation de la connexion " + e.getMessage());
		}
    	
    }
    
    public void finalizeConnection() {
    	try {
			connection.close();
		} catch (SQLException e) {
			log.error("Impossible de fermer la connexion " + e.getMessage());
		}
    }
 
    public Connection getConnection() {
        return connection;
    }
    
}
