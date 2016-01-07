/*
BuildCarModelOptions
 */
package server;

import adapter.ProxyModAuto;

// Making this class' implementation empty doesn't break anything since all the implementation
// is in proxyModAuto
public class BuildCarModelOptions extends ProxyModAuto implements AutoServer {
	// Returns a comma delimited list(String) of model names (element, element, element, etc)
	// DECLARED in autoserver interface, IMPLEMENTED in 
	// (abstract) proxyModAuto - USED in BuildCarModelOption
	//public String getModels () { return super.getModels(); }
	
	// Takes a modelname and either returns a matching modauto, or null.
	// DECLARED in autoserver interface, IMPLEMENTED in
	// (abstract) proxyModAuto - USED in BuildCarModelOption
	//public ModAuto getModel (String modelName){ return super.getModel(modelName); }
	
}
