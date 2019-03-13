package com.emaunzpa.computerdatabase.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDriver {

	private static String _URL_ = "jdbc:mysql://localhost:3306/computer-database-db";
    private static String _USER_ = "admincdb";
    private static String _MDP_ = "qwerty1234";
    
    private Connection connection;
    
    public ConnectionDriver() {
    	this.connection = null;
    }
    
    public void initializeConnection() {
    	try {
	    	System.out.println( "Chargement du driver..." );
	        Class.forName( "com.mysql.jdbc.Driver" );
	        System.out.println( "Driver chargé !" );
	    } catch ( ClassNotFoundException e ) {
	    	System.out.println( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! "
	                + e.getMessage() );
	    }
	    
	    try {
	    	System.out.println( "Connexion à la base de données..." );
	        connection = DriverManager.getConnection( _URL_, _USER_, _MDP_ );
	        System.out.println( "Connexion réussie !" );

	        
	    } catch ( SQLException e ) {
	    	System.out.println( "Erreur lors de la connexion : "
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
