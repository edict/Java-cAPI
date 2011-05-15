import java.awt.Image;
import java.util.ArrayList;


/*
 * cAPI ver1.0
 * 
 * Author: W. S. Lasecki
 */

public class CogInterface {
	Connection link;
	
	// CONSTRUCTORS ============================================================
	
	CogInterface(int numAgents) {
		System.out.println("Initializing Cognitive Interface...");
		
		// Initialize a connection to [numAgents] ingame chars
		link = new Connection(numAgents);
	}
	
	// ACCESSORS
	
	public ArrayList<String> getAgentSet() {
		return (new ArrayList<String>(link.msgQueueSet.keySet()));
	}
	
	// Messages
	public String getMsg(String playerID) {
		return link.popMsg(playerID);
	}
	
	public ArrayList<String> getMsgStack(String playerID) {
		return link.getMsgSet(playerID);
	}
	
	public Object waitMsg(String playerID) {
		Object response = null;
		while( (response = link.popMsg(playerID)) == null ) {
			// Do nothing
		}
		
		return response;
	}
	
	// Images
	public Image getNextImg(String playerID) {
		return link.getNextImg(playerID);
	}
	
	public Image getNewestImg(String playerID) {
		return link.getNewestImg(playerID);
	}
	
	public ArrayList<Image> getImgStack(String playerID) {
		return link.getImgSet(playerID);
	}
	
	// Audio
	// TODO: Add retrieval for audio streams [or files]
	
	// SENSORS ============================================================
	
	// Request the amount of health which the given char has
	public boolean reqHealthLevel(String playerID) {
		// Request the current amount of health a player has
		link.sendMsg(playerID, "current_health");
		return true;
	}
	
	// Request the amount of armor which the given char has
	public boolean reqArmorLevel(String playerID) {
		//
		link.sendMsg(playerID, "max_health");
		return true;
	}
	
	// Request the maximum amount of health which the given char can acquire
	public boolean reqMaxHealth(String playerID) {
		//
		link.sendMsg(playerID, "current_armor");
		return true;
	}
	
	// Request the maximum amount of armor which the given char can acquire
	public boolean reqMaxArmor(String playerID) {
		//
		link.sendMsg(playerID, "max_armor");
		return true;
	}
	
	// TODO: Support the following
	
	// Request the current position of the char
	// TODO: Support retrieving the location in 2 ways: raw pos or change in position since last req
	public boolean reqLoc(String playerID) {
		//
		link.sendMsg(playerID, "current_location");
		return true;
	}
	
	/*
	 * Issues:
	 *  - How do we handle sending back complex data such as the following?
	 *   --> Video (raw SS feed)
	 *   --> Video (symbolic version: what is being looked at)
	 *   --> Audio (raw signal)
	 *   --> Audio (symbolic? i.e. event being heard)
	 *   -->  
	 */
	
	
	// ACTIONS ============================================================
	
	// Perform the 'use' action
	public boolean doUse(String playerID) {
		//
		link.sendMsg(playerID, "player_use");
		return true;
	}
	
	// Move the character forward
	public boolean doMoveForward(String playerID, double dist) {
		// NOTE: Can use dist or as a single step. Dist could be an int here.
		link.sendMsg(playerID, "player_move_forward/" + dist);
		return true;
	}
	
	// Move the character backward
	public boolean doMoveBackward(String playerID, double dist) {
		// NOTE: Can use dist or as a single step. Dist could be an int here.
		link.sendMsg(playerID, "player_move_backward/" + dist);
		return true;
	}
	
	// Move the character left (strafe)
	public boolean doMoveLeft(String playerID, double dist) {
		// NOTE: Can use dist or as a single step. Dist could be an int here.
		link.sendMsg(playerID, "player_move_left/" + dist);
		return true;
	}
	
	// Move the character right (strafe)
	public boolean doMoveRight(String playerID, double dist) {
		// NOTE: Can use dist or as a single step. Dist could be an int here.
		link.sendMsg(playerID, "player_move_right/" + dist);
		return true;
	}
	
	// Turn the character left
	public boolean doLookLeft(String playerID, double dist) {
		// NOTE: Dist in degrees?
		link.sendMsg(playerID, "view_rotate_left/" + dist);
		return true;
	}
	
	// Turn the character right
	public boolean doLookRight(String playerID, double dist) {
		// NOTE: Dist in degrees?
		link.sendMsg(playerID, "view_rotate_right/" + dist);
		return true;
	}
	
	// Look up
	public boolean doLookUp(String playerID, double dist) {
		// NOTE: Dist in degrees?
		link.sendMsg(playerID, "view_rotate_up/" + dist);
		return true;
	}
	
	// Look down
	public boolean doLookDown(String playerID, double dist) {
		// NOTE: Dist in degrees?
		link.sendMsg(playerID, "view_rotate_down/" + dist);
		return true;
	}
	
	// Crouch the character
	public boolean doCrouch(String playerID) {
		//
		link.sendMsg(playerID, "player_crouch");
		return true;
	}
	
	// Make the character jump
	public boolean doJump(String playerID) {
		//
		link.sendMsg(playerID, "player_jump");
		return true;
	}
	
	// Fire the character's current weapon
	public boolean doFire(String playerID) {
		//
		link.sendMsg(playerID, "weapon_fire");
		return true;
	}
	
	// Use the character's current weapon's alternate fire mode
	public boolean doAltFire(String playerID) {
		//
		link.sendMsg(playerID, "weapon_alternate");
		return true;
	}
	
	// Change the character's current weapon
	public boolean doSwitchWeapon(String playerID, int selection) {
		//
		link.sendMsg(playerID, "weapon_switch/" + selection);
		return true;
	}
	
	/*
	 * Issues:
	 *  - How do we handle the following?
	 *   --> Sustained actions (i.e. hold alt fire or fire down): pass duration?
	 *   --> 
	 */
}
