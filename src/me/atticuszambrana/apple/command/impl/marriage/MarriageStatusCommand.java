package me.atticuszambrana.apple.command.impl.marriage;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.command.Command;
import me.atticuszambrana.apple.common.Marriage;
import me.atticuszambrana.apple.modules.marriage.MarriageManager;
import me.atticuszambrana.apple.util.LogUtil;

public class MarriageStatusCommand extends Command {
	
	public MarriageStatusCommand() {
		super("marriagestatus", "See your current marriage status on the server");
	}

	@Override
	public void execute(MessageCreateEvent event) {
		Marriage m = MarriageManager.getMarriage(event.getMessageAuthor().getIdAsString());
		DiscordApi api = Start.getApple().getAPI();
		
		// Simple way to check if you are not married
		if(!MarriageManager.isMarried(event.getMessageAuthor().getIdAsString())) {
			EmbedBuilder e = new EmbedBuilder();
			e.setTitle("You are not married to anyone!");
			e.setColor(Color.RED);
			e.setDescription("Sorry, but you are not married to anyone!");
			event.getChannel().sendMessage(e);
			return;
		}
		
		EmbedBuilder embed = new EmbedBuilder();

		embed.setColor(Color.RED);
		try {
			embed.addField("Partner One", api.getUserById(m.getPartnerOne()).get().getName());
			embed.addField("Partner Two", api.getUserById(m.getPartnerTwo()).get().getName());
		} catch (InterruptedException | ExecutionException e) {
			LogUtil.error("There was an error while performing that action! Printing Stack Trace...");
			e.printStackTrace();
		}
		
		embed.addField("Marriage Status", m.getMarriageStatus().getName());
		
		embed.setTitle("Your Marriage Status");
		
		event.getChannel().sendMessage(embed);
		MarriageManager.updateMarriages();
		return;
	}

}
