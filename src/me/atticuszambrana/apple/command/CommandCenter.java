package me.atticuszambrana.apple.command;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.atticuszambrana.apple.AppleBot;
import me.atticuszambrana.apple.command.impl.info.HelpCommand;
import me.atticuszambrana.apple.command.impl.marriage.MarriageStatusCommand;
import me.atticuszambrana.apple.command.impl.marriage.ProposeCommand;
import me.atticuszambrana.apple.command.impl.membership.RedeemCommand;
import me.atticuszambrana.apple.util.LogUtil;

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
		
		// Information Commands
		commands.add(new HelpCommand());
		
		// Membership Commands
		commands.add(new RedeemCommand());
		
		// Marriage Commands
		commands.add(new MarriageStatusCommand());
		commands.add(new ProposeCommand());
	}

	// This method will be ran when the bot parses that a message has been passed into the server
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		String prefix = apple.getConfig().getPrefix();
		String input = event.getMessageContent();
		MessageAuthor user = event.getMessageAuthor();
		
		for(Command c : getCommands()) {
			if(input.startsWith(prefix + c.getName())) {
				//TODO: Fix the command detection system to not fuck stuff up
				// Log the execution of a command
				LogUtil.logCommand(user.getName() + " executed command " + c.getName());
				c.execute(event);
			}
		}
		
		// Also maybe log messages? or no
	}
	
	public List<Command> getCommands() {
		return commands;
	}
}
