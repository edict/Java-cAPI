/*
 * Simple (naive) agent
 */

public class AIAgent extends Agent {
	
	String name;
	
	AIAgent() {
		super();
		constructAIAgent("default");
	}
	
	AIAgent(String inName) {
		super();
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
			
			// Wait for response
			Object reply = cAPI.waitMsg(playerIDs.get(i));
			System.out.println("Game Character Response to Request [" + sel + "]: (Is of type)" + reply.getClass().toString());
			
			/*
			if( reply.getClass() == "String" ) {
				// Process message
			else if( reply.getClass() == "Image" ) {
				// Process image
			}
			else if( reply.getClass() == "Audio" ) {
				// Process audio
			}
			else {
				System.out.println("Unknown data type in stack!");
			}
			*/
			 
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
