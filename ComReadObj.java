/*
 * Format of in-game feedback:
 * 
 *  Messages:
 *   <playerID>:<command>/<param1>/<param2>/...
 *   
 *  Images: 
 *   <playerID>:<filename.jpg>
 *   
 *   Audio:
 */

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ComReadObj implements Runnable {

	ObjectInputStream receiver;
	ObjectOutputStream transmitter;
	Socket connection;
	Connection conn;
	
	ComReadObj(Socket server, Connection inConn) {
		connection = server;
		conn = inConn;
		
		try { 
	    	receiver = new ObjectInputStream(connection.getInputStream());
		}
		catch( IOException ioe ) {
			System.out.println( ioe.toString() );
		}
	}

	public void run() {
		String line;
		try {
			// TODO: Need to fix to accout for incoming audio streams and images
			//  - Note that this could involve simpley reading a special line
			//     here and adding the image to the buffer (if its a file name)
			//  - Could be the same for audio files (if streams can't be passed [likely])
			while( (line = receiver.readLine()) != null ) {
				String sender = line.substring(0,line.indexOf(':'));
				String message = line.substring(line.indexOf(':')+1);
				if( message.contains(".jpg") ) {
					// Then it is an image file name: add image to the image stack for this player
					System.out.println("client(" + sender + "> Reading image file: " + message);
					// Load image from file into imgQueue (message var is the file name)
					conn.imgQueueSet.get(sender).add(ImageIO.read(new File(message)));
				}
				//else if( message.contains(".mp3") ) { 	// then open audio file?
				else {
					// Otherwise it must just be a message
					System.out.println("client(" + sender + "> " + message);
					conn.msgQueueSet.get(sender).add(message);
				}
			}
		}
		catch( IOException ioe ) {
			System.out.println(ioe.toString());
		}
		
		return;
	}
	
}
