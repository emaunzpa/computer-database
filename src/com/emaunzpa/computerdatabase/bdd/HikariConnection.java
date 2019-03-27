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

    private HikariDataSource dataSource;
    private static HikariConnection instance = null;
    
    private HikariConnection(HikariDataSource ds){
  	  	this.dataSource = ds;
    }
    
    public static HikariConnection getInstance(String databaseName) throws FileNotFoundException, IOException {
    	
    	if (instance == null) {
	    	/**
			 * The file database.properties has to be added to the /root/resources/ path with 
			 * parameters url, user and password for accessing the database.
			 * TODO Change path to relative path
			 */
			HikariConfig config = new HikariConfig("/home/emaunzpa/excilys/computer-database/resources/" + databaseName + "HikariConfig.properties");
			HikariDataSource dataSource = new HikariDataSource(config);
			instance = new HikariConnection(dataSource);
    	}
    	
		return instance;
    }
    
    public void finalizeConnection() throws SQLException {
    	dataSource.close();
    	instance = null;
    }
 
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

	public HikariDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(HikariDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static void setInstance(HikariConnection instance) {
		HikariConnection.instance = instance;
	}
    
}
