package eu.crackscout.partynfriends.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.crackscout.partynfriends.handlers.Party;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyManager {
	
	private static PartyManager instance;

	private List<Party> parties = new ArrayList<Party>();
	private Map<ProxiedPlayer, Party> playerInParty = new HashMap<ProxiedPlayer, Party>();
	private Map<ProxiedPlayer, Party> invites = new HashMap<ProxiedPlayer, Party>();
	
	//----------------------------------------------HANDLING---------------------------------------------------------//
	
	public boolean isInParty(ProxiedPlayer player) { return this.playerInParty.containsKey(player); }
	
	public boolean isPartyLeader(ProxiedPlayer player) { return (isInParty(player) && ((Party)this.playerInParty.get(player)).isOwner(player)); }
	
	public List<Party> getParties() { return this.parties; }
	
	public Map<ProxiedPlayer, Party> getPartyPlayers() { return this.playerInParty; }
	
	public Map<ProxiedPlayer, Party> getInvites() { return this.invites; }
	
	//----------------------------------------------MESSAGING---------------------------------------------------------//
	
	public void sendMessage(ProxiedPlayer target, String message) {	TextComponent realTc = new TextComponent(""); realTc.addExtra(message); target.sendMessage(realTc); }
	
	public void sendMessage(ProxiedPlayer target, TextComponent tc) { TextComponent realTc = new TextComponent(""); realTc.addExtra(tc); target.sendMessage(realTc); }
	
	public void sendMessage(ProxiedPlayer target, BaseComponent[] bc) { target.sendMessage(bc);	}
	
	
	
	
	
	
	
	public static PartyManager getInstance() { if (instance == null) { instance = new PartyManager(); }	return instance; }
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 10, 2025 - 12:13:27 AM
 *
 */