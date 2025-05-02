package eu.crackscout.partynfriends.commands.messaging;

import java.util.ArrayList;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DirectMessage extends Command{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public DirectMessage(Main plugin) {	super("msg"); this.plugin = plugin; }

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
				p.sendMessage(Message.dm_syntax());
			}
			if(args.length >= 2) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if(target != p) {
					
					if(placeholder_friendlist.contains(target)) { // check here if befriended - TODO: implement when friend system is done
						
						if(target instanceof ProxiedPlayer) {
							String msg = "";
							for(int i =1; i < args.length; i++) {
								msg = msg + args[i] + " ";
							}
							
							p.sendMessage(Message.dm_msgSend(target.getDisplayName(), msg));
							target.sendMessage(Message.dm_msgRecieved(p.getDisplayName(), msg));
							
							// save last target and sender names for /r 
							Message.lastmsg.put(target.getName(), p.getName());
						} else {
							p.sendMessage(Message.offlinePlayer(args[0]));
						}
					} else {
						p.sendMessage(Message.dm_nofriends(args[0]));
					}
				} else {
					sender.sendMessage(Message.dm_selfmessage());
				}
			}
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