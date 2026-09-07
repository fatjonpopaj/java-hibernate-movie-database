package de.hsh.dbs2.imdb.other;
import java.sql.SQLException;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionManager {

	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getConnection() throws SQLException {
		
		try {
			
			if (emf == null) {
				
		        emf = Persistence.createEntityManagerFactory("movie");
			}
			
		} catch (Exception e) {
			 
			throw new SQLException("Error while connecting to database");
		} 
			
		return emf;
	}
}
