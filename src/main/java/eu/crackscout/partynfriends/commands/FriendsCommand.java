package eu.crackscout.partynfriends.commands;

import java.util.HashMap;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.FriendsManager;
import eu.crackscout.partynfriends.utils.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FriendsCommand extends Command{
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public FriendsCommand(Main plugin) { 
		
		super("Friend"); 
		
		this.plugin = plugin; 
		this.invites = new HashMap<String, String>();
		this.names = new HashMap<String, String>();
	}
	
	HashMap<String, String> invites; HashMap<String, String> names;
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer)sender;
			if(args.length == 0) {
				Message.getInstance().sendMessage(player, Message.friends_help());
				return;
			}
			if(args[0].equalsIgnoreCase("add")) {
				if(args.length == 1) {
					Message.getInstance().sendMessage(player, Message.friends_syntax());
					return;
				}
				if(args.length == 2) {
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target == null) {
						Message.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
						return;
					}
					if(target == player) {
						Message.getInstance().sendMessage(player, Message.friends_selfRequest());
						return;
					}
					if(FriendsManager.areFriends(player.getUniqueId().toString(), target.getUniqueId().toString())) {
						Message.getInstance().sendMessage(player, Message.friends_alreadyFriends(target.getName()));
						return;
					}
					if(!FriendsManager.sendFriendRequest(player.getUniqueId().toString(), target.getUniqueId().toString())) { Message.getInstance().sendMessage(player, Message.friends_alreadyRequested(target.getName())); return; }
					Message.getInstance().sendMessage(player, Message.friends_requestSend(target.getName()));
					
					String spacer = ChatColor.translateAlternateColorCodes('&', "&8\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\n");
					Message.getInstance().sendMessage(target, Message.friends_requestRecived(player.getName()));

					TextComponent acceptTc = Message.getInstance().createComponent(Message.friends_ACCEPT(), "/friend accept");
					TextComponent denyTc = Message.getInstance().createComponent(Message.friends_DENY(), "/friend deny");
					TextComponent ignoreTc = Message.getInstance().createComponent(Message.friends_IGNORE(), "/friend ignore");
					TextComponent tc = new TextComponent(spacer);
					tc.addExtra(acceptTc);
					tc.addExtra(ChatColor.GRAY + " - ");
					tc.addExtra(denyTc);
					tc.addExtra(ChatColor.GRAY + " - ");
					tc.addExtra(ignoreTc);
					
					Message.getInstance().sendMessage(target, tc);
				}
			}
		}
		return;
	}

}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 3, 2025 - 2:51:15 PM
 *
 */