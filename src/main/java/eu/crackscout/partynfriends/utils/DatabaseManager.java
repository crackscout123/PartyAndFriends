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
	        sqliteDbPath = "jdbc:sqlite:" + new File(dataFolder, "database.db").getAbsolutePath();
		}
		main.getLogger().info("Datenbank-Typ: " + dbType);
		createTables();
	}
	
	public static void createTables() {
	    try(Connection conn = getConnection()) {
	        boolean sqlite = isSQLite();

	        // === Players ===
	        String createPlayersTable = sqlite
	                ? "CREATE TABLE IF NOT EXISTS players ("
	                  + "uuid TEXT PRIMARY KEY, "
	                  + "name TEXT NOT NULL, "
	                  + "last_seen TEXT)"
	                : "CREATE TABLE IF NOT EXISTS players ("
	                  + "uuid VARCHAR(36) PRIMARY KEY, "
	                  + "name VARCHAR(16) NOT NULL, "
	                  + "last_seen DATETIME DEFAULT NULL)";

	        // === Friends ===
	        String createFriendsTable = sqlite
	                ? "CREATE TABLE IF NOT EXISTS friends ("
	                  + "player_uuid TEXT NOT NULL, "
	                  + "friend_uuid TEXT NOT NULL, "
	                  + "since TEXT NOT NULL, "
	                  + "PRIMARY KEY (player_uuid, friend_uuid))"
	                : "CREATE TABLE IF NOT EXISTS friends ("
	                  + "player_uuid VARCHAR(36) NOT NULL, "
	                  + "friend_uuid VARCHAR(36) NOT NULL, "
	                  + "since DATETIME NOT NULL, "
	                  + "PRIMARY KEY (player_uuid, friend_uuid), "
	                  + "FOREIGN KEY (player_uuid) REFERENCES players(uuid), "
	                  + "FOREIGN KEY (friend_uuid) REFERENCES players(uuid))";

	        // === Friend Requests ===
	        String createFriendRequestsTable = sqlite
	                ? "CREATE TABLE IF NOT EXISTS friend_requests ("
	                  + "sender_uuid TEXT NOT NULL, "
	                  + "receiver_uuid TEXT NOT NULL, "
	                  + "send_at TEXT NOT NULL, "
	                  + "status TEXT DEFAULT 'pending', "
	                  + "PRIMARY KEY (sender_uuid, receiver_uuid))"
	                : "CREATE TABLE IF NOT EXISTS friend_requests ("
	                  + "sender_uuid VARCHAR(36) NOT NULL, "
	                  + "receiver_uuid VARCHAR(36) NOT NULL, "
	                  + "send_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
	                  + "status VARCHAR(20) DEFAULT 'pending', "
	                  + "PRIMARY KEY (sender_uuid, receiver_uuid), "
	                  + "FOREIGN KEY (sender_uuid) REFERENCES players(uuid), "
	                  + "FOREIGN KEY (receiver_uuid) REFERENCES players(uuid))";

	        try (PreparedStatement stmt1 = conn.prepareStatement(createPlayersTable);
	             PreparedStatement stmt2 = conn.prepareStatement(createFriendsTable);
	             PreparedStatement stmt3 = conn.prepareStatement(createFriendRequestsTable)) {

	            stmt1.execute();
	            stmt2.execute();
	            stmt3.execute();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
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