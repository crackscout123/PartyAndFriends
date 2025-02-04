package eu.crackscout.partynfriends.commands;


import eu.crackscout.partynfriends.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DirectMessage extends Command{
	private Main plugin;
	
	public DirectMessage(Main plugin) {	super("DirectMessage"); this.plugin = plugin; }
	
	
	static BaseComponent[] syntax = new ComponentBuilder("§aNACHRICHT | §7Du §6➜ §7 %target §a»§7 %msg").create();
	static BaseComponent[] send_sender = new ComponentBuilder("msg <target[player]> <message[string]>").color(ChatColor.RED).create();
	
	
	
	// /msg <target[player]> <message[string]>
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if(args.length == 0 || args.length == 1) {
				p.sendMessage(syntax);
			}
			if(args.length >= 2) {
				
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if(target != p) {
					if(target != null) {
						String msg = "";
						for(int i =1; i < args.length; i++) {
							msg = msg + args[i] + " ";
						}
						p.sendMessage(send_sender);
						target.sendMessage("§aNACHRICHT | §7" + p.getName() + " §6➜ §7Du §a» §7" + msg);
						
						
					} else {
						p.sendMessage("§c" + target.getName() + " ist nicht dein Freund.");
					}
				} else {
					p.sendMessage("§cDu kannst dir nicht selbst Nachrichten senden.");
				}
			}
		} else {
			sender.sendMessage("§cDu musst ein Spieler sein um dies zu tun.");
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