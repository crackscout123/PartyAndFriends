package eu.crackscout.partynfriends.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class FriendsManager {
	
	public static void addPlayerIfNotExists(String uuid) {
		ProxiedPlayer player =ProxyServer.getInstance().getPlayer(UUID.fromString(uuid));
		String name = player.getName();
	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(
	             "INSERT INTO players (uuid, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name)")) {
	        
	        stmt.setString(1, uuid);
	        stmt.setString(2, name);
	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
    public static boolean addFriend(String playerUUID, String friendUUID) {
        String sql = "INSERT INTO friends (player_uuid, friend_uuid, since) VALUES (?, ?, NOW())";
        
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
        String insertQuery = "INSERT INTO friend_requests (sender_uuid, receiver_uuid, status) VALUES (?, ?, 'pending')";

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
            String deleteRequestSQL = "DELETE FROM friend_requests WHERE sender_uuid = ? AND receiver_uuid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteRequestSQL)) {
                stmt.setString(1, senderUUID);
                stmt.setString(2, receiverUUID);
                stmt.executeUpdate();
            }
            
            // 2. Freunde in die `friends`-Tabelle eintragen
            String insertFriendSQL = "INSERT INTO friends (player_uuid, friend_uuid, since) VALUES (?, ?, NOW()), (?, ?, NOW())";
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

    public static List<String> getFriends(String playerUUID) {
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
    
    public static boolean updateLastSeen(String playerUUID) {
        String sql = "UPDATE players SET last_seen = NOW() WHERE uuid = ?";

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
                return rs.getInt(1) > 0; // Falls die Anzahl größer als 0 ist, sind sie befreundet
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