package me.atticuszambrana.apple.command;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.atticuszambrana.apple.AppleBot;
import me.atticuszambrana.apple.command.impl.fun.RandomNameCommand;
import me.atticuszambrana.apple.command.impl.info.HelpCommand;
import me.atticuszambrana.apple.command.impl.marriage.DivorceCommand;
import me.atticuszambrana.apple.command.impl.marriage.MarriageStatusCommand;
import me.atticuszambrana.apple.command.impl.marriage.ProposeCommand;
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
		// Why was this on? It's not even finished
		//commands.add(new RedeemCommand());
		
		// Fun Commands
		commands.add(new RandomNameCommand());
		
		// Marriage Commands
		commands.add(new MarriageStatusCommand());
		commands.add(new ProposeCommand());
		commands.add(new DivorceCommand());
	}

	// This method will be ran when the bot parses that a message has been passed into the server
	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		String prefix = apple.getConfig().getPrefix();
		String input = event.getMessageContent();
		MessageAuthor user = event.getMessageAuthor();
		
		for(Command c : getCommands()) {
			// No it caused more issues then it fixed
			if(input.startsWith(prefix + c.getName())) {
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
