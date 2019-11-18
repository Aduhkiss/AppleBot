package me.atticuszambrana.apple.common;

public enum Gender {
	
	MALE("Male", new String[] {"male", "boy"}),
	FEMALE("Female", new String[] {"female", "girl"});
	
	private String name;
	private String[] handlers;
	
	Gender(String name, String[] handlers) {
		this.name = name;
		this.handlers = handlers;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getHandlers() {
		return handlers;
	}
}
