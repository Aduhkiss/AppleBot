package me.atticuszambrana.apple.modules.marriage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.atticuszambrana.apple.Start;
import me.atticuszambrana.apple.common.Marriage;
import me.atticuszambrana.apple.common.Marriage.MarriageStatus;
import me.atticuszambrana.apple.database.Database;
import me.atticuszambrana.apple.util.LogUtil;

public class MarriageManager {
	
	/*
	 * Static class for interfacing the Database
	 * Author: Atticus Zambrana
	 */
	
	private static List<Marriage> marriages = new ArrayList<Marriage>();
	
	/**
	 * Will tell if two users are married together
	 * @param id_one
	 * @param id_two
	 * @return
	 */
	public static boolean isMarriedTogether(String id_one, String id_two) {
		updateMarriages();
	}
	
	/**
	 * Will tell you if a user is married at all
	 * @param id
	 * @return
	 */
	public static boolean isMarried(String id) {
		updateMarriages();
	}
	
	/**
	 * Will return the ID of the given users partner, if there is any
	 * Will return null if not
	 * @param id
	 * @return
	 */
	public static String getPartner(String id) {
		updateMarriages();
		if(isMarried(id) == false) {
			return null;
		}
	}
	
	/**
	 * Will return the Marriage object that we have, for the marriage
	 * @param id
	 * @return
	 */
	public static Marriage getMarriage(String id) {
		updateMarriages();
		for(Marriage m : marriages) {
			if(m.getPartnerOne().equals(id) && m.getPartnerTwo().equals(id)) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * Will update the active marriages on the DB, into memory
	 */
	public static void updateMarriages() {
		new Thread() {
			public void run() {
				Thread.currentThread().setName("Marriage Updater Thread");
				
				// Clear the current list
				marriages.clear();
				
				try {
					Database db = Start.getApple().getDatabase();
					// Will return all marriages that we have on record
					ResultSet result = db.getConnection().createStatement().executeQuery("SELECT * FROM `Marriages`");
					
					while(result.next()) {
						Marriage m = new Marriage();
						
						m.setID(result.getInt("ID"));
						m.setMarriageStatus(MarriageStatus.valueOf(result.getString("STATUS")));
						m.setPartnerOne(result.getString("PARTNER_ONE"));
						m.setPartnerTwo(result.getString("PARTNER_TWO"));
						
						marriages.add(m);
					}
				} catch(SQLException ex) {
					LogUtil.error("There was an error while contacting the database. Printing Stack Trace...");
					ex.printStackTrace();
				}
			}
		}.start();
	}

	public static List<Marriage> getMarriages() {
		return marriages;
	}
}
