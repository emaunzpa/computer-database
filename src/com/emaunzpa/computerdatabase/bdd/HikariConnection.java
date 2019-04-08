package com.emaunzpa.computerdatabase.bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnection {

    private HikariDataSource dataSource;
    
    public HikariConnection(String databaseName) throws FileNotFoundException, IOException {
    	
	    	/**
			 * The file database.properties has to be added to the /root/resources/ path with 
			 * parameters url, user and password for accessing the database.
			 */
			Properties prop = new Properties();
			prop.load(this.getClass().getClassLoader().getResourceAsStream(databaseName + "HikariConfig.properties"));
    		HikariConfig config = new HikariConfig(prop);
			dataSource = new HikariDataSource(config);
    }
    
    public void finalizeConnection() throws SQLException {
    	dataSource.close();
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
    
}
