package eu.crackscout.partynfriends.utils;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Message {
	
	private static Message instance;
	

	public static HashMap<String, String> lastmsg = new HashMap<String, String>();
	

	//----------------------------------------------GENERAL MESSAGES---------------------------------------------------------//
	public static BaseComponent[] offlinePlayer(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Der Spieler&e %player% &7ist &cnicht &conline&7.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] enterMsg() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Bitte gebe &ceine &cNachricht &7ein.")).create(); }
	

	//----------------------------------------------DIRECT MESSAGES---------------------------------------------------------//
	public static BaseComponent[] dm_syntax() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cBenutze&8: &e/msg <Spieler> <Nachricht>")).create(); }

	public static BaseComponent[] dmr_syntax() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cBenutze&8: &e/r <Nachricht>")).create(); }
	
	public static BaseComponent[] dm_nofriends(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&c%target% ist nicht dein Freund.".replaceAll("%target%", target))).create(); }
	public static BaseComponent[] dm_selfmessage(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cDu kannst dir nicht selbst Nachrichten senden.")).create(); }
	public static BaseComponent[] dm_notaplayer(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cDu musst ein Spieler sein um dies zu tun.")).create(); }

	public static BaseComponent[] dm_msgSend(String target, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aNACHRICHT &8|&7 Du &6> &7%target%&a:&7 ".replaceAll("%target%", target)+msg)).create(); }
	public static BaseComponent[] dm_msgRecieved(String sender, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aNACHRICHT &8|&7 %sender% &6> &7Du&a:&7 ".replaceAll("%sender%", sender)+msg)).create();	}

	//----------------------------------------------PARTY MESSAGES---------------------------------------------------------//
	public static BaseComponent[] party_listPlayer() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Folgende Spieler befinden sich in der Party:")).create(); }
	public static BaseComponent[] party_noInvite() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast leider &ckeine Partyanfrage &7bekommen.")).create(); }
	public static BaseComponent[] party_accepted() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast die Anfrage &aangenommen&7.")).create(); }
	public static BaseComponent[] party_denied() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast die Anfrage &cabgelehnt&7.")).create(); }
	public static BaseComponent[] party_noParty() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du befindest dich &7in &ckeiner Party&7.")).create(); }
	public static BaseComponent[] party_alreadyInParty(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Der Spieler &e%player% &7ist bereits in &ceiner anderen Party&7.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] party_invited(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast den Spieler &e%target% &aeingeladen&7.".replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_noLeader() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du bist &ckein Party-Leader&7.")).create(); }
	public static BaseComponent[] party_notInParty(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Der Spieler &e%target% &7ist &cnicht &7in deiner Party.".replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_newLeader(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&e%target% &7ist nun der &aParty-Leader&7.".replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_targetInvited(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Der Spieler &e%target% &7hat dich in seine Party &aeingeladen&7.".replaceAll("%target%", target))).create(); }
	public static BaseComponent[] party_maxPlayer() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast die &cmaximale Anzahl &7an Partymitgliedern erreicht.")).create(); }
	public static BaseComponent[] party_joined(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&e%player%&7 ist der Party beigetreten!".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] party_left(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Der Spieler&e %player% &7hat die Party &cverlassen&7.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] party_disabanned() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Die Party wurde &caufgelöst.")).create(); }
	
	public static BaseComponent[] party_msgSend(String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cPARTY &8|&7 Du &6> &7Party&a:&7 "+msg)).create(); }
	public static BaseComponent[] party_msgRecieved(String sender, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cPARTY &8|&7 %sender% &6> &7Du&a:&7 ".replaceAll("%sender%", sender)+msg)).create();	}

	//-------------------------------------------------------------------------------------------------------------------------------------------------------------//

	static BaseComponent send(BaseComponent msg) {
		return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', msg.toString())).build();
	}
	
	
	@SuppressWarnings("unused")
	private TextComponent createComponent(ChatColor color, String text, String cmd) {
		TextComponent tc = new TextComponent();
		tc.setColor(color);
		tc.setText("[" + text + "] ");
		tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
		return tc;
	}
	
	
	@SuppressWarnings("deprecation")
	public void sendMessage(ProxiedPlayer target, String message) { target.sendMessage(message); }
	
	public void sendMessage(ProxiedPlayer target, TextComponent tc) { TextComponent realTc = new TextComponent(""); realTc.addExtra(tc); target.sendMessage(realTc); }

	
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