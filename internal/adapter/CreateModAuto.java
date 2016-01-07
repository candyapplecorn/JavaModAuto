package adapter;

public interface CreateModAuto {
	//Given a text file name a method called BuildAuto can be
	//written to build an instance of Automobile. This method
	//does not have to return the Auto instance.
	// DEPRECIATED: public void BuildAuto(String filename);
	
	// This overload accepts a filetype designator, with the options for a properties file or
	// the previously used programmer-defined text file
	public boolean BuildAuto(String filename, int filetype);
	
	//This function searches and prints the properties of a
	//given model.
	public void printAuto(String  Modelname);
	
}
