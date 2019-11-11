package me.atticuszambrana.apple.data;

public class Config {
	public String TOKEN;
	public String APP_ID;
	public String PREFIX;
	
	public Config(String TOKEN, String APP_ID, String PREFIX) {
		this.TOKEN = TOKEN;
		this.APP_ID = APP_ID;
		this.PREFIX = PREFIX;
	}
	
	public String getToken() {
		return TOKEN;
	}
	
	public String getID() {
		return APP_ID;
	}
	
	public String getPrefix() {
		return PREFIX;
	}
}
