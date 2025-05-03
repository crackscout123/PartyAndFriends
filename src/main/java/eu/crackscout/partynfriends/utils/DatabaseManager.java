package eu.crackscout.partynfriends.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.crackscout.partynfriends.Main;

public class DatabaseManager {
	private static final String MYSQL_URL = "jdbc:mariadb://crackscout.eu:3306/demo";
	private static final String MYSQL_USER = "demo";
	private static final String MYSQL_PASSWORD = "demo";
	
	//SQLite Path
	private static String sqliteDbPath;
	private static Connection connection;
	
	private static String dbType = "mysql"; //default value
	
	/**
	 * Initialisiert den DatabaseManager
	 * @param main Plugin-Hauptklasse
	 * @param type Datenbanktyp ("mysql" oder "sqlite")
	 */
	public static void init(Main main, String type) {
		dbType = type.toLowerCase();
		
		//SQLite
		if("sqlite".equals(dbType)) {
			File dataFolder = main.getDataFolder();
	        if (!dataFolder.exists()) {
	            dataFolder.mkdirs();
	        }
	        sqliteDbPath = "jdbc:sqlite:" + new File(dataFolder, "database.db").getAbsolutePath();
		}
		main.getLogger().info("Datenbank-Typ: " + dbType);
	}
	
	public static Connection getConnection() {
		try {
			if(connection != null && connection.isClosed()) {
				return connection;
			}
			if("mysql".equals(dbType)) {
				connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
			} else {
				connection = DriverManager.getConnection(sqliteDbPath);
			}
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public static boolean isSQLite() { return "sqlite".equals(dbType); }
	
	
	
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