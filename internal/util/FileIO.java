package util;

import java.io.*;

import exception.*;
import model.ModAuto;

public class FileIO {
		
	// read from file and return an auto object
	public ModAuto fromFile(String filename) throws FixAutoException {
		ModAuto m = new ModAuto();
		try{
			FileReader file = new FileReader(filename);
			BufferedReader buff = new BufferedReader(file);
			while (updateAuto(m, buff.readLine(), buff));
			buff.close();
		} catch (IOException e) {
			throw new FixAutoException(15, "File not Found Exception");
		}
		return m;
	}
	// updateAuto is a helper function for fromFile, with comment and blank line support
	private boolean updateAuto(ModAuto newAuto, String information, BufferedReader b){
		if(information == null) return false;
		
		// -+- COMMENT PROCESSING -+-
		// Multiple line comment support
		boolean flag = true;
		if(information.toLowerCase().matches(".*\\|\\*.*"))
			do{
				try{
					flag = information.matches(".*\\*\\|") ? false : true;
					information = b.readLine();
				} catch (IOException e) {
					return false;
				}
			}while (flag);
		// Single line comment support
		information = information.replaceAll("(.*?)\\|\\|.*$", "$1");
		// Handle an empty line
		if(information.matches("^\\s*$")) return true;
				
		// -+- INFORMATION PROCESSING -+-
		try{
			// Handle a missing auto label
			if	(!information.toLowerCase().matches("^auto\\s?.*") && newAuto.getName() == "")
				throw new FixAutoException(35, "Missing ModAuto Name", newAuto);
			// Handle a missing auto name
			if	(information.toLowerCase().matches("^auto\\s?.*"))
				if 	(information.matches("^auto\\s?[^\"]*(\"\")?[^\"]*")) 
					throw new FixAutoException(33, "Missing ModAuto Name", newAuto);
				else
					newAuto.setName			(information.replaceAll(".*?\"(.*?)\".*", "$1"));
			// ADD an OPTIONSET
			else if (information.toLowerCase().matches(".*option\\s?set.*"))
				// Handle a missing optionset name
				if 	(information.matches("^optionset\\s?[^\"]*(\"\")?[^\"]*")) 
					throw new FixAutoException(31, "Missing OptionSet name", newAuto);
				else
					newAuto.addOptionSet	(information.replaceAll(".*?\"(.*)\".*", "$1"));
			// ADD an OPTION
			else if (information.toLowerCase().matches(".*option.*"))
				// Missing option name AND price
				if	(information.toLowerCase().matches(".*option\\s*$")){ 
					throw new FixAutoException(34, "Missing Option Name & Price", newAuto);
				}
				// Missing Name
				else if (information.toLowerCase().matches(".*option\\s*\\$?[0-9\\.?]+$")) 
					throw new FixAutoException(32, "Missing Option Name", newAuto,
							"",
							Double.parseDouble(information.replaceAll(".*option[^0-9|^\\.]*(.*)$", "$1")));
				// Missing Price
				else if (information.toLowerCase().matches(".*option.*\"[^0-9]*$")) 
					throw new FixAutoException(30, "Missing Option Price", newAuto, 
							information.replaceAll(".*?\"([^\"]+).*", "$1"), .0);
				else
					newAuto.createOption	(newAuto.length() - 1, information.replaceAll(".*?\"([^\"]+).*", "$1"), 
										 	Double.valueOf(information.replaceAll(".*\"\\s+\\$?(.*$)", "$1"))
										 	);
			else
				System.out.println("Error: Invalid Formatting Found:\t" + information);
		} catch (FixAutoException e){
			// Instead of encasing the entire block of parsing code in a do while just to accommodate 
			// one exception, instead recursively call updateAuto to reduce nesting. Error 33 is the
			// only error where a line is entirely missing (no auto declaration/name) - therefore, 
			// we don't want to throw away the current string stored in 'information'.
			return e.getErrorno() == 35 ? updateAuto(newAuto, information, b) : true;//e.isFixed();
		} finally{
			
		}
		
		return true;
	}
	
	// Serialize a modauto object to a file
	public boolean serializeAuto(ModAuto m, String filename){
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(filename.matches(".*\\.ser") ? filename : filename + ".ser");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(m);
			oos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	// deserialize an auto object from file and return an auto object
	public ModAuto deserializeAuto(String filename){
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ModAuto newAuto = null;
		try {
			fis = new FileInputStream(filename.matches(".*\\.ser") ? filename : filename + ".ser");
			ois = new ObjectInputStream(fis);
			newAuto = (ModAuto) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return newAuto;
		}
		
		return newAuto;
	}
}
