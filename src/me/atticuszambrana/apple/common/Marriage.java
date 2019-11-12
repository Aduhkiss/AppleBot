package me.atticuszambrana.apple.common;

import java.sql.SQLException;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.database.Database;
import me.atticuszambrana.apple.util.LogUtil;

public class Marriage {
	
	/*
	 * Will be converted from a row in the DB to this, then parsed back
	 * Author: Atticus Zambrana
	 */
	
	private int ID;
	private MarriageStatus STATUS;
	private String PARTNER_ONE;
	private String PARTNER_TWO;
	
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
	
	public void save() {
		new Thread() {
			public void run() {
				Database db = Start.getApple().getDatabase();
				
				try {
					
					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `STATUS` = '" + STATUS + "';");
					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `PARTNER_ONE` = '" + PARTNER_ONE + "';");
					db.getConnection().createStatement().executeUpdate("UPDATE `Marriages` SET `PARTNER_TWO` = '" + PARTNER_TWO + "';");
					
				} catch(SQLException ex) {
					LogUtil.error("There was an error while writing to Database. Printing Stack Trace...");
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
