package eu.crackscout.partynfriends;

import com.google.inject.Inject;

import org.slf4j.Logger;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(id = "partynfriends",
		name = "Party and Friends",
		version = "0.0.1-SNAPSHOT",
		url = "https://example.org",
		description = "I did it!",
		authors = {"crackscout"}
)


public class Main {
	
    @SuppressWarnings("unused")
	private final ProxyServer server;
    @SuppressWarnings("unused")
	private final Logger logger;
	
    @Inject
	public  Main(ProxyServer server, Logger logger) {
    	this.server = server;
    	this.logger = logger;
    	
    	logger.info("Hello there! I made my first plugin with Velocity.");
    	
    	
	}

}


/** 
 *
 * @author Joel Rzepka - crackscout.de
 *
 * @date Jan 23, 2025 - 4:53:26 PM
 *
 */