package eu.crackscout.partynfriends;

import eu.crackscout.partynfriends.commands.FriendsCommand;
import eu.crackscout.partynfriends.commands.PartyCommand;
import eu.crackscout.partynfriends.commands.messaging.DirectMessage;
import eu.crackscout.partynfriends.commands.messaging.PartyMessage;
import eu.crackscout.partynfriends.commands.messaging.ResponseMessage;
import eu.crackscout.partynfriends.handlers.PlayerDisconnectListener;
import eu.crackscout.partynfriends.handlers.ServerSwitchListener;
import eu.crackscout.partynfriends.utils.Message;
import eu.crackscout.partynfriends.utils.PartyManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	
	// Instances 
	private PartyManager partyManager;
	public PartyManager getPartyManager() { return this.partyManager; }
	
	private Message message;
	public Message getMessage() { return this.message; }
	
	void initCommands() {
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new FriendsCommand(this));		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new PartyCommand(this));		
		
		//Message related commands
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new DirectMessage(this));		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new PartyMessage(this));		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new ResponseMessage(this));	
	}
	
	void initListeners(Listener[] listeners) {
		Listener[] arrayOfListener;
		int j = (arrayOfListener = listeners).length;
		for(int i = 0; i < j; i++) {
			Listener listener = arrayOfListener[i];
			ProxyServer.getInstance().getPluginManager().registerListener(this, listener);
		}
	}
	
	private void loadDrivers() {
		try {
		    Class.forName("org.mariadb.jdbc.Driver");
		    System.out.println("[Party & Friends] MariaDB-Treiber erfolgreich geladen.");
		} catch (ClassNotFoundException e) {
		    System.err.println("[Party & Friends] MariaDB-Treiber konnte nicht geladen werden!");
		    e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable() {
		loadDrivers();
		
		initCommands();
		initListeners(new Listener[] {new ServerSwitchListener(this), new PlayerDisconnectListener(this)});
		
		partyManager = new PartyManager();
		message = new Message();
		
	}
	
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Jan 23, 2025 - 4:53:26 PM
 *
 */