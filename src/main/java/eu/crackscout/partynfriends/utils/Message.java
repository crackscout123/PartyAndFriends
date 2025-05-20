package eu.crackscout.partynfriends.utils;

import java.io.IOException;
import java.util.HashMap;
import eu.crackscout.partynfriends.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.config.Configuration;

public class Message {
	
	private static Message instance;	
	private static Configuration cfg; 
	
	public static String friends_prefix;
	
	public static void init(Main main) throws IOException {
		cfg = main.getLangConfig();
		friends_prefix = cfg.getString("friends.prefix"); 
	}
	
	public static HashMap<String, String> lastmsg = new HashMap<String, String>();
	
	
	//----------------------------------------------GENERAL MESSAGES---------------------------------------------------------//
	public static BaseComponent[] offlinePlayer(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("general.offlinePlayer").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] enterMsg() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("general.enterMessage"))).create(); } 
	
	public static String dateFormat() { return ChatColor.translateAlternateColorCodes('&', cfg.getString("general.dateFormat")); } // dd.MM.yyyy - HH:mm
	

	//----------------------------------------------DIRECT MESSAGES---------------------------------------------------------//
	public static BaseComponent[] dm_syntax() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("directmessage.syntax"))).create(); }

	public static BaseComponent[] dmr_syntax() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("directmessage.responseSyntax"))).create(); }
	
	public static BaseComponent[] dm_nofriends(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("directmessage.nofriends").replaceAll("%target%", target))).create(); }
	public static BaseComponent[] dm_selfmessage(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("directmessage.selfmessage"))).create(); }

	public static BaseComponent[] dm_notaplayer(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("directmessage.notaplayer"))).create(); }

	public static BaseComponent[] dm_msgSend(String target, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("directmessage.chat.send").replaceAll("%target%", target)+msg)).create(); }
	public static BaseComponent[] dm_msgRecieved(String sender, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("directmessage.chat.recieved").replaceAll("%sender%", sender)+msg)).create();	}

	//----------------------------------------------PARTY MESSAGES---------------------------------------------------------//
	public static BaseComponent[] party_help() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.help"))).create(); }
	
	public static BaseComponent[] party_listPlayer(String playercount) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.listPlayer").replaceAll("%playercount%", playercount))).create(); }
	public static BaseComponent[] party_noInvite() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.noInvite"))).create(); }
	public static BaseComponent[] party_accepted() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.inviteAccepted"))).create(); }
	public static BaseComponent[] party_denied() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.inviteDenied"))).create(); }
	public static BaseComponent[] party_noParty() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.notInParty"))).create(); }
	
	public static BaseComponent[] party_alreadyInParty(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.alreadyInParty").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] party_invited(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.invited").replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_noLeader() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.noLeader"))).create(); }
	public static BaseComponent[] party_notInParty(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.notInYourParty").replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_newLeader(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.newLeader").replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_targetInvited(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.targetInvited").replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_maxPlayer() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.maxPlayer"))).create(); }
	public static BaseComponent[] party_joined(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.playerJoined").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] party_left(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.playerLeft").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] party_disabanned() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.disbanned"))).create(); }
	
	public static BaseComponent[] party_msgSend(String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.chat.send")+msg)).create(); }
	public static BaseComponent[] party_msgRecieved(String sender, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.chat.recieved").replaceAll("%sender%", sender)+msg)).create();	}

	public static BaseComponent[] party_serverSwitched(Server server) {return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.serverSwitched").replaceAll("%server%", server.getInfo().getName()))).create();} 
	
	public static BaseComponent[] party_ACCEPT() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.button.accept"))).create(); }
	public static BaseComponent[] party_DENY() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("party.button.deny"))).create(); }

	//----------------------------------------------FRIENDS MESSAGES---------------------------------------------------------//
	
//	public static String friends_prefix = cfg.getString("friends.prefix"); 
	
	public static BaseComponent[] friends_syntax() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.syntax"))).create(); }

	public static BaseComponent[] friends_help() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.help"))).create(); }

	public static BaseComponent[] friends_notBefriended(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.notBefriended").replaceAll("%target%", target))).create(); }
	public static BaseComponent[] friends_requestSend(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.requestSend").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestRecived(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.requestRecived").replaceAll("%player%", player))).create(); }
	
	public static BaseComponent[] friends_alreadyFriends(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.alreadyFriends").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_selfRequest() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.selfRequest"))).create(); }
	public static BaseComponent[] friends_noRequests() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.noRequests"))).create(); }
	public static BaseComponent[] friends_alreadyRequested(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.alreadyRequested").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestAccptedSend(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.requestAcceptedSend").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestAccpetedRecived(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.requestAcceptedRecived").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestIgnored(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.requestIgnored").replaceAll("%player%", player))).create(); } 
	public static BaseComponent[] friends_requestDeniedSend(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.requestDeniedSend").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestDeniedRecived(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.requestDeniedRecived").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_disbanned(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.disbanned").replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_friendList(String size) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.friendList").replaceAll("%size%", size))).create(); }
	public static BaseComponent[] friends_noFriends() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+cfg.getString("friends.noFriends"))).create(); }
	
	public static BaseComponent[] friends_onlineOn(Server server) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.button.onlineOn").replaceAll("%server%", server.getInfo().getName()))).create(); }
	public static BaseComponent[] friends_LASTSEEN(String lastseen) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.button.lastseen").replaceAll("%lastseen%", lastseen))).create(); }
		
	public static BaseComponent[] friends_ACCEPT() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.button.accept"))).create(); }
	public static BaseComponent[] friends_DENY() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.button.deny"))).create(); }
	public static BaseComponent[] friends_IGNORE() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.button.ignore"))).create(); }
	public static BaseComponent[] friends_REMOVE() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.button.remove"))).create(); }
	public static BaseComponent[] friends_OFFLINETAG() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cfg.getString("friends.button.offline"))).create(); }
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------//

	static BaseComponent send(BaseComponent msg) {
		return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', msg.toString())).build();
	}
	
	
	public TextComponent createComponent(ChatColor color, String text, String cmd) {
		TextComponent tc = new TextComponent();
		tc.setColor(color);
		tc.setText("" + text + "");
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
		return tc;
	}
	
	public TextComponent createComponent(String text, String cmd) {
		TextComponent tc = new TextComponent();
		tc.setText("" + ChatColor.translateAlternateColorCodes('&', text) + "");
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
		return tc;
	}
	
	public TextComponent createComponent(BaseComponent[] comp, String cmd) {
	    // Stelle sicher, dass 'comp' nicht null ist
	    if (comp == null || comp.length == 0) {
	        return new TextComponent(""); // Falls leer, gib eine leere Komponente zurück
	    }

	    // Nutze 'toLegacyText', wenn du Farben behalten willst, sonst 'toPlainText'
	    String text = TextComponent.toLegacyText(comp);
	    text = ChatColor.translateAlternateColorCodes('&', text); // Ersetze '&' mit echten Farben

	    // Erstelle die klickbare Nachricht
	    TextComponent tc = new TextComponent("" + text + "");
	    tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));

	    return tc;
	}
	
	
	public void sendMessage(ProxiedPlayer target, String message) {	TextComponent realTc = new TextComponent(""); realTc.addExtra(message); target.sendMessage(realTc); }
	
	public void sendMessage(ProxiedPlayer target, TextComponent tc) { TextComponent realTc = new TextComponent(""); realTc.addExtra(tc); target.sendMessage(realTc); }
	
	public void sendMessage(ProxiedPlayer target, BaseComponent[] bc) { target.sendMessage(bc);	}

	
	public static Message getInstance() {
		if(instance == null) {
			instance = new Message();
		}
		return instance;
	}
	
}

/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 13, 2025 - 12:50:09 PM
 *
 */