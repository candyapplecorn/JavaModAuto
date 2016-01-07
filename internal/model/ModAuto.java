package model;
import java.io.Serializable;
import java.util.ArrayList;
/*
 * Mod auto is basically a wrapper for optionset and options, so it's full
 * of public methods for working with optionsets and options.
 */
/*
 * class ModAuto has multiple methods to accomplish the same or similar tasks, which
 * makes it more flexible to work with.
 */
@SuppressWarnings("serial")
public class ModAuto implements Serializable{
	
	private ArrayList<OptionSet> opts = new ArrayList<OptionSet>(0);
	//private OptionSet [] opts = new OptionSet[0];
	private String name = "";
	private String make = ""; // Toyota, Honda, Ford, etc
	
	// CONSTRUCTORS
	public ModAuto(){}
	public ModAuto(String name){ this.name = name; }
	
	// ACCESSOR/MUTATOR
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	// CREATE
	// =================
	public void addOptionSet(){
		addOptionSet("NO_NAME");
	}
	public void addOptionSet(String name){
		opts.add(new OptionSet(name)); // Generics making things easy
	}
	public void createOption(int optionSetElement, String description, double price){
		if(optionSetElement >= 0 && optionSetElement < length())
			opts.get(optionSetElement).createOption(description, price);
	}
	public void createOption(String optionSetName, String description, double price){
		if(getElem(optionSetName) != -1)
			createOption(getElem(optionSetName), description, price);
	}
	
	// READ
	// =================
	public int getElem(String optionSetName){
		for(int i = 0, l = opts.size(); i < l; i++)
			if(opts.get(i).getName().equalsIgnoreCase(optionSetName))
				return i;
		return -1;
	}
	public int getOptionElem(String optionSetName, String optionDescrip) throws IndexOutOfBoundsException{
		return opts.get(getElem(optionSetName)).getElem(optionDescrip);
	}
	public int length(){ return opts.size(); }
	public int lengthOptionSet(int optionSetElement){ return opts.get(optionSetElement).length(); }
	// return an array containing each OptionSet's name.
	public String[] optionSetNames(){
		int l = opts.size();
		String [] arr = new String[l];
		for(int i = 0; i < l; i++)
			arr[i] = opts.get(i).getName();
		return arr;
	}
	public int getOptionByName(String optionSetName, String optionName){
		int elem = getElem(optionSetName);		
		return elem == -1 ? -1 : opts.get(elem).getElem(optionName);
	}
	// print all of ModAuto's contents
	public String toString		(){
		StringBuilder print = new StringBuilder(String.format("[%-20s", "MODEL" + "]")).append(this.getName() + "\n");
		if (!this.make.equals("")) print.append(String.format("[%-20s", "MAKE" + "]")).append(this.getMake() + "\n");
		for(int i = 0, l = opts.size(); i < l; i++)
			print.append(opts.get(i).toString()).append("\n");
		return print.toString();
	}
	// print a single option set
	public String toString		(String optionSetName) throws IndexOutOfBoundsException{
		return toString(getElem(optionSetName));
	}
	public String toString		(int element) throws IndexOutOfBoundsException{		
		return (element < 0 || element >= opts.size()) ? "INDEX OUT OF BOUNDS" : opts.get(element).toString();
	}
	// getOptionSetName by Element
	public String optionSetName(int element) throws IndexOutOfBoundsException{ 
		return opts.get(element).getName(); 
	}
	// Lab 3 extra actions for handling option choices:
	public String getOptionChoice(String setName){
		int choiceIndex = opts.get(getElem(setName)).getChoice();
		return choiceIndex < 0 ? "ERROR: " + setName + " NOT FOUND" : opts.get(getElem(setName)).toString(choiceIndex);
	}
	public int getOptionChoicePrice(String setName){
		int choiceIndex = opts.get(getElem(setName)).getChoice();
		return choiceIndex < 0 ? -9999 : (int) opts.get(getElem(setName)).getPrice(choiceIndex);
	}
	public void setOptionChoice(String setName, String optionName){
		int element;
		if ( (element = getElem(setName) ) != -1)
			opts.get(element).setChoice(optionName);
	}
	public double getTotalPrice(){
		double sum = 0;
		for(OptionSet optSet : opts)
			sum += optSet.getPrice(optSet.getChoice());
		return sum;
	}
	
	
	// UPDATE
	// =================
	// options:
	public void updateOptionDescription	(int optionSetElement, String oldDescription, String newDescription){
		if (!(optionSetElement < 0 || optionSetElement >= length()))
			opts.get(optionSetElement).update(oldDescription, newDescription, 0x0.000002P-126f);
	}
	public void updateOptionDescription	(int optionSetElement, int optionElement, String newDescription){
		if (!(optionSetElement < 0 || optionSetElement >= length()))
			if (opts.get(optionSetElement).length() > optionElement && optionElement >= 0 )
				opts.get(optionSetElement).update(optionElement, newDescription, 0x0.000002P-126f);
	}
	public void updateOptionPrice		(int optionSetElement, String description, double newPrice){
		if (!(optionSetElement < 0 || optionSetElement >= length()))
			opts.get(optionSetElement).update(description, "SAME", newPrice);
	}
	public void updateOptionPrice		(int optionSetElement, int optionElement, double newPrice){
		if (!(optionSetElement < 0 || optionSetElement >= length()))
			if (opts.get(optionSetElement).length() > optionElement && optionElement >= 0 )
				opts.get(optionSetElement).update(optionElement, "SAME", newPrice);
	}
	public void updateOption			(int optionSetElement, String oldDescription, String newDescription, double newPrice){
		if (!(optionSetElement < 0 || optionSetElement >= length()))
			opts.get(optionSetElement).update(oldDescription, newDescription, newPrice);
	}
	public void updateOption			(int optionSetElement, int optionElement, String newDescription, double newPrice){
		if (!(optionSetElement < 0 || optionSetElement >= length()))
			if (opts.get(optionSetElement).length() > optionElement && optionElement >= 0 )
				opts.get(optionSetElement).update(optionElement, newDescription, newPrice);
	}
	public void updateOption			(String optionSetName, int optionElement, String newDescription, double newPrice){
		if(getElem(optionSetName) != -1)
			if (opts.get(getElem(optionSetName)).length() > optionElement && optionElement >= 0 )
				opts.get(getElem(optionSetName)).update(optionElement, newDescription, newPrice);
	}
	public void updateOption			(String optionSetName, String oldDescription, String newDescription, double newPrice){
		if(getElem(optionSetName) != -1)
			if(opts.get(getElem(optionSetName)).getElem(oldDescription) != -1)
				opts.get(getElem(optionSetName)).update(oldDescription, newDescription, newPrice);
	}
	// Update optionSet's Name:
	public void updateOptionSetName		(int optionSetElement, String newname){
		if (!(optionSetElement < 0 || optionSetElement >= length()))
			opts.get(optionSetElement).setName(newname);
	}
	public void updateOptionSetName		(String optionSetName, String newname){
		if(getElem(optionSetName) != -1)
			updateOptionSetName(getElem(optionSetName), newname);
	}
	
	// DELETE
	// =================
	public void deleteOptionSet(String optionSetName){
		deleteOptionSet(getElem(optionSetName));
	}
	public void deleteOptionSet(int elem){
		if(elem >= opts.size()) return;
		opts.remove(elem); // generics make things easy
	}
	
	public void deleteOption(String optionSetName, String optionDescription){
		if(getElem(optionSetName) != -1 && opts.get(getElem(optionSetName)).getElem(optionDescription) != -1)
			deleteOption(getElem(optionSetName), opts.get(getElem(optionSetName)).getElem(optionDescription));
	}
	public void deleteOption(String optionSetName, int optionElement){
		if(getElem(optionSetName) != -1 && opts.get(getElem(optionSetName)).length() > optionElement && optionElement >= 0)
			deleteOption(getElem(optionSetName), optionElement);
	}
	public void deleteOption(int optionSetElement, String optionDescription){
		if(optionSetElement >= 0 && optionSetElement < length() && opts.get(optionSetElement).getElem(optionDescription) != -1)
			deleteOption(optionSetElement, opts.get(optionSetElement).getElem(optionDescription));
	}
	public void deleteOption(int optionSetElement, int optionElement){
		if(optionSetElement >= 0 && optionSetElement < length() && opts.get(optionSetElement).length() > optionElement && optionElement >= 0)
			opts.get(optionSetElement).delete(optionElement);
	}
}
