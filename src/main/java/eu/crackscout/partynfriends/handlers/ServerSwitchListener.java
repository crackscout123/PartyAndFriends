package eu.crackscout.partynfriends.handlers;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.Message;
import eu.crackscout.partynfriends.utils.PartyManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerSwitchListener implements Listener {
	
	private Main plugin;

	public ServerSwitchListener(Main plugin) { this.plugin = plugin; }
	
	@EventHandler
	public void onServerSwitchEvent(ServerSwitchEvent e) {
		ProxiedPlayer player = e.getPlayer();
		if(this.plugin.getPartyManager().isPartyLeader(player)) {
			for(ProxiedPlayer member : this.plugin.getPartyManager().getPartyPlayers().get(player).getPlayers()) {
				if(!member.equals(player)) {
					member.connect(player.getServer().getInfo());
					PartyManager.getInstance().sendMessage(member, Message.party_serverSwitched(player.getServer()));
				}
			}
		}
	}
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 19, 2025 - 5:07:11 AM
 *
 */