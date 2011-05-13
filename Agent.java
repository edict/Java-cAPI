import java.util.ArrayList;



public abstract class Agent {
	// Link to the UE game space
	CogInterface cAPI;
	// Number of in-game characters being controlled by this agent
	int numAvatars;
	// TODO: make this a set of ID's for each in-game member
	//String playerID;
	ArrayList<String> playerIDs;
	
	Agent() {
		numAvatars = 1;
	}
	
	public abstract String selectAction();
}
