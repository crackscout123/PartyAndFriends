package eu.crackscout.partynfriends.commands;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.FriendsManager;
import eu.crackscout.partynfriends.utils.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
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
	String spacer = ChatColor.translateAlternateColorCodes('&', "&8\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\n");

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
					if(!FriendsManager.sendFriendRequest(player.getUniqueId().toString(), target.getUniqueId().toString())) { 
						Message.getInstance().sendMessage(player, Message.friends_alreadyRequested(target.getName())); 
						return; 
					}
					Message.getInstance().sendMessage(player, Message.friends_requestSend(target.getName()));
					
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
					this.invites.put(target.getName(), player.getName());
					return;
				}
				if(args.length == 3) {
					Message.getInstance().sendMessage(player, Message.friends_syntax());
					return;
				}
				return;
			}
			if(args[0].equalsIgnoreCase("accept")) {
				if(args.length == 1) { // /friend accept 
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(this.invites.get(player.getName()));
					if(this.invites.containsKey(player.getName())) {
						if(target != null) {
							Message.getInstance().sendMessage(player, Message.friends_requestAccptedSend(target.getName()));
							Message.getInstance().sendMessage(target, Message.friends_requestAccpetedRecived(player.getName()));
							this.invites.remove(player.getName());
							FriendsManager.acceptFriendRequest(player.getUniqueId().toString(), target.getUniqueId().toString()); //TODO: richtige reinfolge überprüfen
							return;
						}
						Message.getInstance().sendMessage(player, Message.offlinePlayer(""));
						return;
					}
					Message.getInstance().sendMessage(player, Message.friends_noRequests());
					return;
				}
				if(args.length == 2) { // /friend accept <spieler>
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target != null) {
						if(FriendsManager.areFriends(player.getUniqueId().toString(), target.getUniqueId().toString())) {
							Message.getInstance().sendMessage(player, Message.friends_alreadyFriends(target.getName()));	
							return;
						}
						if(FriendsManager.getOpenFriendRequests(player.getUniqueId().toString()).contains(target.getUniqueId().toString())) {
							Message.getInstance().sendMessage(player, Message.friends_requestAccptedSend(target.getName()));
							Message.getInstance().sendMessage(target, Message.friends_requestAccpetedRecived(player.getName()));
							FriendsManager.acceptFriendRequest(player.getUniqueId().toString(), target.getUniqueId().toString()); //TODO: richtige reinfolge überprüfen
							return;
						}
						Message.getInstance().sendMessage(player, Message.friends_noRequests());		
						return;
					}
					Message.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
					return;
				}
				Message.getInstance().sendMessage(player, Message.friends_syntax());
				return;
			}
			if(args[0].equalsIgnoreCase("ignore")) {
				if(args.length == 1) { // /friend ignore 
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(this.invites.get(player.getName()));
					if(this.invites.containsKey(player.getName())) {
						if(target != null) {
							Message.getInstance().sendMessage(player, Message.friends_requestIgnored(target.getName()));
							this.invites.remove(player.getName());
							return;
						}
						Message.getInstance().sendMessage(player, Message.offlinePlayer(""));
						return;
					}
					Message.getInstance().sendMessage(player, Message.friends_noRequests());
					return;
				}
				if(args.length == 2) { // /friend ignore <spieler>
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target != null) {
						if(FriendsManager.getOpenFriendRequests(player.getUniqueId().toString()).contains(target.getUniqueId().toString())) {
							Message.getInstance().sendMessage(player, Message.friends_requestIgnored(target.getName()));
							return;
						}
						Message.getInstance().sendMessage(player, Message.friends_noRequests());		
						return;
					}
					Message.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
					return;
				}
				Message.getInstance().sendMessage(player, Message.friends_syntax());
				return;
			}
			if(args[0].equalsIgnoreCase("deny")) {
				if(args.length == 1) { // /friend deny 
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(this.invites.get(player.getName()));
					if(this.invites.containsKey(player.getName())) {
						if(target != null) {
							if(FriendsManager.deleteFriendRequest(player.getUniqueId().toString(), target.getUniqueId().toString())) {
								Message.getInstance().sendMessage(player, Message.friends_requestDeniedSend(target.getName()));
								Message.getInstance().sendMessage(target, Message.friends_requestDeniedRecived(player.getName()));
								this.invites.remove(player.getName());
								return;
							}
						}
						Message.getInstance().sendMessage(player, Message.offlinePlayer(""));
						return;
					}
					Message.getInstance().sendMessage(player, Message.friends_noRequests());
					return;
				}
				if(args.length == 2) { // /friend deny <spieler>
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target != null) {
						if(FriendsManager.areFriends(player.getUniqueId().toString(), target.getUniqueId().toString())) {
							if(FriendsManager.deleteFriendRequest(player.getUniqueId().toString(), target.getUniqueId().toString())) {
								Message.getInstance().sendMessage(player, Message.friends_requestDeniedSend(target.getName()));
								Message.getInstance().sendMessage(target, Message.friends_requestDeniedRecived(player.getName()));
								return;
							}
							return;
						}
						Message.getInstance().sendMessage(player, Message.friends_noRequests());		
						return;
					}
					Message.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
					return;
				}
				Message.getInstance().sendMessage(player, Message.friends_syntax());
				return;
			}
			if(args[0].equalsIgnoreCase("remove")) {
				if(args.length == 2) { // /friend remove <spieler>
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target != null) {
						if(FriendsManager.areFriends(player.getUniqueId().toString(), target.getUniqueId().toString())) {
							if(FriendsManager.removeFriend(player.getUniqueId().toString(), target.getUniqueId().toString())) {
								Message.getInstance().sendMessage(player, Message.friends_disbanned(target.getName()));
								Message.getInstance().sendMessage(target, Message.friends_disbanned(player.getName()));
								return;
							}
							return;
						}
						Message.getInstance().sendMessage(player, Message.friends_notBefriended(target.getName()));		
						return;
					}
					Message.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
					return;
				}
				Message.getInstance().sendMessage(player, Message.friends_syntax());
				return;
			}
			if(args[0].equalsIgnoreCase("join")) { 
				if(args.length == 2) { // friend join <spieler>
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target != null) {
						if(FriendsManager.areFriends(player.getUniqueId().toString(), target.getUniqueId().toString())) {
							ServerInfo server = target.getServer().getInfo();
							if(player.getServer().getInfo() != server) {
								player.connect(server);
								// maybe add message ? (connection to ...)
								return;
							}
							// maybe add message ? (already same server)
							return;
						}
						Message.getInstance().sendMessage(player, Message.friends_notBefriended(target.getName()));		
						return;
					}
					Message.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
					return;
				}
				Message.getInstance().sendMessage(player, Message.friends_syntax());
				return;
			}
			if(args[0].equalsIgnoreCase("list")) {

				if(args.length == 1) { // friend list 
					List<String> freunde = FriendsManager.getFriends(player.getUniqueId().toString());
					int size = freunde.size();
					if(size == 0) {
						Message.getInstance().sendMessage(player, Message.friends_noFriends());
						return;
					}
					Message.getInstance().sendMessage(player, spacer);
					Message.getInstance().sendMessage(player, Message.friends_friendList(size+""));
					for (String freund : freunde) {
						ProxiedPlayer proxiedFriend = ProxyServer.getInstance().getPlayer(UUID.fromString(freund));
						if(proxiedFriend.isConnected()) {
							TextComponent onlineOn = Message.getInstance().createComponent(Message.friends_onlineOn(proxiedFriend.getServer()), "/friend join " + proxiedFriend.getName());
							TextComponent remove = Message.getInstance().createComponent(Message.friends_REMOVE(), "/friend remove " + proxiedFriend.getName());
							TextComponent tc = new TextComponent(spacer);
							
							tc.addExtra(ChatColor.GRAY + proxiedFriend.getName() + "\n");
							tc.addExtra("");
							tc.addExtra(onlineOn);
							tc.addExtra(ChatColor.GRAY + " - ");
							tc.addExtra(remove);
							
							Message.getInstance().sendMessage(player, tc);
						} else {
							TextComponent offline = Message.getInstance().createComponent(Message.friends_OFFLINETAG(), "");
							TextComponent remove = Message.getInstance().createComponent(Message.friends_REMOVE(), "/friend remove " + proxiedFriend.getName());
							TextComponent tc = new TextComponent(spacer);
							
							tc.addExtra(ChatColor.GRAY + proxiedFriend.getName() + "\n");
							tc.addExtra("");
							tc.addExtra(offline);
							tc.addExtra(ChatColor.GRAY + " - ");
							tc.addExtra(remove);
							
							Message.getInstance().sendMessage(player, tc);
						}
					}
					return;
				}
				Message.getInstance().sendMessage(player, Message.friends_syntax());
				return;
			}
			return;
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