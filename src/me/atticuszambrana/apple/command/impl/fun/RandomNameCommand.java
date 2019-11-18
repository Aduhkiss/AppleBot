package me.atticuszambrana.apple.command.impl.fun;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.command.Command;
import me.atticuszambrana.apple.common.Gender;
import me.atticuszambrana.apple.util.StringUtil;

public class RandomNameCommand extends Command {
	
	public RandomNameCommand() {
		super("randomname", "Generate a random name for the given gender");
	}

	@Override
	public void execute(MessageCreateEvent event) {
		String[] args = StringUtil.toArray(event.getMessageContent());
		
		if(args.length == 1) {
			EmbedBuilder err = new EmbedBuilder();
			err.setColor(Color.RED);
			err.setTitle("There was a problem!");
			err.setDescription("You didn't put in the required amount of arguments for this command! You were missing the Gender. (Male/Female) or (Boy/Girl)");
			event.getChannel().sendMessage(err);
			return;
		}
		
		String gender = args[1];
		Gender theGender = null;
		
		// Really cool way of detecting what gender the person put in
		for(Gender g : Gender.values()) {
			for(String handle : g.getHandlers()) {
				if(gender.equalsIgnoreCase(handle)) {
					theGender = g;
				}
			}
		}
		
		// Now that we have the genders figured out, we need to give a random name
		
		String name = Start.getApple().getNameGenerator().getRandomName(theGender);
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setColor(theGender.getColor());
		embed.setTitle("Your name is...");
		embed.setDescription("Your chosen name for the " + theGender.getName() + " gender was " + name + "!");
		
		event.getChannel().sendMessage(embed);
		return;
	}
}
