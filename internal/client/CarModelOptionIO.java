package client;

import networking.DefaultSocketClient;
import exception.FixAutoException;
import model.ModAuto;

public class CarModelOptionIO extends DefaultSocketClient implements AutoClient /*, SocketClientConstants, SocketClientInterface*/ {
	
	private boolean DEBUG = true;
	// The default socket client's constructor works well for the client side.
	public CarModelOptionIO(String strHost, int iPort) { 
		super(strHost, iPort);
	}
	
	public void setDebug(boolean debug) { this.DEBUG = debug; }
	
	// =====================
	// The send functions send either a properties object or a serialized auto object
	// to the server. The server responds with a boolean value indicating success or failure.
	// Thus the send functions block on readBoolean(), and return that boolean.
	// =====================
	public boolean BuildAuto(String Filename, int filetype){
		if (filetype != 1)
			try { 
				return SendAuto( (new util.FileIO().fromFile(Filename) ) ); 
			} catch (FixAutoException e) { return false; }
	
		// "1" tells the server we want to send a properties object
		if (DEBUG) System.out.println(writer == null ? "Writer is null!" : "Writer isn't null.");
		this.Write("1");
		if (DEBUG) System.out.println("Successfully wrote a properties object! Waiting for a boolean");
		return (boolean) this.Exchange(new util.PropertiesFileIO().getLoadedProps(Filename));
	}
	public boolean SendAuto(ModAuto m){
		// "2" tells the server we want to send a serialized auto object
		this.Write("2");
		// send the modAuto to the (now waiting) server
		return (boolean) this.Exchange(m);
	}
	// End of send functions
	
	// =====================
	// these get functions query the server and return the results
	// =====================
	public ModAuto getByName(String modelname){
		// "4" tells the server we want to get an auto by name
		this.Write("4");
		// After receiving the modelname, the server will serialize the corresponding auto
		return (ModAuto) this.Exchange(modelname);
	}
	public String getModels(){
		// 3 is the option to get all models
		if (DEBUG) System.out.println("getModels() - Attempting write");
		return (String) this.Exchange("3");
	}
	// End get functions

	@Override
	public void handleInput(Object o) {}
}
