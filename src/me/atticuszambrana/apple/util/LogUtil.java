package me.atticuszambrana.apple.util;

import me.atticuszambrana.apple.Start;

public class LogUtil {
	private static void print(String message, LogType type) {
		System.out.println("(" + DateUtil.getTime() + ") " + "[" + type.toString().toUpperCase() + "] " + message);
	}
	
	public static void error(String message) {
		print(message, LogType.ERROR);
	}
	
	public static void debug(String message) {
		if(Start.getApple().isDebug()) {
			print(message, LogType.DEBUG);
		}
	}
	
	public static void log(String message) {
		print(message, LogType.NORMAL);
	}
	
	public enum LogType {
		NORMAL,
		DEBUG,
		ERROR,
	}
}
