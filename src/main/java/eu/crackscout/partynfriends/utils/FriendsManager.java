package eu.crackscout.partynfriends.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class FriendsManager {
		
	public static List<String> getOpenFriendRequests(String playerUUID) {
	    List<String> openRequests = new ArrayList<>();
	    String sql = "SELECT sender_uuid FROM friend_requests WHERE receiver_uuid = ? AND status = 'pending'";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, playerUUID);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            openRequests.add(rs.getString("sender_uuid"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return openRequests;
	}

	public static boolean deleteFriendRequest(String senderUuid, String receiverUuid) {
	    String deleteQuery = "DELETE FROM friend_requests WHERE sender_uuid = ? AND receiver_uuid = ?";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

	        deleteStmt.setString(1, senderUuid);
	        deleteStmt.setString(2, receiverUuid);
	        int rowsAffected = deleteStmt.executeUpdate();

	        if (rowsAffected > 0) {
	            return true; // Erfolgreich gelöscht
	        } else {
	            return false; // Fehler beim löschen
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static void addPlayerIfNotExists(String uuid) {
		ProxiedPlayer player =ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
		if(player == null) return;
		
		String name = player.getName();
		String sql;
		
        if (DatabaseManager.isSQLite()) {
            sql = "INSERT INTO players (uuid, name) VALUES (?, ?) "
                + "ON CONFLICT(uuid) DO UPDATE SET name = excluded.name";
        } else {
            sql = "INSERT INTO players (uuid, name) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE name = VALUES(name)";
        }
		
	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, uuid);
	        stmt.setString(2, name);
	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
    public static boolean addFriend(String playerUUID, String friendUUID) {
        String sql;
        
        if (DatabaseManager.isSQLite()) {
            sql = "INSERT INTO friends (player_uuid, friend_uuid, since) VALUES (?, ?, datetime('now'))";
        } else {
            sql = "INSERT INTO friends (player_uuid, friend_uuid, since) VALUES (?, ?, NOW())";
        }
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, playerUUID);
            stmt.setString(2, friendUUID);
            stmt.executeUpdate();
            return true; // Erfolgreich hinzugefügt
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Fehler beim Hinzufügen
        }
    }
    
    public static boolean sendFriendRequest(String senderUUID, String receiverUUID) {
    	addPlayerIfNotExists(senderUUID);
    	addPlayerIfNotExists(receiverUUID);

        String checkQuery = "SELECT COUNT(*) FROM friend_requests WHERE sender_uuid = ? AND receiver_uuid = ?";
        String insertQuery;
        
        if (DatabaseManager.isSQLite()) {
            insertQuery = "INSERT INTO friend_requests (sender_uuid, receiver_uuid, status, send_at) VALUES (?, ?, 'pending', datetime('now'))";
        } else {
            insertQuery = "INSERT INTO friend_requests (sender_uuid, receiver_uuid, status, send_at) VALUES (?, ?, 'pending', NOW())";
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Überprüfen, ob bereits eine Anfrage existiert
            checkStmt.setString(1, senderUUID);
            checkStmt.setString(2, receiverUUID);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            // Wenn keine Anfrage existiert, eine neue einfügen
            insertStmt.setString(1, senderUUID);
            insertStmt.setString(2, receiverUUID);
            insertStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean acceptFriendRequest(String senderUUID, String receiverUUID) {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false); // Transaktion starten
            
            // 1. Anfrage löschen
            String deleteRequestSQL1 = "DELETE FROM friend_requests WHERE sender_uuid = ? AND receiver_uuid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteRequestSQL1)) {
                stmt.setString(1, senderUUID);
                stmt.setString(2, receiverUUID);
                stmt.executeUpdate();
            }
            
            // 2. Anfrage löschen
            String deleteRequestSQL2 = "DELETE FROM friend_requests WHERE sender_uuid = ? AND receiver_uuid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteRequestSQL2)) {
                stmt.setString(1, receiverUUID);
                stmt.setString(2, senderUUID);
                stmt.executeUpdate();
            }
            
            //  Freunde in die `friends`-Tabelle eintragen
            String insertFriendSQL;

            if (DatabaseManager.isSQLite()) {
                insertFriendSQL = "INSERT INTO friends (player_uuid, friend_uuid, since) VALUES (?, ?, datetime('now')), (?, ?, datetime('now'))";
            } else {
                insertFriendSQL = "INSERT INTO friends (player_uuid, friend_uuid, since) VALUES (?, ?, NOW()), (?, ?, NOW())";
            }
            
            try (PreparedStatement stmt = conn.prepareStatement(insertFriendSQL)) {
                stmt.setString(1, senderUUID);
                stmt.setString(2, receiverUUID);
                stmt.setString(3, receiverUUID);
                stmt.setString(4, senderUUID);
                stmt.executeUpdate();
            }

            conn.commit(); // Transaktion erfolgreich
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeFriend(String playerUUID, String friendUUID) {
        String sql = "DELETE FROM friends WHERE (player_uuid = ? AND friend_uuid = ?) OR (player_uuid = ? AND friend_uuid = ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, playerUUID);
            stmt.setString(2, friendUUID);
            stmt.setString(3, friendUUID);
            stmt.setString(4, playerUUID);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getFriendUUIDs(String playerUUID) {
        List<String> friends = new ArrayList<>();
        String sql = "SELECT friend_uuid FROM friends WHERE player_uuid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, playerUUID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                friends.add(rs.getString("friend_uuid"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return friends;
    }
    
    public static String resolveUUID(String playerUUID) {
        String resolvedName = null;
        String sql = "SELECT name FROM players WHERE uuid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, playerUUID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	resolvedName = rs.getString("name");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return resolvedName;
    }
    
    public static boolean updateLastSeen(String playerUUID) {
        String sql;
        
        if (DatabaseManager.isSQLite()) {
            sql = "UPDATE players SET last_seen = datetime('now') WHERE uuid = ?";
        } else {
            sql = "UPDATE players SET last_seen = NOW() WHERE uuid = ?";
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, playerUUID);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static String getLastSeen(String playerUUID) {
        String lastseen = null;
        String sql = "SELECT last_seen FROM players WHERE uuid = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, playerUUID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	lastseen = rs.getString("last_seen");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return formatLastSeen(lastseen);
    }

    public static String formatLastSeen(String lastseen) {
        if (lastseen == null) return "N/A";
        try {
            // Ursprüngliches Format aus der Datenbank
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dbFormat.parse(lastseen);

            // Ziel-Format: 21.03.2025 - 06:47
            SimpleDateFormat displayFormat = new SimpleDateFormat(Message.dateFormat()); // "dd.MM.yyyy - HH:mm"
            return displayFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            // Gib das Originaldatum zurück, falls etwas schiefgeht
            return lastseen;
        }
    }
    
    public static boolean areFriends(String playerUUID, String friendUUID) {
        String sql = "SELECT COUNT(*) FROM friends WHERE (player_uuid = ? AND friend_uuid = ?) OR (player_uuid = ? AND friend_uuid = ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, playerUUID);
            stmt.setString(2, friendUUID);
            stmt.setString(3, friendUUID);
            stmt.setString(4, playerUUID);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Falls die Anzahl groeszer als 0 ist, sind sie befreundet
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Falls es einen Fehler gab oder nichts gefunden wurde
    }
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 19, 2025 - 7:29:39 AM
 *
 */