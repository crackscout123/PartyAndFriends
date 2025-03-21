package eu.crackscout.partynfriends.utils;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

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

	public static BaseComponent[] party_serverSwitched(Server server) {return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7Die Party hat den Server &e%server% &abetreten&7.".replaceAll("%server%", server.getInfo().getName()))).create();} 
	
	public static BaseComponent[] party_ACCEPT() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&2&l[&a&lAKZEPTIEREN&2&l]")).create(); }
	public static BaseComponent[] party_DENY() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&4&l[&c&lABLEHNEN&4&l]")).create(); }

	//----------------------------------------------FRIENDS MESSAGES---------------------------------------------------------//
	
	public static String friends_prefix = "&8[&cFreunde§8] "; 
	
	public static BaseComponent[] friends_help() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&7\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\n"
																														+ "&7\u2588 &6/friend add <spieler>&8- &7Adde einen Freund\n"
																														+ "&7\u2588 &6/friend remove <spieler>&8- &7Remove einen Freund\n"
																														+ "&7\u2588 &6/friend join <spieler>&8- &7Springe zum Server eines Freundes\n"
																														+ "&7\u2588 &6/friend accept [spieler]&8- &7Akzeptiere eine Anfrage\n"
																														+ "&7\u2588 &6/friend deny [spieler]&8- &7Lehene eine Anfrage ab\n"
																														+ "&7\u2588 &6/friend ignore [spieler]&8- &7Ignoriere eine Anfrage\n"
																														+ "&7\u2588 &6/friend list &8- &7Zeige die Freundesliste\n"
																														+ "&7\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588")).create(); }
	public static BaseComponent[] friends_syntax() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&cBenutze&8: &e/friend ")).create(); }

	public static BaseComponent[] friends_notBefriended(String target) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&c%target% ist nicht dein Freund.".replaceAll("%target%", target))).create(); }
	public static BaseComponent[] friends_requestSend(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&7Du hast &a%player% &7eine Anfrage gesendet.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestRecived(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&7Du haste eine Anfrage von &a%player% &7bekommen!".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_ACCEPT() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&2&l[&a&lAKZEPTIEREN&2&l]")).create(); }
	public static BaseComponent[] friends_DENY() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&4&l[&c&lABLEHNEN&4&l]")).create(); }
	public static BaseComponent[] friends_IGNORE() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&6&l[&e&lIGNORIEREN&6&l]")).create(); }
	public static BaseComponent[] friends_REMOVE() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&4&l[&c&lENTFERNEN&4&l]")).create(); }
	public static BaseComponent[] friends_OFFLINETAG() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&6&l[&e&lOFFLINE&6&l]")).create(); }
	public static BaseComponent[] friends_alreadyFriends(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&c%player% &7ist bereits dein Freund.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_selfRequest() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&cDu kannst dich nicht selber als Fruend hinzufügen.")).create(); }
	public static BaseComponent[] friends_noRequests() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&cDu hast momentan keine Anfragen.")).create(); }
	public static BaseComponent[] friends_alreadyRequested(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&cDu hast &e%player% &cbereits eine Anfrage gesendet.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestAccptedSend(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&7Du hast die Anfrage von &e%player%&7 angenommen.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestAccpetedRecived(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&e%player% &7hat deine Anfrage angenommen.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestIgnored(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&7Du ignoriest die Anfrage von &e%player%&7.".replaceAll("%player%", player))).create(); } 
	public static BaseComponent[] friends_requestDeniedSend(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&7Du hast die Anfrage von &e%player%&7 abgelehnt.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_requestDeniedRecived(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&e%player% &7hat deine Anfrage abgelehnt.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_disbanned(String player) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&7Du bist nun kein Freund mehr von &e%player%.".replaceAll("%player%", player))).create(); }
	public static BaseComponent[] friends_friendList(String size) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&6&lFreundes Liste &7(&eAnzahl von Freunden&8: &e%size%&7)&e&l:".replaceAll("%size%", size))).create(); }
	public static BaseComponent[] friends_onlineOn(Server server) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&2&l[&a&lOnline auf %server%&2&l]".replaceAll("%server%", server.getInfo().getName()))).create(); }
	public static BaseComponent[] friends_noFriends() { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', friends_prefix+"&7&oDu hast keine Freunde.")).create(); }
	
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