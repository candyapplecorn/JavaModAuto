/*
 * SelectCarOption extends ProxyModAuto and so has all its capabilties, and is used
 * by a client to speak to a server. Besides being a proxyModAuto itself, it also
 * uses (and wraps around) a CarModelOptionIO object for socket communications.
 */
package client;

import model.ModAuto;
import adapter.ProxyModAuto;

public class SelectCarOption extends ProxyModAuto implements AutoClient {
	private CarModelOptionIO cmo;
	public SelectCarOption(CarModelOptionIO cmo) {
		this.cmo = cmo;
	}
	
	// Begin wrapping of implementation of AutoClient interface - I'm trying some wacky formatting
	// =====================
	//public boolean SendProperties	(String propertiesFileName)	{ return cmo.SendProperties(propertiesFileName); }
	public boolean BuildAuto		(String Filename, int type)	{ return cmo.BuildAuto(Filename, type); }
	public boolean SendAuto			(ModAuto m)					{ return cmo.SendAuto(m); }
	public ModAuto getByName		(String modelname)			{ return cmo.getByName(modelname); }
	public String getModels			()							{ return cmo.getModels(); }
	// closeConnection isn't in the AutoClient interface, but it's a wrapper method nonetheless
	public void closeConnection		()							{ cmo.closeSession(); }
	public void start() { cmo.start(); }
	public void setDebug(boolean debug) { cmo.setDebug(debug); }
	// =====================
	// End wrapping of implementation of AutoClient interface
	
}