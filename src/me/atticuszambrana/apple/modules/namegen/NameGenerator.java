package me.atticuszambrana.apple.modules.namegen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import me.atticuszambrana.apple.common.Gender;
import me.atticuszambrana.apple.util.LogUtil;
import me.atticuszambrana.apple.util.Packet;

public class NameGenerator {
	
	private List<String> boyNames = new ArrayList<String>();
	private List<String> girlNames = new ArrayList<String>();
	
	public NameGenerator() {
		//LogUtil.debug("Fetching random names...");
		Gson g = new Gson();
		
		Packet boyPacket = new Packet("https://raw.githubusercontent.com/atticusthecoder/LifeSimulator/master/boyNames.txt");
		Packet girlPacket = new Packet("https://raw.githubusercontent.com/atticusthecoder/LifeSimulator/master/girlNames.txt");
		
		try {
			boyPacket.Send();
			girlPacket.Send();
		} catch(Exception ex) {
			LogUtil.error("There was an error while fetching random names. Printing stack trace...");
			ex.printStackTrace();
		}
		
		boyNames = g.fromJson(boyPacket.getResponse(), List.class);
		girlNames = g.fromJson(girlPacket.getResponse(), List.class);
		
		// Done.
		//LogUtil.debug("Done.");
	}
	
	public List<String> getBoyNames() {
		return boyNames;
	}
	
	public List<String> getGirlNames() {
		return girlNames;
	}
	
	public String getRandomName(Gender gender) {
		Random r = new Random();
		List<String> theList = null;
		if(gender == Gender.MALE) {
			theList = boyNames;
		}
		if(gender == Gender.FEMALE) {
			theList = girlNames;
		}
		
		int i = r.nextInt(theList.size());
		return theList.get(i);
	}
}
