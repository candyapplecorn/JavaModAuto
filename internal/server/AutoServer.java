package server;

import java.util.Properties;

import model.ModAuto;

public interface AutoServer {
	public boolean addAuto(Properties p);

	public String getModels();
	
	// The server allows the client to get a model by name
	public ModAuto getModel(String modelName);
}

