package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import model.ModAuto;
import exception.FixAutoException;
/*
 * The purpose of PropertiesFileIO is to provide the Modular Automobile Project with
 * the capability to create a ModAuto object from a Java Properties file. As the
 * structure of a properties file is different from my programmer-defined input
 * file, the file i/o operations must be different as well.
 * 
 * One problem was that unlike the programmer-defined file, properties files aren't read
 * sequentially from top to bottom. To fix this issue, I chose to name my keys with an
 * optionSet name, followed by a number. The number's only purpose is to make each key unique,
 * since preoprties files need unique keys. The number is stripped off for processing.
 * 
 * Again, the fact that Java supports regular expressions makes parsing much easier to do than
 * in C++ (#include <regex> sucks! Too confusing to conveniently use). Go Java!!!
 * 
 * If the key, aka optionSet, doesn't exist, it's created. Then the value, its property, is 
 * inserted in to the new Modular Automobile.
 */
public class PropertiesFileIO extends FileIO {
	// fromFile takes a properties file 
	public ModAuto fromFile(String filename) {
		ModAuto m = new ModAuto();
		updateAuto(m, getLoadedProps(filename));
		return m;
	}
	// fromFile takes a properties object
	public ModAuto fromFile(Properties props){
		ModAuto m = new ModAuto();
		updateAuto(m, props);
		return m;
	}
	// getLoadedProps returns a properties object from a properties file
	public Properties getLoadedProps(String filename){
		Properties props= new Properties();
		FileInputStream in = null;
		boolean problem;
		do {
			problem = false;
			try {
				in = new FileInputStream(filename);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				filename = (new FixAutoException(15, "File not Found Exception"))
						.getFixString();
				problem = true;
			}
		} while (problem);
		
		try { props.load(in); } 
		catch (IOException e) { e.printStackTrace(); } //This loads the entire file in memory.
		
		return props;
	}
	// updateAuto is a helper function for fromFile and does the parsing
	private void updateAuto(ModAuto newAuto, Properties props){
		
		// -+- INFORMATION PROCESSING -+-
		String information = "", property = "";
		 for (Enumeration<Object> keys = props.keys(); keys.hasMoreElements();){

			 information = (String) keys.nextElement();
			 
			 property = props.getProperty(information, "DEFAULT, $0");
			 
			 information = information.replaceAll("([^0-9]*)([0-9]*)", "$1");
			 
			 // auto name
			 if	(information.toLowerCase().matches(".*model.*"))
				 newAuto.setName			(property);
			 else if	(information.toLowerCase().matches(".*make.*"))
				 newAuto.setMake			(property);
			 else{
				 // ADD a new OPTIONSET if not exists
				 if (newAuto.getElem(information) == -1)
					 newAuto.addOptionSet	(information);
				 // ADD an OPTION - Parse the value!
				 double price = Double.parseDouble(property.replaceAll(".*,\\s?\\$?(.*)$", "$1"));
				 String optionName = property.replaceAll("(.*),.*", "$1");
				 // The parents of PropertiesFileIO uses a different overload of createOption; this
				 // overload takes the optionSet name rather than an index.
				 newAuto.createOption (information, optionName, price);
			 } // end branching
		 } // end for
		}
	}
