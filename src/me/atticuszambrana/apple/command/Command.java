package me.atticuszambrana.apple.command;

import org.javacord.api.event.message.MessageCreateEvent;

public abstract class Command {
	
	private String name;
	private String description;
	
	public Command(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Command(String name) {
		this.name = name;
		this.description = "No Description was provided for this command.";
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public abstract void execute(MessageCreateEvent event);
}
