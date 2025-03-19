package eu.crackscout.partynfriends.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
	private static final String URL = "jdbc:mariadb://crackscout.eu:3306/demo";
	private static final String USER = "demo";
	private static final String PASSWORD = "demo";
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void testQuery() {
		try(Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM demo");
			ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
				System.out.println("Eintrag: " + rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}

}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 19, 2025 - 6:19:48 AM
 *
 */