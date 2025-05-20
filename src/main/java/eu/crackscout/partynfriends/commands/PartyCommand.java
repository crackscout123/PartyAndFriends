package eu.crackscout.partynfriends.commands;

import java.io.IOException;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.handlers.Party;
import eu.crackscout.partynfriends.utils.Message;
import eu.crackscout.partynfriends.utils.PartyManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommand extends Command{
	
	private Main plugin;
	
	public PartyCommand(Main plugin) { super("party"); this.plugin = plugin; }

	static BaseComponent[] partyNone = Message.party_noParty();
	static BaseComponent[] partyNoInvite= Message.party_noInvite();
	static BaseComponent[] partyInviteAccepted = Message.party_accepted();
	static BaseComponent[] partyInviteDenied = Message.party_denied();
	static BaseComponent[] noLeader = Message.party_noLeader();

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer)sender;
		if(args.length == 0) {
			Message.getInstance().sendMessage(player, Message.party_help());
			return;
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("list")) {
				if(!this.plugin.getPartyManager().isInParty(player)) {
					PartyManager.getInstance().sendMessage(player, partyNone);
					return;
				}
				Party party = this.plugin.getPartyManager().getPartyPlayers().get(player);
				try {
					PartyManager.getInstance().sendMessage(player, Message.party_listPlayer(party.getPlayers().size()+"/" + party.getMaxSize(party.getOwner())));
				} catch (IOException e) {
					e.printStackTrace();
				}
				for (ProxiedPlayer member : party.getPlayers()) {
					PartyManager.getInstance().sendMessage(player, ChatColor.GRAY+"- " + member.getName());
				}
				return;
			}
			if(args[0].equalsIgnoreCase("accept")) {
				if(!this.plugin.getPartyManager().getInvites().containsKey(player)) {
					PartyManager.getInstance().sendMessage(player, partyNoInvite);
					return;
				}
				Party party = this.plugin.getPartyManager().getInvites().get(player);
				party.addPlayer(player);
				this.plugin.getPartyManager().getInvites().remove(player);
				PartyManager.getInstance().sendMessage(player, partyInviteAccepted);
				return;				
			}
			if(args[0].equalsIgnoreCase("deny")) {
				if(!this.plugin.getPartyManager().getInvites().containsKey(player)) {
					PartyManager.getInstance().sendMessage(player, partyNoInvite);
					return;
				}
				this.plugin.getPartyManager().getInvites().remove(player);
				PartyManager.getInstance().sendMessage(player, partyInviteDenied);
				return;				
			}
			if(args[0].equalsIgnoreCase("leave")) {
				if(!this.plugin.getPartyManager().isInParty(player)) {
					PartyManager.getInstance().sendMessage(player, partyNone);
					return;
				}
				Party party = this.plugin.getPartyManager().getPartyPlayers().get(player);
				party.removePlayer(player);
				return;				
			}
			return;
		}
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("kick")) {
				if(!plugin.getPartyManager().isInParty(player)) {
					PartyManager.getInstance().sendMessage(player, Message.party_notInParty(args[1]));
				}
				if(this.plugin.getPartyManager().isPartyLeader(player)) {
					Party party = this.plugin.getPartyManager().getPartyPlayers().get(player);
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target == null) {
						PartyManager.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
						return;
					}
					if(this.plugin.getPartyManager().isInParty(target)) {
						party.removePlayer(player);
						return;
					}
					PartyManager.getInstance().sendMessage(player, Message.party_notInParty(target.getName()));
					return;
					
				}
				PartyManager.getInstance().sendMessage(player, noLeader);
				return;
			}
			if(args[0].equalsIgnoreCase("invite")) {
				if(!plugin.getPartyManager().isInParty(player)) {
					Party party = new Party(this.plugin, player);
					this.plugin.getPartyManager().getPartyPlayers().put(player, party);	
				}
				if(this.plugin.getPartyManager().isPartyLeader(player)) {
					Party party = this.plugin.getPartyManager().getPartyPlayers().get(player);
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target == null) {
						PartyManager.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
						return;
					}
					if(this.plugin.getPartyManager().isInParty(target)) {
						PartyManager.getInstance().sendMessage(player, Message.party_alreadyInParty(target.getName()));
						return;
					}
					PartyManager.getInstance().sendMessage(player, Message.party_invited(target.getName()));
					party.invitePlayer(target, player);
					return;
				}
				PartyManager.getInstance().sendMessage(player, noLeader);
				return;
			}
			if(args[0].equalsIgnoreCase("promote")) {
				if(!this.plugin.getPartyManager().isInParty(player)) {
					PartyManager.getInstance().sendMessage(player, partyNone);
					return;
				}
				if(this.plugin.getPartyManager().isPartyLeader(player)) {
					Party party = this.plugin.getPartyManager().getPartyPlayers().get(player);
					ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
					if(target == null) {
						PartyManager.getInstance().sendMessage(player, Message.offlinePlayer(args[1]));
						return;
					}
					if(!this.plugin.getPartyManager().isInParty(target)) {
						PartyManager.getInstance().sendMessage(player, Message.party_notInParty(target.getName()));
						return;
					}
					party.setOwner(target);
					party.chatToParty(party, Message.party_newLeader(target.getName()));
					return;
				}
				PartyManager.getInstance().sendMessage(player, noLeader);
				return;
			}
			return;
		}
	}
}

/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 14, 2025 - 2:45:20 AM
 *
 */