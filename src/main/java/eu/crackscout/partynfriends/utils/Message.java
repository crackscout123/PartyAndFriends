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
	
//	public static boolean isDE() { return true; }
	
//	public static String party_noParty() { return isDE() ? "&7Du befindest dich &7in &ckeiner Party&7." : "&7You &caren't &7in a party&7."; }
//	public static String enterMsg() { return isDE() ? "&7Bitte gebe &ceine &cNachricht &7ein." : "&7Please enter a &cmessage&7."; }
//	public static String party_listPlayer() { return isDE() ? "&7Folgende Spieler befinden sich in der Party:" : "&7Following players are in your party:"; }
//	public static String party_noInvite() { return isDE() ? "&7Du hast leider &ckeine Partyanfrage &7bekommen." : "&7Sorry, but you don't have a party invite&7."; }
//	public static String party_accepted() { return isDE() ? "&7Du hast die Anfrage &aangenommen&7." : "&7You &aaccpeted&7 the invite."; }
//	public static String party_denied() { return isDE() ? "&7Du hast die Anfrage &cabgelehnt&7." : "&7You have &cdenied&7 the party request."; }
//	public static String offlinePlayer(String player) { return isDE() ? ("&7Der Spieler&e %player% &7ist &cnicht &conline&7.") : ("&7The player&e %player% &cisn't online&7."); }
//	public static String party_alreadyInParty(String player) { return isDE() ? ("&7Der Spieler &e%player% &7ist bereits in &ceiner anderen Party&7.") : ("&7The player &e%player% &7is already in&c another Party&7."); }
//	public static String party_invited(String target) { return isDE() ? ("&7Du hast den Spieler &e%target% &aeingeladen&7.") : ("&7You invited the Spieler &e%target% &ato your party&7."); }
//	public static String party_noLeader() { return isDE() ? "&7Du bist &ckein Party-Leader&7." : "&7You aren't &ca Party-Leader&7."; }
//	public static String party_notInParty(String target) { return isDE() ? ("&7Der Spieler &e%target% &7ist &cnicht &7in deiner Party.") : ("&7The player &e%target% &cisn't &7in your party."); }
//	public static String party_newLeader(String target) { return isDE() ? ("&e%target% &7ist nun der &aParty-Leader&7.") : ("&e%target% &7is now the &aParty-Leader&7."); }

	public static BaseComponent[] enterMsg() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Bitte gebe &ceine &cNachricht &7ein.")).create(); }

	public static BaseComponent[] party_msgSend(String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cPARTY &8|&7 Du &6> &7Party&a:&7 "+msg)).create(); }
	public static BaseComponent[] party_msgRecieved(String sender, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cPARTY &8|&7 %sender% &6> &7Du&a:&7 ".replaceAll("%sender%", sender)+msg)).create();	}
	public static BaseComponent[] party_listPlayer() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Folgende Spieler befinden sich in der Party:")).create(); }
	public static BaseComponent[] party_noInvite() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast leider &ckeine Partyanfrage &7bekommen.")).create(); }
	public static BaseComponent[] party_accepted() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast die Anfrage &aangenommen&7.")).create(); }
	public static BaseComponent[] party_denied() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du hast die Anfrage &cabgelehnt&7.")).create(); }
	public static BaseComponent[] party_noParty() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Du befindest dich &7in &ckeiner Party&7.")).create(); }
	public static BaseComponent[] offlinePlayer(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Der Spieler&e %player% &7ist &cnicht &conline&7.".replaceAll("%player%", player))).create(); }
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

	static String partyJoined,partyLeft,partyDisbanned,partyNewLeader;

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