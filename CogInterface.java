import java.util.ArrayList;
import java.util.Set;

/*
 * cAPI ver1.0
 * 
 * Author: W. S. Lasecki
 */

public class CogInterface {
	Connection link;
	
	CogInterface(int numAgents) {
		System.out.println("Initializing Cognitive Interface...");
		
		// Initialize a connection to [numAgents] ingame chars
		link = new Connection(numAgents);
	}
	
	/*CogInterface(enum players) {
		super();
	}*/
	
	public ArrayList<String> getAgentSet() {
		return (new ArrayList<String>(link.queueSet.keySet()));
	}
	
	public boolean reqHealthLevel(String playerID) {
		// Request the current amount of health a player has
		link.sendMsg(playerID, "current_health");
		return true;
	}
	
	boolean reqArmorLevel(String playerID) {
		//
		link.sendMsg(playerID, "max_health");
		return true;
	}
	
	boolean reqMaxHealth(String playerID) {
		//
		link.sendMsg(playerID, "current_armor");
		return true;
	}
	
	boolean reqMaxArmor(String playerID) {
		//
		link.sendMsg(playerID, "max_armor");
		return true;
	}
}
