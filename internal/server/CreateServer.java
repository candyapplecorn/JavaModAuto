package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;

// This class will model knockknockserver.java
public class CreateServer {
	private ServerSocket serverSocket = null;
	private int PORT = 4444;
	private HashSet<ModAutoDefaultSocketClient> h; // In order to gracefully stop the clients, must track them.
	
	// Constructors
	public CreateServer(){ this(4444); }
	public CreateServer(int PORT){
		this.PORT = PORT;
		h = new HashSet<ModAutoDefaultSocketClient>();
	    try {
	        serverSocket = new ServerSocket(PORT);
	    } catch (IOException e) {
	        System.err.println("Could not listen on port: " + PORT);
	        System.exit(1);
	    }
	}
    // End constructors
	
	// Blocking call accepts sockets and spawns DSC's
	public void startServer(){
		ModAutoDefaultSocketClient n; 
		while(true){
	        try {
	        	System.out.println("Waiting to accept on port " + PORT);
	        	n = new ModAutoDefaultSocketClient(serverSocket.accept(), PORT);
	        	h.add( n );
	        	n.start();
	        	System.out.println("Accepted & Started.");
	        } catch (IOException e) {
	            System.err.println("Accept failed.");
	            // Close all connections before exiting
	            stopServer();
	            System.exit(1);
	        }
		}
	}
	// stopServer closes all connections
	private void stopServer() { 
		for (ModAutoDefaultSocketClient m : h) 
			if (m != null) m.closeSession();
	}
}
