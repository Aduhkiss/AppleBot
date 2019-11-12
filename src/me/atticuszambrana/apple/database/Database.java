package me.atticuszambrana.apple.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.atticuszambrana.apple.AppleBot;
import me.atticuszambrana.apple.util.LogUtil;

public class Database {
	/*
	 * Database Management class
	 * Author: Atticus Zambrana
	 */
	
	private AppleBot apple;
	
	// Database Connection Settings
	private String url;
	private String name;
	private String username;
	private String password;
	
	// The database connection object that we use
	private Connection connection;
	
	public Database(AppleBot apple) {
		this.apple = apple;
		
		// Grab the connection settings from the config, and store them in this class
		this.url = apple.getConfig().getHost();
		this.name = apple.getConfig().getDB();
		this.username = apple.getConfig().getUsername();
		this.password = apple.getConfig().getPassword();
		
		// Then we want to actually connect to the database through this
		// Also make sure we patch that one issue that I always have when working with databases
		// Where if we dont interact with it for a while, we drop the connection
		// Just make sure to not drop the connection
		
		try {
			if(connection != null && !connection.isClosed()) {
				return;
			}
			
			synchronized(this) {
				if(connection != null && !connection.isClosed()) {
					return;
				}
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://" + this.url + ":" + 3306 + "/" + this.name, this.username, this.password);
			}
		} catch(SQLException | ClassNotFoundException ex) {
			LogUtil.error("Unable to connect to the Database.");
			LogUtil.error("Printing Stack Trace of Error: ");
			ex.printStackTrace();
			System.exit(1);
		}
		
		// The fix
		applyFix();
	}
	
	// A fix for that bug mentioned above
	private void applyFix() {
		Thread t = new Thread() {
			public void run() {
				Thread.currentThread().setName("Database Keep Alive Thread");
				try {
					ResultSet result = connection.createStatement().executeQuery("SELECT * FROM `LicenseKeys`");
				} catch (SQLException e) {
				}
				
				try {
					// 5 Minutes?
					Thread.sleep((1000 * 60) * 5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				applyFix();
			}
		};
		t.start();
	}
	
	public Connection getConnection() {
		return connection;
	}
}
