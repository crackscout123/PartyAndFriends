package eu.crackscout.partynfriends;

import eu.crackscout.partynfriends.commands.DirectMessage;
import eu.crackscout.partynfriends.commands.Friends;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	
	@Override
	public void onEnable() {
		initCommands();
	}

	
	void initCommands() {
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new Friends(this));		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new DirectMessage(this));		
	}
	
	
}




/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Jan 23, 2025 - 4:53:26 PM
 *
 */