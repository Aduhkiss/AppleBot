package me.atticuszambrana.apple.command.impl.membership;

import org.javacord.api.event.message.MessageCreateEvent;

import me.atticuszambrana.apple.command.Command;
import me.atticuszambrana.apple.util.StringUtil;

public class RedeemCommand extends Command {
	public RedeemCommand() {
		super("redeem", "Redeem a license key to recieve your goods");
	}

	@Override
	public void execute(MessageCreateEvent event) {
		String[] args = StringUtil.toArray(event.getMessageContent());
		String code = args[1];
		
		return;
	}
}
