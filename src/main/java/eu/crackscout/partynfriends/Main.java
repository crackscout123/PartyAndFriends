package eu.crackscout.partynfriends;

import eu.crackscout.partynfriends.commands.Friends;
import eu.crackscout.partynfriends.commands.PartyCommand;
import eu.crackscout.partynfriends.commands.messaging.DirectMessage;
import eu.crackscout.partynfriends.commands.messaging.PartyMessage;
import eu.crackscout.partynfriends.commands.messaging.ResponseMessage;
import eu.crackscout.partynfriends.utils.Message;
import eu.crackscout.partynfriends.utils.PartyManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	

	
	// Instances 
	private PartyManager partyManager;
	public PartyManager getPartyManager() { return this.partyManager; }
	
	private Message message;
	public Message getMessage() { return this.message; }
	
	
	void initCommands() {
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new Friends(this));		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new PartyCommand(this));		
		
		//Message related commands
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new DirectMessage(this));		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new PartyMessage(this));		
		ProxyServer.getInstance().getPluginManager().registerCommand(this, (Command)new ResponseMessage(this));	
	}
	
	void initListeners() {
		// no Listeners yet
	}
	
	
	
	@Override
	public void onEnable() {
		initCommands();
		initListeners();
		
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