package exception;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.HashMap;


//The exceptions for lab 2 arise from incorrect file names (a string), missing
// values (strings or doubles), or index out of bounds exceptions (some element
// wasn't found). So to tackle this problem, FixAutoException prompts the user
// through the console for valid information. This valid information is then stored
// inside of fixDouble and/or fixString as required.
@SuppressWarnings("serial")
public class FixAutoException extends AutoException {
	
	private double fixDouble, extraD; // Will hold the new value to fix a problem with
	private String fixString, extraS; // Will hold the new value to fix a problem with
	private boolean fixed = false; // If FixAutoException was able to fix the problem, true, else false.
	private Object m; // m will hold a modAuto object with which to call methods via reflection
	
	private HashMap<Integer, String> exceptions = new HashMap<Integer, String>();
	
	// Constructors
	public FixAutoException(){
		super();
		initExceptions();
	}
	public FixAutoException(AutoException e){
		super();
		initExceptions();
		fixed = fix(e.getErrorno()) == 1 ? true : false;
	}
	public FixAutoException(String errormsg) {
		super(errormsg);
		initExceptions();
	}
	public FixAutoException(int errorno) {
		super(errorno);
		initExceptions();
		fixed = fix(errorno) == 1 ? true : false;
	}
	public FixAutoException(int errorno, String errormsg) {
		super(errorno, errormsg);
		initExceptions();
		fixed = fix(errorno) == 1 ? true : false;
	}
	public FixAutoException(int errorno, String errormsg, Object o) {
		super(errorno, errormsg);
		initExceptions();
		m = o;
		fixed = fix(errorno) == 1 ? true : false;
	}
	public FixAutoException(int errorno, String errormsg, Object o, String extrastring, Double extradouble) {
		super(errorno, errormsg);
		initExceptions();
		extraD = extradouble; // Extra information for o's methods
		extraS = extrastring; // Extra information for o's methods
		m = o;
		fixed = fix(errorno) == 1 ? true : false;
	}
	
	// initExceptions fills the hashmap "exceptions" with values. Since it's called by constructors,
	// I put it with the constructors.
	private void initExceptions(){
		exceptions.put(15, "File not found. Please enter a valid file name:");
		exceptions.put(31, "The OptionSet is missing a name. Please enter a name:");
		exceptions.put(32, "The option is missing a name. Please enter a name:");
		exceptions.put(33, "The ModAuto is missing a name. Please enter a name:");
		exceptions.put(35, "The file is missing an auto name directive, enter a name:");
		exceptions.put(34, "The ModAuto is missing a name and price. Please enter both:");
		exceptions.put(30, "The option is missing a price. Please enter a double");
	}
	
	// Accessors/Mutators
	public double getFixDouble() {
		return fixDouble;
	}
	public void setFixDouble(double fixDouble) {
		this.fixDouble = fixDouble;
	}
	public String getFixString() {
		return fixString;
	}
	public void setFixString(String fixString) {
		this.fixString = fixString;
	}
	public boolean isFixed() {
		return fixed;
	}
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	// fix calls an appropriate helper function to fix the exception, or
	// returns -1 if no solution is available
	private int fix(int errno){		
		return 	errno == 15 ? FixFileNotFoundException() : 	
				errno == 31 ? FixMissingOptionSetName() :		
				errno == 32 ? FixMissingOptionName() :		
				errno == 33 ? FixMissingAutoName() :		
				errno == 34 ? FixMissingOptionNameAndPrice() : 
				errno == 30 ? FixMissingOptionPrice() : 
				errno == 35 ? FixMissingAutoName() : -1;	
	}
	private int FixFileNotFoundException(){
		return consoleStringInput(exceptions.get(15));
	}
	private int FixMissingOptionSetName(){
		int code = consoleStringInput(exceptions.get(31));
		try {
			m.getClass().getMethod("addOptionSet", String.class).invoke(m, getFixString());
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		finally{}
		return code;
	}
	private int FixMissingOptionName(){
		int code = consoleStringInput(exceptions.get(32));
		try {
			Class [] args = {int.class, String.class, double.class};
			m.getClass().getMethod("createOption", args).invoke(m, 
					((int) m.getClass().getMethod("length", null).invoke(m, null) ) - 1,
					(java.lang.String) getFixString(),
					(double) extraD
					);
		
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		finally{}
		return code;
	}
	private int FixMissingOptionPrice(){
		int code = consoleDoubleInput(exceptions.get(30));
		try {
			Class [] args = {int.class, String.class, double.class};
			m.getClass().getMethod("createOption", args).invoke(m, 
					((int) m.getClass().getMethod("length", null).invoke(m, null) ) - 1,
					extraS,
					getFixDouble()
					);
		
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		finally{}
		return code;
	}
	private int FixMissingOptionNameAndPrice(){
		int code = consoleStringInput("The option is missing a name. Please enter a name:") * 
				consoleDoubleInput("The option is missing a price. Please enter a double");
		try {
			Class [] args = {int.class, String.class, double.class};
			m.getClass().getMethod("createOption", args).invoke(m, 
					((int) m.getClass().getMethod("length", null).invoke(m, null) ) - 1,
					getFixString(),
					getFixDouble()
					);
		
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		finally{}
		return code;
	}
	private int FixMissingAutoName(){
		int code = consoleStringInput(exceptions.get(33));
		try {
			m.getClass().getMethod("setName", String.class).invoke(m, getFixString());
		
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		finally{}
		return code;
	}
	// consoleStringInput and consoleDoubleInput prompt the user for input and store the 
	// user's input in either fixString or fixDouble for later use.
	private int consoleStringInput(String errorMessage){
		if (errorMessage != "") System.out.println(errorMessage);
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    try {
			setFixString(br.readLine());
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	private int consoleDoubleInput(String errorMessage){
		if (errorMessage != "") System.out.println(errorMessage);
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader br = new BufferedReader(isr);
	    try {
			setFixDouble(Double.parseDouble(br.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
}
