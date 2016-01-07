package model;

import java.util.LinkedHashMap;
//import java.util.Set;

// Fleet lets the user add an auto, delete an auto, and directly manipulate an auto
public class Fleet {
	// It would seem the best key for this linkedhashmap of modauto values should be the modauto's name
	// But thinking about it, we might want to have multiple same models but whose optionsets are different
	// So then using the model name wouldn't work as a key. For now it will be an integer. Previously we used
	// modelname to identify an auto object (see methods here), but I don't want to commit to using the model name
	// as the primary key until the project is more designed.
	private static LinkedHashMap<Integer, ModAuto> m = new LinkedHashMap<Integer, ModAuto>(0);
	
	public boolean addAuto(ModAuto o){
		if (m.containsValue(o)) return false; // Don't allow duplicate entries
		m.put(m.size(), (ModAuto) o);
		return true;
	}
	
	public boolean deleteAuto(String modelname){
		for( ModAuto ma : m.values())
			if ( ma.getName().equalsIgnoreCase(modelname) ) {
				m.remove(ma);
				return true;
			}
		return false;
	}
	
	// Returns a modAuto or null if no auto found
	// This function could be overloaded for a different key than "name:String"
	public ModAuto getModAutoByName(String modelname){
		for( ModAuto ma : m.values())
			if ( ma.getName().equalsIgnoreCase(modelname) )
				return ma;
		return null;
	}
	
	// By returning the linked hashmap, fleet becomes a wrapper for the linkedhashmap m,
	// which provides extra functionality in add/delete auto and getbyname methods.
	public LinkedHashMap<Integer, ModAuto> get(){ return m; }
	
	// AutoServer interface has a method "getModels" - A read operation fitting for fleet
	public String getModels(){
		String ret = "";
		for( ModAuto ma : m.values() )
			ret += (ret == "" ? "" : ", " ) + ma.getName();
		return ret;
	}
}
