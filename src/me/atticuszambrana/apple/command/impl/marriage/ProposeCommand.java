package me.atticuszambrana.apple.command.impl.marriage;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.command.Command;
import me.atticuszambrana.apple.common.Marriage;
import me.atticuszambrana.apple.common.MarriageProposal;
import me.atticuszambrana.apple.modules.marriage.MarriageManager;
import me.atticuszambrana.apple.modules.marriage.MarryHelper;

public class ProposeCommand extends Command {
	
	public ProposeCommand() {
		super("propose", "Propose to marry another user");
	}

	@Override
	public void execute(MessageCreateEvent event) {
		List<User> targets = event.getMessage().getMentionedUsers();
		
		if(targets.isEmpty()) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription("You are required to give a user to propose to!");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		if(targets.size() > 1) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription("You are only allowed to propose to one user!");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		User target = targets.get(0);
		
		// Do basic marriage checks to see if there is already a pending proposal, or if any of the users are already married
		
		if(MarriageManager.isMarried(event.getMessageAuthor().getIdAsString())) {
			Marriage m = MarriageManager.getMarriage(event.getMessageAuthor().getIdAsString());
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription("You are already married!");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		if(MarriageManager.isMarried(target.getIdAsString())) {
			Marriage m = MarriageManager.getMarriage(event.getMessageAuthor().getIdAsString());
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription(target.getName() + " is already married!");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		if(MarryHelper.alreadyProposed(event.getMessageAuthor().getIdAsString())) {
			EmbedBuilder embed = new EmbedBuilder();
			MarriageProposal pro = MarryHelper.proposals.get(event.getMessageAuthor().getIdAsString());
			User t = null;
			try {
				t = Start.getApple().getAPI().getUserById(pro.getReceiver()).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			embed.setColor(Color.RED);
			embed.setTitle("There was a problem!");
			embed.setDescription("You already have a pending proposal for " + t.getName() + "!");
			event.getChannel().sendMessage(embed);
			return;
		}
		
		MarryHelper.propose(event.getMessageAuthor().getIdAsString(), target.getIdAsString(), event.getChannel());
		return;
	}

}
