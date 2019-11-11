package me.atticuszambrana.apple.command;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.atticuszambrana.apple.AppleBot;
import me.atticuszambrana.apple.command.impl.info.HelpCommand;

public class CommandCenter implements MessageCreateListener {
	/*
	 * Command Center System
	 * The system that intercepts commands ran by users, and runs tasks with the code
	 */
	
	List<Command> commands = new ArrayList<Command>();
	
	private AppleBot apple;
	
	public CommandCenter(AppleBot apple) {
		this.apple = apple;
		register();
	}
	
	public void register() {
		// Register commands here
		commands.add(new HelpCommand());
	}

	// This method will be ran when the bot parses that a message has been passed into the server
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		String prefix = apple.getConfig().getPrefix();
		String input = event.getMessageContent();
		
		for(Command c : getCommands()) {
			if(input.startsWith(prefix + c.getName())) {
				// Log the execution of a command
				c.execute(event);
			}
		}
		
		// Also maybe log messages? or no
	}
	
	public List<Command> getCommands() {
		return commands;
	}
}
