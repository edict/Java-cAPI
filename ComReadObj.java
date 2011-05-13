import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
			while( (line = receiver.readLine()) != null ) {
				String sender = line.substring(0,line.indexOf(':'));
				String message = line.substring(line.indexOf(':')+1); 
				System.out.println("client(" + sender + "> " + message);
				conn.queueSet.get(sender).add(message);
			}
		}
		catch( IOException ioe ) {
			System.out.println(ioe.toString());
		}
		
		return;
	}
	
}
