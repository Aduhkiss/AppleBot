package me.atticuszambrana.apple.command.impl.info;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.command.Command;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help", "See all the commands you can use.");
	}

	@Override
	public void execute(MessageCreateEvent event) {
		String prefix = Start.getApple().getConfig().getPrefix();
		
		EmbedBuilder pub = new EmbedBuilder();
		
		pub.setColor(Color.GREEN);
		pub.setTitle("I sent you the commands!");
		pub.setDescription("A list of all of my commands have been direct messaged to you!");
		
		event.getChannel().sendMessage(pub);
		
		EmbedBuilder start = new EmbedBuilder();
		start.addField("My Prefix", prefix);
		
		for(Command c : Start.getApple().getCommandCenter().getCommands()) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.addField(c.getName(), c.getDescription());
			embed.setColor(Color.GREEN);
			event.getMessageAuthor().asUser().get().sendMessage(embed);
		}
	}
}
