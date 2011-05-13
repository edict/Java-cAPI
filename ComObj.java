import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ComObj implements Runnable {

	ObjectInputStream receiver;
	ObjectOutputStream transmitter;
	Socket connection;
	
	ComObj(Socket server) {
		connection = server;
		try { 
			transmitter = new ObjectOutputStream(connection.getOutputStream());
	    	transmitter.flush();
	    	receiver = new ObjectInputStream(connection.getInputStream());
		}
		catch( IOException ioe ) {
			System.out.println( ioe.toString() );
		}
	}

	public void run() {
		
		
		return;
	}
	
	void sendMsg(String msg)
	{
		try{
			transmitter.writeObject(msg);
			transmitter.flush();
			System.out.println("server> " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
