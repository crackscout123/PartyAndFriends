package eu.crackscout.partynfriends.commands.messaging;


import java.util.ArrayList;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DirectMessage extends Command{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public DirectMessage(Main plugin) {	super("msg"); this.plugin = plugin; }
	
	static BaseComponent[] syntax = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cBenutze&8: &e/msg <Spieler> <Nachricht>")).create();
//	static BaseComponent[] error_playeroffline = new ComponentBuilder("&c%target ist nicht Online.").create();
//	static BaseComponent[] error_nofriends = new ComponentBuilder("&c%target ist nicht dein Freund.").create();
//	static BaseComponent[] error_selfmessage = new ComponentBuilder("&cDu kannst dir nicht selbst Nachrichten senden.").create();
//	static BaseComponent[] error_notaplayer = new ComponentBuilder("&cDu musst ein Spieler sein um dies zu tun.").create();
//	static BaseComponent[] send = new ComponentBuilder("&aNACHRICHT | &7Du &6➜ &7 %target &a»&7 %msg").create();
//	static BaseComponent[] recieved = new ComponentBuilder("&aNACHRICHT | &7 %sender &6➜ &7Du &a» &7 %msg").create();

	
	static BaseComponent[] error_playeroffline(String target) {	return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&c"+ target + " ist nicht Online.")).create(); }
	static BaseComponent[] error_nofriends(String target) {	return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&c"+ target +" ist nicht dein Freund.")).create(); }
	static BaseComponent[] error_selfmessage(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cDu kannst dir nicht selbst Nachrichten senden.")).create(); }
	static BaseComponent[] error_notaplayer(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cDu musst ein Spieler sein um dies zu tun.")).create(); }

	static BaseComponent[] send(String target, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aNACHRICHT &8|&7 Du &6> &7"+target+"&a:&7 "+msg)).create(); }
	static BaseComponent[] recieved(String sender, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aNACHRICHT &8|&7 "+sender+" &6> &7Du&a:&7 "+msg)).create();	}

	
	public static ArrayList<ProxiedPlayer> placeholder_friendlist = new ArrayList<ProxiedPlayer>();
	
	// /msg <target[player]> <message[string]>
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			
			// remove later
			placeholder_friendlist.add(ProxyServer.getInstance().getPlayer("crackscout"));
			placeholder_friendlist.add(ProxyServer.getInstance().getPlayer("baumirein"));
			
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if(args.length == 0 || args.length == 1) {
				p.sendMessage(syntax);
			}
			if(args.length >= 2) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if(target != p) {
					
					if(placeholder_friendlist.contains(target)) { // check here if befriended
						
						if(target instanceof ProxiedPlayer) {
							
							String msg = "";
							for(int i =1; i < args.length; i++) {
								msg = msg + args[i] + " ";
							}
							
							p.sendMessage(send(target.getDisplayName(), msg));
							target.sendMessage(recieved(p.getDisplayName(), msg));
							
							// save last target and sender names for /r 
							Message.lastmsg.put(target.getName(), p.getName());
						} else {
							p.sendMessage(error_playeroffline(args[0]));
						}
					} else {
						p.sendMessage(error_nofriends(args[0]));
					}
				} else {
					p.sendMessage(error_selfmessage());
				}
			}
		} else {
			sender.sendMessage(error_notaplayer());
		}
	}
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 3, 2025 - 5:19:59 PM
 *
 */