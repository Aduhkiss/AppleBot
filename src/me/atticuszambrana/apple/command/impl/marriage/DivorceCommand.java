package me.atticuszambrana.apple.command.impl.marriage;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.command.Command;
import me.atticuszambrana.apple.common.Marriage;
import me.atticuszambrana.apple.modules.marriage.MarriageManager;

public class DivorceCommand extends Command {
	
	public DivorceCommand() {
		super("divorce", "Divorce your current spouse");
	}

	@Override
	public void execute(MessageCreateEvent event) {
		if(!MarriageManager.isMarried(event.getMessageAuthor().getIdAsString())) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription("You are not married!");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		// Then separate them lmao
		Marriage m = MarriageManager.getMarriage(event.getMessageAuthor().getIdAsString());
		String other = getOther(event.getMessageAuthor().getIdAsString(), m.getPartnerOne(), m.getPartnerTwo());
		
		try {
			User target = Start.getApple().getAPI().getUserById(other).get();
			
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setColor(Color.RED);
			embed.setTitle("Your spouse has divorced you!");
			embed.setDescription("Your spouse " + event.getMessageAuthor().getName() + ", has chosen to divorce you. Sorry for the sad news.");
			
			target.sendMessage(embed);
			
			EmbedBuilder idiot = new EmbedBuilder();
			
			idiot.setColor(Color.RED);
			idiot.setTitle("You have divorced your spouse!");
			idiot.setDescription("You have chosen to divorce your spouse, " + target.getName() + "! I always knew that marriage was never going to work.");
			
			event.getChannel().sendMessage(idiot);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// This will remove the marriage from the database
		m.remove();
	}

	private String getOther(String executed, String one, String two) {
		if(executed.equals(one)) {
			return two;
		}
		if(executed.equals(two)) {
			return one;
		}
		return null;
	}
}
