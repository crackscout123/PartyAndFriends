package eu.crackscout.partynfriends.listeners;

import java.util.ArrayList;
import java.util.List;

import eu.crackscout.partynfriends.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party{

	private Main plugin;
	private ProxiedPlayer owner;
	private List<ProxiedPlayer> players;
	@SuppressWarnings("unused")
	private int maxSize = 10; // CHANGE ME TO READ CONFIG FILE
	
	
	public Party(Main plugin, ProxiedPlayer owner) { 
		
		this.plugin = plugin; 
		this.owner = owner;
		
		this.players = new ArrayList<ProxiedPlayer>();
		this.players.add(owner);
		
		plugin.getPartyManager().getParties().add(this);

//		MAX PARTY LIMIT PERMISSIONS AND SHIT		
//				
//		this.maxSize = Main.standart;
//		if (owner.hasPermission("party.premium")) {
//			this.maxSize = Main.premium;
//		}
//		if (owner.hasPermission("party.youtuber")) {
//			this.maxSize = Main.youtuber;
//		}		
	}
	
	public boolean isOwner(ProxiedPlayer player) { return true; }
	
	public ProxiedPlayer getOwner() { return this.owner; }
	
	public void setOwner(ProxiedPlayer player) { this.owner = player; }
	
	public List<ProxiedPlayer> getPlayers() { return this.players; }
	
	
	public void chatToParty(String message) {
		// sendMessage for each ProxiedPlayer in this.party
		// for (player : this.players) { party.sendMessage(message) }
	}
	
	public void addPlayer(ProxiedPlayer player) {
		chatToParty("player joined the party!");
		this.players.add(player);
		this.plugin.getPartyManager().getPartyPlayers().put(player, this);
	}
	
	public void removePlayer(ProxiedPlayer player) {
		chatToParty("player has left the party!");
		this.players.remove(player);
		this.plugin.getPartyManager().getPartyPlayers().remove(player);
		//check if new party leader has to be set
		if(this.players.size() >= 1) {
			if(isOwner(player)) {
				isOwner(this.players.get(0));
				chatToParty("player is now the party leader!");
			}
		} else {
			disband();
		}
	}
	
	public void disband() {
		chatToParty("the party was disbanned!"); // party aufgelöst
		this.players.clear();
		this.plugin.getPartyManager().getParties().remove(this);
	}
	
	public void invitePlayer(ProxiedPlayer player) {
		// need to do asap
	}
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 11, 2025 - 6:06:39 AM
 *
 */