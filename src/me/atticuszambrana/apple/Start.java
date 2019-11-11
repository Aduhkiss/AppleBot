package me.atticuszambrana.apple;

import com.google.gson.Gson;

import me.atticuszambrana.apple.data.Config;
import me.atticuszambrana.apple.util.ConfigReader;

public class Start {
	
	private static Config activeConfig;
	private static AppleBot apple;
	
	public static void main(String[] args) {
		System.out.println("Starting AppleBot...");
		System.out.println("Coded by Atticus Zambrana");
		//TODO: Any arguments? Such as debug mode?
		
		// Get Gson
		Gson g = new Gson();
		
		// Config code
		System.out.println("Looking for a config file...");
		if(ConfigReader.exists()) {
			System.out.println("Found it! Now mounting...");
			activeConfig = g.fromJson(ConfigReader.get(), Config.class);
		} else {
			System.out.println("None was found. Creating a new one from default settings...");
			Config defaultConfig = new Config("put the token here", "put the app id here", "$");
			ConfigReader.write(g.toJson(defaultConfig));
			System.out.println("Done. Now mounting the new config...");
			activeConfig = defaultConfig;
		}
		
		System.out.println("Starting Apple...");
		// Lets turn on Debug mode so we can see debug messages
		apple = new AppleBot(activeConfig, true);
	}
	
	public static AppleBot getApple() {
		return apple;
	}
}
