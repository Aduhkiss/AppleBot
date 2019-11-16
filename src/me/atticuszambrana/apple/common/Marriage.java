package me.atticuszambrana.apple.common;

import java.sql.SQLException;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.database.Database;

public class Marriage {
	
	/*
	 * Will be converted from a row in the DB to this, then parsed back
	 * Author: Atticus Zambrana
	 */
	
	private int ID;
	private MarriageStatus STATUS;
	private String PARTNER_ONE;
	private String PARTNER_TWO;
	private String SERVER_ID;
	
	// This one is the one that will always be used
	public Marriage() {
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int i) {
		ID = i;
	}
	
	public MarriageStatus getMarriageStatus() {
		return STATUS;
	}
	
	public void setMarriageStatus(MarriageStatus s) {
		STATUS = s;
	}
	
	public String getPartnerOne() {
		return PARTNER_ONE;
	}
	
	public void setPartnerOne(String i) {
		PARTNER_ONE = i;
	}
	
	public String getPartnerTwo() {
		return PARTNER_TWO;
	}
	
	public void setPartnerTwo(String i) {
		PARTNER_TWO = i;
	}
	
	public String getServerID() {
		return SERVER_ID;
	}
	
	public void setServerID(String id) {
		SERVER_ID = id;
	}
	
	// fucking broken ass method that i wrote probably while i was high
	
//	public void save() {
//		new Thread() {
//			public void run() {
//				Database db = Start.getApple().getDatabase();
//				
//				try {
//					
//					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `STATUS` = '" + STATUS + "';");
//					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `PARTNER_ONE` = '" + PARTNER_ONE + "';");
//					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `PARTNER_TWO` = '" + PARTNER_TWO + "';");
//					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `SERVER_ID` = '" + SERVER_ID + "';");
//					
//				} catch(SQLException ex) {
//					LogUtil.error("There was an error while writing to Database. Printing Stack Trace...");
//					ex.printStackTrace();
//				}
//			}
//		}.start();
//	}
	
	
	public void make() {
		new Thread() {
			public void run() {
				Thread.currentThread().setName("Marriage Update Thread");
				
				try {
					Database db = Start.getApple().getDatabase();
					
					//System.out.println("Debug: " + "INSERT INTO `Marriages` (`STATUS`, `SERVER_ID`, `PARTNER_ONE`, `PARTNER_TWO`) VALUES (`" + STATUS.toString().toUpperCase() + "`, `" + SERVER_ID + "`, `" + PARTNER_ONE + "`, `" + PARTNER_TWO + "`);");
					
					db.getConnection().createStatement().executeUpdate("INSERT INTO `Marriages` (`STATUS`, `SERVER_ID`, `PARTNER_ONE`, `PARTNER_TWO`) VALUES ('" + STATUS.toString().toUpperCase() + "', '" + SERVER_ID + "', '" + PARTNER_ONE + "', '" + PARTNER_TWO + "');");
					
					// The thing was actually made and inserted into the database yay!
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}.start();
	}
	
	public void save() {
		new Thread() {
			public void run() {
				Thread.currentThread().setName("Marriage Update Thread");
				
				Database db = Start.getApple().getDatabase();
				
				try {
					
					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `STATUS` = '" + STATUS + "' WHERE `Marriage`.`ID` = " + ID + ";");
					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `SERVER_ID` = '" + SERVER_ID + "' WHERE `Marriage`.`ID` = " + ID + ";");
					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `PARTNER_ONE` = '" + PARTNER_ONE + "' WHERE `Marriage`.`ID` = " + ID + ";");
					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `PARTNER_TWO` = '" + PARTNER_TWO + "' WHERE `Marriage`.`ID` = " + ID + ";");
					
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		}.start();
	}
	
	public enum MarriageStatus {
		TOGETHER("Together"),
		DIVORCED("Divorced");
		
		private String name;
		
		MarriageStatus(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
