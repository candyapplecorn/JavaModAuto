/*
 * There's an interface called AutoServer, so it's only natural that there should
 * be an interface for AutoClient as well.
 */
package client;
import model.ModAuto;

public interface AutoClient {
	// =====================
	// The send functions send either a properties object or a serialized auto object
	// to the server. The server responds with a boolean value indicating success or failure.
	// Thus the send functions block on readBoolean(), and return that boolean.
	// =====================
	public boolean BuildAuto(String Filename, int filetype);
	public boolean SendAuto(ModAuto m);
	
	
	// =====================
	// these get functions query the server and return the results
	// =====================
	public ModAuto getByName(String modelname);
	public String getModels();
}
