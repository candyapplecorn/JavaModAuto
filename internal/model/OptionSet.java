package model;

import java.io.Serializable;
import java.util.ArrayList;

// OptionSet contains an array of options for one property
@SuppressWarnings("serial")
public class OptionSet implements Serializable {

	private ArrayList<Option> opts = new ArrayList<Option>();
	//private Option [] opts = new Option[0];
	private String name = ""; // Brakes/Traction Control, Transmission, Color, Side Impact Air Bags, Power Moonroof
	private Option choice = null; // Will hold the user's selected choice
	
	public OptionSet(){}
	public OptionSet(String name){
		setName(name);
	}
	public OptionSet(String name, int size){
		this(name);
		opts = size >= 0 ? new ArrayList<Option>(size) : new ArrayList<Option>();
		for(int i = 0; i < size; i++)
			opts.set(i, new Option("DEFAULT", 0.0));
			//opts.get(i) = new Option("DEFAULT", 0.0);
	}
	
	// Accessors/Mutators
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		this.name = name;
	}
	// Option shouldn't be exposed, so instead, return choice's index in opts.
	// Then the user can use the index to get the name, price or toString values, like any other option
	// If choice is null, default to element 0
	protected int getChoice() {
		return choice != null ? opts.indexOf(choice) : opts.size() == 0 ? -1 : 0;
	}
	protected void setChoice(String optionname) {
		for(Option op : opts)
			if (op.getName().equalsIgnoreCase(optionname)){
				choice = op; 
				return; 
			}
	}
	
	// =================
	// CREATE OPERATIONS
	protected void createOption(String description, double price){
		if(getElem(description) != -1) return;
		opts.add(new Option(description, price)); // Oh generics, you make things so simple
	}
	
	// =================
	// READ OPERATIONS
	protected int getElem(String description){
		for(int i = 0, l = opts.size(); i < l; i++)
			if(opts.get(i).getName().equalsIgnoreCase(description))
				return i;
		return -1;
	}
	protected double getPrice(int elem) throws IndexOutOfBoundsException{
		return opts.get(elem).getPrice();
	}
	protected String getDescription(int elem) throws IndexOutOfBoundsException{
		return opts.get(elem).getName();
	}
	// Can't reduce visibility of toString() method
	public String toString(){
		StringBuilder all = (new StringBuilder(String.format("[%-20s", this.name.toUpperCase() + "]")));
		for(int i = 0, l = opts.size(); i < l; i++)
			all.append(opts.get(i).toString()).append(i < l - 1 ? ", " : "");
		return all.toString();
	}
	protected String toString (int elem) throws IndexOutOfBoundsException{
		return opts.get(elem).toString();
	};
	protected int length(){ return opts.size(); }
	
	// =================
	// DELETE OPERATIONS
	
	protected void delete(String name){
		delete(getElem(name));
	}
	// delete only calls opts.size() once. It also only iterates through the array opts
	// once. The only bad part to this algorithm is that it uses a ternary operator each iteration.
	protected void delete(int elem){
		if (elem >= opts.size()) return;
		if (opts.get(elem) == choice) choice = null;
		opts.remove(elem); // Again, generics makin' life e-z 
	}

	// =================
	// UPDATE OPERATIONS
	// 6 different update functions should cover any use case
	// (0x0.000002P-126f is the MIN_double value, taken from oracle's documentation)
	protected void update(String oldDescription, String newDescription){
		update(getElem(oldDescription), newDescription, 0x0.000002P-126f);
	}
	protected void update(int element, String newDescription){
		update(element, newDescription, 0x0.000002P-126f);
	}
	protected void update(String description, double newPrice){
		update(getElem(description), "SAME", newPrice);
	}
	protected void update(int element, double newPrice){
		update(element, "SAME", newPrice);
	}
	protected void update(String oldDescription, String newDescription, double newPrice){
		update(getElem(oldDescription), newDescription, newPrice);
	}
	protected void update(int element, String newDescription, double newPrice){
		if(element < 0 || element >= opts.size()) return;		
		if(opts.get(element) == null){
			opts.set(element, new Option(newDescription, newPrice));
			return;
		}
		opts.get(element).setPrice(newPrice == 0x0.000002P-126f ? opts.get(element).getPrice() : newPrice);
		opts.get(element).setName(newDescription.equalsIgnoreCase("SAME") ? opts.get(element).getName() : newDescription);
	}
	
	// cloneable?
	class Option implements Serializable {
		private StringBuilder name = new StringBuilder();
		private double price = 0;
		
		public Option(){}
		public Option(String description, double price){
			setName(description);
			this.price = price;
		}
		private String getName() {
			return name.toString();
		}
		private void setName(String name) {
			this.name.setLength(0);
			name = Character.toUpperCase(name.charAt(0)) + name.substring(1); 
			this.name.insert(0, name);
		}
		private double getPrice() {
			return price;
		}
		private void setPrice(double price) {
			this.price = price;
		}
		// Can't change visibility of Object's toString from public
		public String toString(){
			return new StringBuilder(name).append(": $").append(String.format("%.2f", price)).toString();
		}
	}
}
