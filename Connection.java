import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Connection {
	public static int portNumber = 1234;
	
	ServerSocket service;
	ObjectOutputStream transmitter;
	
	// For each player ID (string) we store a queue of waiting messages
	HashMap<String,ArrayList<String>> queueSet;
	// TODO: Extend to multi-writer. This requires sending an initial msg to
	//	retrieve the pID associated with the connection.
	HashMap<String,Socket> connectionSet;
	//Socket connection;
	
	
	Connection() {
		createConnection(1);
	}
	
	Connection(int numAgents) {
		createConnection(numAgents);
	}
	
	public void createConnection(int numAgents) {
		queueSet = new HashMap<String,ArrayList<String>>();
		
	    try {
	    	service = new ServerSocket(portNumber);
	    	for( int i = 0; i < numAgents; i++ ) {
	            Socket tempConnection = service.accept();
	            
	            ObjectInputStream receiver = new ObjectInputStream(tempConnection.getInputStream());
	            String pID = receiver.readLine();
	            
	            connectionSet.put(pID, tempConnection);
	            queueSet.put(pID, new ArrayList<String>());
	            
	            ComReadObj in = new ComReadObj(connectionSet.get(pID),this);
	            Thread reader = new Thread(in);
	            reader.start();
	            
	            /*ComWriteObj out = new ComObj(connection);
	            Thread writer = new Thread(out);
	            writer.start();
	            */
	            
	            transmitter = new ObjectOutputStream(connectionSet.get(pID).getOutputStream());
		    	transmitter.flush();
	        }
		    
	    }
	    catch (IOException e) {
	       System.out.println(e);
	    }
	}
	
	public boolean sendMsg(String playerID, String msg)
	{
		try{
			String message = playerID + ":" + msg;
			transmitter.writeObject(message);
			transmitter.flush();
			System.out.println("server> " + message);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public ArrayList<String> getMsg(String playerID) {
		return (new ArrayList<String>(queueSet.get(playerID)));
	}

}
