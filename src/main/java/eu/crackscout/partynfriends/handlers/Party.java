package eu.crackscout.partynfriends.handlers;

import java.util.ArrayList;
import java.util.List;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.Message;
import eu.crackscout.partynfriends.utils.PartyManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party{

	private Main plugin;
	private ProxiedPlayer owner;
	private List<ProxiedPlayer> players;
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
	
	public boolean isOwner(ProxiedPlayer player) { return this.owner == player; }
	
	public ProxiedPlayer getOwner() { return this.owner; }
	
	public void setOwner(ProxiedPlayer player) { this.owner = player; }
	
	public List<ProxiedPlayer> getPlayers() { return this.players; }
		
	public void chatToParty(Party party, String message) {
		for (ProxiedPlayer member : party.getPlayers()) { PartyManager.getInstance().sendMessage(member, message); }
	}
	
	public void chatToParty(Party party, BaseComponent[] message) {
		for (ProxiedPlayer member : party.getPlayers()) { PartyManager.getInstance().sendMessage(member, message); }
	}
	
	public void addPlayer(ProxiedPlayer player) {
		chatToParty(this, Message.party_joined(player.getName()));
		this.players.add(player);
		this.plugin.getPartyManager().getPartyPlayers().put(player, this);
	}
	
	public void removePlayer(ProxiedPlayer player) {
		Party party = this.plugin.getPartyManager().getPartyPlayers().get(player);
		chatToParty(party, Message.party_left(player.getName()));
		this.players.remove(player);
		this.plugin.getPartyManager().getPartyPlayers().remove(player);
		//check if new party leader has to be set
		if(this.players.size() >= 1) {
			if(isOwner(player)) {
				owner = this.players.get(0);
				chatToParty(party, Message.party_newLeader(owner.getName()));
			}
		} else {
			disband();
		}
	}
	
	public void disband() {
		this.players.clear();
		this.plugin.getPartyManager().getParties().remove(this);
	}
	
	public void invitePlayer(ProxiedPlayer player) {
		if(this.players.size() == this.maxSize) {
			PartyManager.getInstance().sendMessage(player, Message.party_maxPlayer());
			return;
		}
		TextComponent acceptTc = Message.getInstance().createComponent(Message.party_ACCEPT(), "/party accept");
		TextComponent denyTc = Message.getInstance().createComponent(Message.party_DENY(), "/party deny");
		TextComponent tc = new TextComponent(Message.party_targetInvited(owner.getName()) + "\n");
		tc.addExtra(acceptTc);
		tc.addExtra(ChatColor.GRAY + " - ");
		tc.addExtra(denyTc);
		
		PartyManager.getInstance().sendMessage(player, tc);
		this.plugin.getPartyManager().getInvites().put(player, this);
	}
}

/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 11, 2025 - 6:06:39 AM
 *
 */