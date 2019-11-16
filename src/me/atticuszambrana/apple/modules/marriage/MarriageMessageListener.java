package me.atticuszambrana.apple.modules.marriage;

import java.awt.Color;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.common.Marriage;
import me.atticuszambrana.apple.common.Marriage.MarriageStatus;
import me.atticuszambrana.apple.common.MarriageProposal;

public class MarriageMessageListener implements MessageCreateListener {

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		// Check if the message is either yes or no
		String in = event.getMessageContent().toLowerCase();
		User sender = event.getMessageAuthor().asUser().get();
		
		if(in.startsWith("yes") || in.startsWith("no")) {
			// Then check if there is a marriage proposal in our system with them as the
			// user being proposed to
			for(Map.Entry<String, MarriageProposal> mp : MarryHelper.proposals.entrySet()) {
				String asker = mp.getKey();
				MarriageProposal p = mp.getValue();
				if(p.getReceiver().equals(sender.getIdAsString())) {
					
					User proposed = null;
					
					try {
						proposed = Start.getApple().getAPI().getUserById(p.getReceiver()).get();
					} catch(Exception ex) {
						ex.printStackTrace();
					}
					
					// Then accept the proposal if they said yes
					if(in.startsWith("yes")) {
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.GREEN);
						embed.setTitle("They accepted!");
						embed.setDescription(proposed.getName() + " said yes! Congratulations!");
						p.getChannel().sendMessage(embed);
						
						MarryHelper.proposals.get(asker).kill();
						
						new Thread() {
							public void run() {
								Thread.currentThread().setName("Marriage Thread");
								
								Marriage marriage = new Marriage();
								
								marriage.setMarriageStatus(MarriageStatus.TOGETHER);
								marriage.setPartnerOne(mp.getValue().getAsker());
								marriage.setPartnerTwo(mp.getValue().getReceiver());
								marriage.setServerID("lol flap them");
								
								marriage.make();
							}
						}.start();
						return;
					}
					// and deny it if they said no
					else if(in.startsWith("no")) {
						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.GREEN);
						embed.setTitle("They denied.");
						embed.setDescription(proposed.getName() + "said denied your proposal. :(");
						p.getChannel().sendMessage(embed);
						
						// Then kill the thing
						MarryHelper.proposals.get(asker).kill();
						return;
					}
					
				}
			}
		}
	}
}
