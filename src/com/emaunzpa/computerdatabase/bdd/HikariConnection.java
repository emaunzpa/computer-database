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
    
    public HikariConnection(HikariDataSource ds) throws FileNotFoundException, IOException {
			this.dataSource = ds;
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
