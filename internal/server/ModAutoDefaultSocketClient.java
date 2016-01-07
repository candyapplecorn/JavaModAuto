/*
 * ModAutoDefaultSocketClient inherits from DefaultSocketClient, but overloads handleInput, and adds
 * some new constructors as well as helper functions. It also uses a BuildCarModelOptions and performs
 * operations on it.
 */

package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Properties;

import model.ModAuto;
import networking.DefaultSocketClient;
import networking.SocketClientConstants;
import networking.SocketClientInterface;

public class ModAutoDefaultSocketClient extends DefaultSocketClient implements SocketClientConstants, SocketClientInterface {
	private BuildCarModelOptions bcmo = null;
	//private boolean DEBUG = true;
	
	// this constructor calls the DSC's constructor, and instantiates bcmo
	public ModAutoDefaultSocketClient(String strHost, int iPort) {
		super(strHost, iPort);
		bcmo = new BuildCarModelOptions();
		System.out.println("ModAutoDefaultSocketClient listening on port" + iPort);
	}
	
	// This constructor takes a socket that's already been established
	public ModAutoDefaultSocketClient(Socket s, int iPort){
		this(s.getInetAddress().getHostAddress(), iPort);
		sock = s;
	}
	public void run(){
		if (DEBUG) System.out.println("Running DSC.run()");
		if (openConnection()){
			handleSession();
			closeSession();
		}
	}//run
	//public void DEBUGMODE(){ DEBUG = DEBUG ? false : true; System.out.println("Debug mode: " + DEBUG); }
	
	// HandleSession passes a string to handleInput.
	// handleInput will take a string, and that string will represent
	// the action that the client wants to perform. Based on the action,
	// handleInput will call methods and perform actions on and with
	// the private member bcmo, a buildCarModelOptions object
	public void handleInput(Object o){
		if (DEBUG) System.out.println("Received command: " + o.toString());
		String strInput = o.toString();
		int choice = 0;
		try{
			choice = Integer.parseInt(strInput);
		} catch(NumberFormatException e){
			System.out.println ("Invalid input error: " + strInput);
		}
        switch(choice){
        case 1:
        	// Upload a properties object - Server accepts a properties object
			try {
				boolean response = receiveProps();
				writer.writeObject( response ); 
				if (DEBUG) System.out.println("Wrote a boolean to socket!");
			} 
			catch (IOException e) { e.printStackTrace(); }
	        break;
        case 2:
        	// Upload a ModAuto directly - Server accepts it. Don't even need a helper function.
        	
        	try { 
        		boolean response =  bcmo.addAuto( (ModAuto) reader.readObject() );
        		writer.writeObject( response ); 
        	}
        	catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
        	break;
        case 3:
        	// Return a comma delimited list (STRING) of available models
        	String models = bcmo.getModels();
        	try { writer.writeObject( models ); } 
			catch (IOException e) { e.printStackTrace(); }
        	break;
        case 4:
        	// Serialization is just writing an object to a stream
        	// Return a ModAuto based on a model name, or null
        	try { writer.writeObject( bcmo.getModel( (String) reader.readObject() ) ); }
        	catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
        	break;
        default:
        	System.out.println("Invalid input error: " + strInput);
        	break;
        }
	}
	// Add helper methods for each use-case represented by an input string
	// send to handleInput
	private boolean receiveProps(){
		try {
			if (DEBUG) System.out.println("Waiting for a properties object!");
			// BLOCKING - Waits to read an auto object
			boolean response = bcmo.addAuto((Properties) reader.readObject());
			if (DEBUG) System.out.println("Successfully added an auto!");
			return response;
		} catch (ClassNotFoundException | IOException e) { e.printStackTrace(); }
		return false;
	}
}