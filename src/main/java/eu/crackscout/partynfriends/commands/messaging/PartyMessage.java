package eu.crackscout.partynfriends.commands.messaging;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.handlers.Party;
import eu.crackscout.partynfriends.utils.Message;
import eu.crackscout.partynfriends.utils.PartyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyMessage extends Command{

	private Main plugin;
	
	public PartyMessage(Main plugin) { super("p"); this.plugin = plugin; }

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer)sender;
		if(!this.plugin.getPartyManager().isInParty(player)) {
			PartyManager.getInstance().sendMessage(player, Message.party_noParty());
			return;
		}
		if (args.length == 0) {
			PartyManager.getInstance().sendMessage(player, Message.enterMsg());
			return;
		}
		Party party = this.plugin.getPartyManager().getPartyPlayers().get(player);
		
		String message = "";
		for(int i = 0; i < args.length; i++) {
			message = message + args[i] + " ";
		}
		for(ProxiedPlayer members : party.getPlayers()) {
			if(members != player) {
				this.plugin.getPartyManager().sendMessage(members, Message.party_msgRecieved(members.getName(), message));
			}
		}
		this.plugin.getPartyManager().sendMessage(player, Message.party_msgSend(message));
	}
}

/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 11, 2025 - 6:07:31 AM
 *
 */