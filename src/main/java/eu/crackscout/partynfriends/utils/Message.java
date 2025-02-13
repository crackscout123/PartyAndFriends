package eu.crackscout.partynfriends.utils;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Message {
	
	private static Message instance;
	

	public static HashMap<String, String> lastmsg = new HashMap<String, String>();

	static BaseComponent send(BaseComponent msg) {
		return new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', msg.toString())).build();
	}
	
	
	
	
	
	
	
	
	
	public static Message getInstance() {
		if(instance == null) {
			instance = new Message();
		}
		return instance;
	}
	
}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Feb 13, 2025 - 12:50:09 PM
 *
 */