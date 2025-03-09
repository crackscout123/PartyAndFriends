package eu.crackscout.partynfriends.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.crackscout.partynfriends.listeners.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyManager {

	private List<Party> parties = new ArrayList<Party>();
	private Map<ProxiedPlayer, Party> playerInParty = new HashMap<ProxiedPlayer, Party>();
	private Map<ProxiedPlayer, Party> invites = new HashMap<ProxiedPlayer, Party>();
	
	//-------------------------------------------------------------------------------------------------------//
	
	public boolean isInParty(ProxiedPlayer player) { return this.playerInParty.containsKey(player); }
	
	public boolean isPartyLeader(ProxiedPlayer player) { return (isInParty(player) && ((Party)this.playerInParty.get(player)).isOwner(player)); }
	
	public List<Party> getParties() { return this.parties; }
	
	public Map<ProxiedPlayer, Party> getPartyPlayers() { return this.playerInParty; }
	
	public Map<ProxiedPlayer, Party> getInvites() { return this.invites; }
	
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 10, 2025 - 12:13:27 AM
 *
 */