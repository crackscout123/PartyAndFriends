package eu.crackscout.partynfriends.handlers;

import eu.crackscout.partynfriends.Main;
import eu.crackscout.partynfriends.utils.FriendsManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {
	
	private Main plugin;
	
	public PlayerDisconnectListener(Main plugin) { this.plugin = plugin;}
	
	@EventHandler
	public void onPlayerDisconnectEvent(PlayerDisconnectEvent e) {
		ProxiedPlayer player = e.getPlayer();
		if(this.plugin.getPartyManager().isInParty(player)) {
			this.plugin.getPartyManager().getPartyPlayers().get(player).removePlayer(player);
		}
		FriendsManager.updateLastSeen(player.getUniqueId().toString());
	}

}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 19, 2025 - 5:07:01 AM
 *
 */