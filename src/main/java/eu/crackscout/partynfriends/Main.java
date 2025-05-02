package eu.crackscout.partynfriends;

import java.io.File;
import java.io.IOException;

import eu.crackscout.partynfriends.commands.FriendsCommand;
import eu.crackscout.partynfriends.commands.PartyCommand;
import eu.crackscout.partynfriends.commands.messaging.DirectMessage;
import eu.crackscout.partynfriends.commands.messaging.PartyMessage;
import eu.crackscout.partynfriends.commands.messaging.ResponseMessage;
import eu.crackscout.partynfriends.handlers.PlayerDisconnectListener;
import eu.crackscout.partynfriends.handlers.ServerSwitchListener;
import eu.crackscout.partynfriends.utils.FileManager;
import eu.crackscout.partynfriends.utils.Message;
import eu.crackscout.partynfriends.utils.PartyManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {

    @SuppressWarnings("unused")
	private static Main instance;
	
	private File langFile = new File(getDataFolder()+"/lang/default.yml");
    private Configuration langConfig;
    
	public File langFile() { return langFile; }
	public Configuration getLangConfig() throws IOException { langConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(langFile); return langConfig; }
	
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
	
	private void createDefaultConfigs() {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
			FileManager fileManager = new FileManager(this, this.langFile);
			fileManager.createDefaults();
		}
		return;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		loadDrivers();
		
		createDefaultConfigs();
		
		try {
			Message.init(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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