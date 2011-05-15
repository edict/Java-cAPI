import java.awt.Image;
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
	// TODO: Consider changing this to use 3 queues per player (Message, Image, Audio) -> or use direct audio streams / video streams
	HashMap<String,ArrayList<String>> msgQueueSet;
	// NOTE: Could use BufferedImage (subclass of Image) to allow manipulation later
	HashMap<String,ArrayList<Image>> imgQueueSet;
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
		msgQueueSet = new HashMap<String,ArrayList<String>>();
		imgQueueSet = new HashMap<String,ArrayList<Image>>();
		
	    try {
	    	service = new ServerSocket(portNumber);
	    	for( int i = 0; i < numAgents; i++ ) {
	            Socket tempConnection = service.accept();
	            
	            ObjectInputStream receiver = new ObjectInputStream(tempConnection.getInputStream());
	            String pID = receiver.readLine();
	            
	            connectionSet.put(pID, tempConnection);
	            msgQueueSet.put(pID, new ArrayList<String>());
	            imgQueueSet.put(pID, new ArrayList<Image>());
	            
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
	
	// Return the top message from the stack
	public String popMsg(String playerID) {
		if( msgQueueSet.get(playerID).size() == 0 ) {
			return msgQueueSet.get(playerID).remove(0);
		}
		
		return null;
	}
	
	// Return the whole stack of incoming messages since last check
	public ArrayList<String> getMsgSet(String playerID) {
		ArrayList<String> retAry = new ArrayList<String>(msgQueueSet.get(playerID));
		msgQueueSet.clear();
		return retAry;
	}
	
	// Return whole stack of images (loss-less)
	public ArrayList<Image> getImgSet(String playerID) {
		ArrayList<Image> retAry = new ArrayList<Image>(imgQueueSet.get(playerID));
		msgQueueSet.clear();
		return retAry;
	}
	
	// Return the top image from the stack of images (loss-less)
	public Image getNextImg(String playerID) {
		if( imgQueueSet.get(playerID).size() == 0 ) {
			return imgQueueSet.get(playerID).remove(0);
		}
		return null;
	}

	// Return the latest image from the stack of images (loss-full)
	//  Automatically clears the rest of the stack [req?] -- incl due to typical expected use case
	public Image getNewestImg(String playerID) {
		if( imgQueueSet.get(playerID).size() == 0 ) {
			Image retImg = imgQueueSet.get(playerID).remove(imgQueueSet.size()-1);
			imgQueueSet.clear();
			return retImg;
		}
		return null;
	}
}
