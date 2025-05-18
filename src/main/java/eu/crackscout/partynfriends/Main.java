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
import eu.crackscout.partynfriends.utils.DatabaseManager;
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
	private File confFile = new File(getDataFolder()+"/config.yml");
    private Configuration langConfig;
    private Configuration confConfig;
    
	public File langFile() { return langFile; }
	public File confFile() { return confFile; }
	public Configuration getLangConfig() throws IOException { langConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(langFile); return langConfig; }
	public Configuration getConfConfig() throws IOException { confConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(confFile); return confConfig; }
	
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
		    Class.forName("org.sqlite.JDBC");
		    System.out.println("[Party & Friends] Alle Treiber erfolgreich geladen.");
		} catch (ClassNotFoundException e) {
		    System.err.println("[Party & Friends] Treiber konnte nicht geladen werden!");
		    e.printStackTrace();
		}
	}
	
	private void createDefaultConfigs() {
		String dbType = "sqlite"; //default in case config is broken

		FileManager fileManager = new FileManager(this,this.langFile,this.confFile);
		fileManager.createDefaults();
		
    try {
      confConfig = getConfConfig();
      langConfig = getLangConfig();

      Configuration cfg = this.getConfConfig();
      dbType = ("sqlite".equals(cfg.getString("database.type")) || "mysql".equals(cfg.getString("database.type"))) ? cfg.getString("database.type") : "sqlite";
      DatabaseManager.init(this, dbType);
    } catch (IOException e1) {
      e1.printStackTrace();
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
	
	@Override
	public void onDisable() {
		DatabaseManager.closeConnection();
	}

}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Jan 23, 2025 - 4:53:26 PM
 *
 */