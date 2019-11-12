package me.atticuszambrana.apple;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

import me.atticuszambrana.apple.command.CommandCenter;
import me.atticuszambrana.apple.data.Config;
import me.atticuszambrana.apple.database.Database;
import me.atticuszambrana.apple.modules.marriage.MarriageManager;
import me.atticuszambrana.apple.util.AtticusThread;
import me.atticuszambrana.apple.util.LogUtil;

public class AppleBot {
	
	private DiscordApi api;
	private Config config;
	private boolean debugMode;
	
	private CommandCenter commandCenter;
	
	private Database database;
	
	private int activitySlide = 0;
	
	public AppleBot(Config config, boolean debugMode) {
		
		LogUtil.log("Starting Command Center...");
		commandCenter = new CommandCenter(this);
		
		LogUtil.log("Getting everything ready...");
		this.config = config;
		this.debugMode = debugMode;
		api = new DiscordApiBuilder().setToken(config.getToken()).login().join();
		
		LogUtil.log("Setting up the Database...");
		database = new Database(this);
		
		// Register the message handlers
		api.addMessageCreateListener(commandCenter);
		
		// Then we want to give the user a URL to add the bot to their server
		LogUtil.log("You may invite this bot into your server with this link: " + api.createBotInvite());
		
		updateActivity();
		
		LogUtil.log("Updating Marriages...");
		MarriageManager.updateMarriages();
	}
	
	private void updateActivity() {
		AtticusThread t = new AtticusThread() {
			public void run() {
				Thread.currentThread().setName("Activity Switch Thread");
				String prefix = getConfig().getPrefix();
				
				if(activitySlide == 0) {
					getAPI().updateActivity(ActivityType.PLAYING, prefix + "help for commands.");
					activitySlide++;
				} else
				if(activitySlide == 1) {
					getAPI().updateActivity(ActivityType.PLAYING, "Hello World!");
					activitySlide++;
				} else
				if(activitySlide == 2) {
					getAPI().updateActivity(ActivityType.PLAYING, "Minecraft");
					activitySlide = 0;
				} else {
					System.out.println("umm what.");
				}
				
				try {
					Thread.sleep((1000 * 60));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				updateActivity();		
				return;
			}
		};
		t.start();
	}
	
	public DiscordApi getAPI() {
		return api;
	}
	
	public Config getConfig() {
		return config;
	}
	
	public boolean isDebug() {
		return debugMode;
	}
	public CommandCenter getCommandCenter() {
		return commandCenter;
	}
	public Database getDatabase() {
		return database;
	}
}
