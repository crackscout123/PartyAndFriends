package eu.crackscout.partynfriends.commands.messaging;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.Message;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ResponseMessage extends Command{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public ResponseMessage(Main plugin) { super("r"); this.plugin = plugin; }

	// /r <message[string]>
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
				
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if(args.length == 0) {
				p.sendMessage(Message.dmr_syntax());
			}
			
			if(args.length >= 1) {
				// grab last target of player executing this command
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(Message.lastmsg.get(p.getName()));
				if(target != p) {
					
					if(DirectMessage.placeholder_friendlist.contains(target)) { // check here if befriended - TODO: implement when friend system is done
					
						if(target instanceof ProxiedPlayer) {
							String msg = "";
							for(int i =0; i < args.length; i++) {
								msg = msg + args[i] + " ";
							}
							p.sendMessage(Message.dm_msgSend(target.getDisplayName(), msg));
							target.sendMessage(Message.dm_msgRecieved(p.getDisplayName(), msg));
						} else {
							p.sendMessage(Message.offlinePlayer(args[0]));
						}
					} else {
						p.sendMessage(Message.dm_nofriends(args[0]));
					}
				} else {
					p.sendMessage(Message.dm_selfmessage());
				}
			} else {
				p.sendMessage(Message.dmr_syntax());
			}
		} else {
			sender.sendMessage(Message.dm_notaplayer());
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