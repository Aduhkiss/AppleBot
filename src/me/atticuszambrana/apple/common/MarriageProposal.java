package me.atticuszambrana.apple.common;

import org.javacord.api.entity.channel.TextChannel;

public class MarriageProposal {
	
	private String ASKER;
	private String RECEIVER;
	private boolean isActive;
	private TextChannel channel;
	
	public MarriageProposal(String ASKER, String RECEIVER, TextChannel channel) {
		this.ASKER = ASKER;
		this.RECEIVER = RECEIVER;
		this.isActive = true;
		this.channel = channel;
		
		// After 5 minutes, lets self destruct this marriage proposal from the system
		new Thread() {
			public void run() {
				Thread.currentThread().setName("Marriage Proposal Self Destruct Thread");
				
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				isActive = false;
			}
		}.start();
	}
	
	public String getAsker() {
		return ASKER;
	}
	
	public String getReceiver() {
		return RECEIVER;
	}
	
	public TextChannel getChannel() {
		return channel;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void kill() {
		isActive = false;
	}
}
