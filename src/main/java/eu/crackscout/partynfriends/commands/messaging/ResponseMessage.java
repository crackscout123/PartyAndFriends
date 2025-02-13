package eu.crackscout.partynfriends.commands.messaging;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ResponseMessage extends Command{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ResponseMessage(Main plugin) { super("r"); this.plugin = plugin; }
	
	
	static BaseComponent[] syntax = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cBenutze&8: &e/r <Nachricht>")).create();
	
	static BaseComponent[] error_playeroffline(String target) {	return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&c"+ target + " ist nicht Online.")).create(); }
	static BaseComponent[] error_nofriends(String target) {	return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&c"+ target +" ist nicht dein Freund.")).create(); }
	static BaseComponent[] error_selfmessage(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cDu kannst dir nicht selbst Nachrichten senden.")).create(); }
	static BaseComponent[] error_notaplayer(){ return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cDu musst ein Spieler sein um dies zu tun.")).create(); }

	static BaseComponent[] send(String target, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aNACHRICHT &8|&7 Du &6> &7"+target+"&a:&7 "+msg)).create(); }
	static BaseComponent[] recieved(String sender, String msg) { return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&aNACHRICHT &8|&7 "+sender+" &6> &7Du&a:&7 "+msg)).create();	}

	// /r <message[string]>
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
				
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if(args.length == 0) {
				p.sendMessage(syntax);
			}
			if(args.length >= 1) {
				// grab last target of player executing this command
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(Message.lastmsg.get(p.getName()));
				if(target != p) {
					if(DirectMessage.placeholder_friendlist.contains(target)) { // check if befriended
					
						if(target instanceof ProxiedPlayer) {
							
							String msg = "";
							for(int i =0; i < args.length; i++) {
								msg = msg + args[i] + " ";
							}
							
							p.sendMessage(send(target.getDisplayName(), msg));
							target.sendMessage(recieved(p.getDisplayName(), msg));
						} else {
							p.sendMessage(error_playeroffline(args[0]));
						}
						
					} else {
						p.sendMessage(error_nofriends(args[0]));
					}
					
				} else {
					p.sendMessage(error_selfmessage());
				}
				
			} else {
				p.sendMessage(syntax);
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
 * @date Feb 11, 2025 - 6:03:02 AM
 *
 */