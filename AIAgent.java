/*
 * Simple agent
 */

public class AIAgent extends Agent {
	
	String name;
	
	AIAgent() {
		constructAIAgent("default");
	}
	
	AIAgent(String inName) {
		constructAIAgent(inName); 
	}
	
	void constructAIAgent(String inName) {
		//numAvatars = [number of ingame players | default = 1];
		// TODO: Get the real player ID from connection
		cAPI = new CogInterface(numAvatars);
		playerIDs = cAPI.getAgentSet();
		name = inName;
	}
	
	public void makeMove() {
		for( int i = 0; i < playerIDs.size(); i++ ) {
			String sel = selectAction();
			if( sel.toLowerCase() == "hp" ) {
				cAPI.reqHealthLevel(playerIDs.get(i));
			}
			else if( sel.toLowerCase() == "armor" ) {
				cAPI.reqArmorLevel(playerIDs.get(i));
			}
			else {
				System.out.println("Invalid move selection!");
			}
		}
	}
	
	public String selectAction() {
		String selection;
		
		if( System.currentTimeMillis() % 2 == 0 ) {
			selection = "HP";
		}
		else {
			selection = "Armor";
		}
		
		return selection;
	}
}
