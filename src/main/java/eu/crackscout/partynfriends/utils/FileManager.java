package eu.crackscout.partynfriends.utils;

import java.io.File;
import java.io.IOException;

import eu.crackscout.partynfriends.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class FileManager {
	private Main plugin;
	private final File langFile;
	private final File confFile;

	// public Configuration getConfig() { return config; }
	
	public FileManager(Main plugin, File langFile, File confFile) {
		this.plugin = plugin;
		this.langFile = langFile;
		this.confFile = confFile;
    }
		
    public void createDefaults() {
        try {
            // Erstelle Plugin-Ordner
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            
            // Erstelle lang-Ordner
            File langDir = new File(plugin.getDataFolder(), "lang");
            if (!langDir.exists()) {
                langDir.mkdir();
            }

            // CONFIG.YML
            if (!confFile.exists()) {
                Configuration config = new Configuration();
                config.set("database.type", "sqlite");
                config.set("database.mysql.url", "jdbc:mysql://localhost:3306/mydb");
                config.set("database.mysql.user", "demo");
                config.set("database.mysql.password", "password");
                config.set("database.sqlite.file", "database.db");
                config.set("values.parties.size.placeholderONE", 10);
                config.set("values.parties.size.placeholderTWO", 15);
                config.set("values.parties.size.placeholderTHREE", 20);
                //TODO: maybe make it a catch so the user can add unlimited types of "ranks" 
                /*
                 * maybe something like
                 * 
                 * */
                saveConfig(config, confFile);
            }

            // LANG/DEFAULT.YML
            if (!langFile.exists()) {
                Configuration config = new Configuration();
				
				//----------------------------------------------GENERAL MESSAGES---------------------------------------------------------//
				config.set("general.offlinePlayer", "&7Der Spieler&e %player% &7ist &cnicht &conline&7.");
				config.set("general.enterMessage", "&7Bitte gebe &ceine &cNachricht &7ein.");
				config.set("general.dateFormat", "dd.MM.yyyy - HH:mm");
				
				//----------------------------------------------DIRECT MESSAGES---------------------------------------------------------//
				config.set("directmessage.syntax", "&cBenutze&8: &e/msg <Spieler> <Nachricht>");
				config.set("directmessage.responseSyntax", "&cBenutze&8: &e/r <Nachricht>");
				
				config.set("directmessage.nofriends", "&c%target% ist nicht dein Freund.");
				config.set("directmessage.selfmessage", "&cDu kannst dir nicht selbst Nachrichten senden.");
				config.set("directmessage.notaplayer", "&cDu musst ein Spieler sein um dies zu tun.");
				
				config.set("directmessage.chat.send", "&aNACHRICHT &8|&7 Du &6> &7%target%&a:&7 ");
				config.set("directmessage.chat.recieved", "&aNACHRICHT &8|&7 %sender% &6> &7Du&a:&7 ");
				
				//----------------------------------------------PARTY MESSAGES---------------------------------------------------------//
				config.set("party.help", "&7\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\n"
						+ "&7\u2588 &6/party invite <spieler>&8- &7Lade jemanden zur Party ein\n"
						+ "&7\u2588 &6/party kick <spieler>&8- &7Entferne einen Spieler aus der Party\n"
						+ "&7\u2588 &6/party promote <spieler>8- &7Ernenne eine Spieler zum Partyleader\n"
						+ "&7\u2588 &6/party leave &8- &7Verlasse die Party\n"
						+ "&7\u2588 &6/party accept &8- &7Akzeptiere eine Einladung\n"
						+ "&7\u2588 &6/party deny &8- &7Lehene eine Einladung ab\n"
						+ "&7\u2588 &6/party list &8- &7Zeige die Mitglieder der Party an\n"
						+ "&7\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588");
				
				config.set("party.listPlayer", "&7Folgende Spieler befinden sich in der Party: [%playercount%]");
				config.set("party.noInvite", "&7Du hast leider &ckeine Partyanfrage &7bekommen.");
				config.set("party.inviteAccepted", "&7Du hast die Anfrage &aangenommen&7.");
				config.set("party.inviteDenied", "&7Du hast die Anfrage &cabgelehnt&7.");
				config.set("party.notInParty", "&7Du befindest dich &7in &ckeiner Party&7.");
				config.set("party.alreadyInParty", "&7Der Spieler &e%player% &7ist bereits in &ceiner anderen Party&7.");
				config.set("party.invited", "&7Du hast den Spieler &e%target% &aeingeladen&7.");
				config.set("party.noLeader", "&7Du bist &ckein Party-Leader&7.");
				config.set("party.notInYourParty", "&7Der Spieler &e%target% &7ist &cnicht &7in deiner Party.");
				config.set("party.newLeader", "&e%target% &7ist nun der &aParty-Leader&7.");
				config.set("party.targetInvited", "&7Der Spieler &e%target% &7hat dich in seine Party &aeingeladen&7.");
				config.set("party.maxPlayer", "&7Du hast die &cmaximale Anzahl &7an Partymitgliedern erreicht.");
				config.set("party.playerJoined", "&e%player%&7 ist der Party beigetreten!");
				config.set("party.playerLeft", "&7Der Spieler&e %player% &7hat die Party &cverlassen&7.");
				config.set("party.disbanned", "&7Die Party wurde &caufgelˆst.");
				
				config.set("party.serverSwitched", "&7Die Party hat den Server &e%server% &abetreten&7.");
				
				config.set("party.button.accept", "&2&l[&a&lAKZEPTIEREN&2&l]");
				config.set("party.button.deny", "&4&l[&c&lABLEHNEN&4&l]");
				
				config.set("party.chat.send", "&cPARTY &8|&7 Du &6> &7Party&a:&7 ");
				config.set("party.chat.recieved", "&cPARTY &8|&7 %sender% &6> &7Du&a:&7 ");
				
				//----------------------------------------------FRIENDS MESSAGES---------------------------------------------------------//
				config.set("friends.prefix", "&8[&cFreunde&8] ");
				config.set("friends.syntax", "&cBenutze&8: &e/friend ");
				
				config.set("friends.help", "&7\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\n"
						+ "&7\u2588 &6/friend add <spieler>&8- &7Adde einen Freund\n"
						+ "&7\u2588 &6/friend remove <spieler>&8- &7Remove einen Freund\n"
						+ "&7\u2588 &6/friend join <spieler>&8- &7Springe zum Server eines Freundes\n"
						+ "&7\u2588 &6/friend accept [spieler]&8- &7Akzeptiere eine Anfrage\n"
						+ "&7\u2588 &6/friend deny [spieler]&8- &7Lehene eine Anfrage ab\n"
						+ "&7\u2588 &6/friend ignore [spieler]&8- &7Ignoriere eine Anfrage\n"
						+ "&7\u2588 &6/friend list &8- &7Zeige die Freundesliste\n"
						+ "&7\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588");
				
				config.set("friends.notBefriended", "&c%target% ist nicht dein Freund.");
				config.set("friends.requestSend", "&7Du hast &a%player% &7eine Anfrage gesendet.");
				config.set("friends.requestRecived", "&7Du haste eine Anfrage von &a%player% &7bekommen!");
				config.set("friends.alreadyFriends", "&c%player% &7ist bereits dein Freund.");
				config.set("friends.selfRequest", "&cDu kannst dich nicht selber als Fruend hinzuf√ºgen.");
				config.set("friends.noRequests", "&cDu hast momentan keine Anfragen.");
				config.set("friends.alreadyRequested", "&cDu hast &e%player% &cbereits eine Anfrage gesendet.");
				config.set("friends.requestAcceptedSend", "&7Du hast die Anfrage von &e%player%&7 angenommen.");
				config.set("friends.requestAcceptedRecived", "&e%player% &7hat deine Anfrage angenommen.");
				config.set("friends.requestIgnored", "&7Du ignoriest die Anfrage von &e%player%&7.");
				config.set("friends.requestDeniedSend", "&7Du hast die Anfrage von &e%player%&7 abgelehnt.");
				config.set("friends.requestDeniedRecived", "&e%player% &7hat deine Anfrage abgelehnt.");
				config.set("friends.disbanned", "&7Du bist nun kein Freund mehr von &e%player%.");
				config.set("friends.friendList", "&6&lFreundes Liste &7(&eAnzahl von Freunden&8: &e%size%&7)&e&l:");
				config.set("friends.noFriends", "&7&oDu hast keine Freunde.");
				
	
				config.set("friends.button.accept", "&2&l[&a&lAKZEPTIEREN&2&l]");
				config.set("friends.button.deny", "&4&l[&c&lABLEHNEN&4&l]");
				config.set("friends.button.ignore", "&6&l[&e&lIGNORIEREN&6&l]");
				config.set("friends.button.remove", "&4[&cENTFERNEN&4]");
				config.set("friends.button.offline", "&6[&eOFFLINE&6]");
				config.set("friends.button.onlineOn", "&2[&aOnline auf %server%&2]");
				config.set("friends.button.lastseen", "&2[&aZuletzt: %lastseen%&2]");
				
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, langFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private void saveConfig(Configuration config, File file) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
    }
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Mar 27, 2025 - 2:27:46 AM
 *
 */