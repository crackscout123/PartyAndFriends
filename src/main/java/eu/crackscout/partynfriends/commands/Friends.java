package eu.crackscout.partynfriends.commands;

import eu.crackscout.partynfriends.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Friends extends Command{
	private Main plugin;
	
	public Friends(Main plugin) {
		super("Friends");
		this.plugin = plugin;
	}
	
	@Override
	public void execute(CommandSender s, String[] a) {
		if(s instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) s;
			p.sendMessage(new ComponentBuilder("Hallo Spieler").color(ChatColor.GREEN).create());
		}
		s.sendMessage(new ComponentBuilder("Hallo Console").color(ChatColor.GREEN).create());
	}

}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 3, 2025 - 2:51:15 PM
 *
 */