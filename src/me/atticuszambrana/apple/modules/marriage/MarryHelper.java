package me.atticuszambrana.apple.modules.marriage;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.common.MarriageProposal;

public class MarryHelper {
	/*
	 * Static class that will be used to help us with creating marriages and ending them
	 * Author: Atticus Zambrana
	 */
	
	public static Map<String, MarriageProposal> proposals = new HashMap<String, MarriageProposal>();
	
	public static void propose(String asker, String proposed, TextChannel channel) {
		MarriageProposal pro = new MarriageProposal(asker, proposed, channel);
		proposals.put(asker, pro);
		
		User target = null;
		User ask = null;
		
		try {
			target = Start.getApple().getAPI().getUserById(proposed).get();
			ask = Start.getApple().getAPI().getUserById(asker).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Once we got the code stuff done, we need to announce it to the server
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.PINK);
		embed.setTitle("Congratulations!");
		embed.setDescription(ask.getName() + " has proposed to " + target.getName() + "!" + 
		"\nDo you accept? (Say Yes, or No)");
		channel.sendMessage(embed);
		
		// Then we will handle stuff on another listener for the chat to see if they say yes or no
	}
	
	public static boolean alreadyProposed(String id) {
		for(Map.Entry<String, MarriageProposal> entry : proposals.entrySet()) {
			// Make a real quick check to remove any closed marriage proposals from our system here
			if(!entry.getValue().isActive()) {
				proposals.remove(entry.getKey());
			}
			
			if(entry.getKey().equals(id)) {
				return entry.getValue().isActive();
			}
		}
		return false;
	}
}
