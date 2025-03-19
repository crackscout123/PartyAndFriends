package eu.crackscout.partynfriends.handlers;

import eu.crackscout.partynfriends.Main;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.event.EventHandler;

public class PlayerConnectListener {
	

	@SuppressWarnings("unused")
	private Main plugin;
	
	public PlayerConnectListener(Main plugin) { this.plugin = plugin;}
	
	@EventHandler
	public void onPlayerHandshakeEvent(PlayerHandshakeEvent e) {
		
	}
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 19, 2025 - 8:25:29 AM
 *
 */