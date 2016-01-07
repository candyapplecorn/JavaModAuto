package adapter;
//import java.io.IOException;

import java.util.Properties;

import exception.FixAutoException;
import model.*;
import util.*;
//This class will contain all the implementation of any method declared in
//the interface.
public abstract class ProxyModAuto {

	protected static Fleet f = new Fleet();
	
	// Interface AutoServer
	public String getModels() { return f.getModels(); }
	
	// This is an overload of addAuto from ModAutoCollection, which accepts
	// just a properties file. It calls BuildAuto with "2" signifying that
	// the input file is a properties file. Returning boolean simplifies code
	// in the server package.
	public boolean addAuto(String propertiesFileName){
		this.BuildAuto(propertiesFileName, 2);
		return true;
	}
	// This overload of addAuto takes a properties object
	public boolean addAuto(Properties p){
		f.addAuto((new PropertiesFileIO()).fromFile(p));
		return true;
	}
	
	public ModAuto getModel(String modelName){ return f.getModAutoByName(modelName); }
	// End Interface AutoServer
	
	// Interface ModAutoCollection
	public boolean deleteAuto(String modelname){
		return f.deleteAuto(modelname);
	}	

	public boolean addAuto(Object o){
		return f.addAuto((ModAuto) o);
	}
	// End Interface ModAutoCollection
	
	// Interface ModAutoOptionSelect
	public double getTotalPrice(String modelname){
		return f.getModAutoByName(modelname) != null ? f.getModAutoByName(modelname).getTotalPrice() : 0;
	}
	
	public void setOptionChoice(String modelname, String setName, String optionName){
		ModAuto n = f.getModAutoByName(modelname);
		if (n != null) n.setOptionChoice(setName, optionName);
	}
	
	public String getSelectedOptions(String modelname){
		ModAuto n = f.getModAutoByName(modelname);
		if (n == null) return modelname + " not found.";
		StringBuilder selected = new StringBuilder("Selected Option Choices: \n[MODEL]:\t" + modelname + "\n");
		
		for (int index = 0, numOptionSets = n.length(); index < numOptionSets; index++){
			String optionSetName = n.optionSetName(index);
			selected.append((index != 0 ? "\n" : "") + "[" + optionSetName.toUpperCase() + "]:\t" + n.getOptionChoice(optionSetName));
		}
			
		
		return selected.toString();
	}
	// End Interface ModAutoOptionSelect
	
	// Interface createModAuto
	public boolean BuildAuto(String filename, int filetype){
		FileIO fi = filetype == 1 ? new FileIO() :  new PropertiesFileIO();
		ModAuto n = new ModAuto();
		
		boolean exception;
		do{ // In case the file isn't found, encase in a try/catch
			exception = false;
			try{
				n = fi.fromFile(filename);
			}catch(FixAutoException e){
				exception = true;
				filename = e.getFixString();
			}
		}while(exception);
		
		return f.addAuto(n);
	}

	public void printAuto(String  Modelname){
		ModAuto ma;
		if ( (ma = f.getModAutoByName(Modelname) ) != null )
			System.out.println( ma.toString() );
	}
	// End Interface createModAuto
	
	// Interface UpdateModAuto
	public void updateOptionSetName(String Modelname, String OptionSetname, String newName){
		int index;
		ModAuto ma;
		if ( (ma = f.getModAutoByName(Modelname) ) != null )
			if ( (index = ma.getElem(OptionSetname)) != -1)
				ma.updateOptionSetName(index, newName);
	}
	
	public void updateOptionPrice(String Modelname, String Optionname, String Option, float newprice){
		int index;
		ModAuto ma;
		if ( (ma = f.getModAutoByName(Modelname) ) != null )
				if( (index = ma.getElem(Optionname) ) != -1)
					ma.updateOptionPrice(index, Option, newprice);
	}
	// End Interface UpdateModAuto
	
	
}
