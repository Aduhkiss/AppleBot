package me.atticuszambrana.apple.common;

import java.awt.Color;

public enum Gender {
	
	MALE("Male", new String[] {"male", "boy"}, Color.CYAN),
	FEMALE("Female", new String[] {"female", "girl"}, Color.PINK);
	
	private String name;
	private String[] handlers;
	private Color color;
	
	Gender(String name, String[] handlers, Color color) {
		this.name = name;
		this.handlers = handlers;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getHandlers() {
		return handlers;
	}
	
	public Color getColor() {
		return color;
	}
}
