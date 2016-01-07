package scale;

/*
 * ScaleThread is implemented by class EditOptions. Class EditOptions'
 * constructor takes an integer argument telling it which operation to
 * do. It then takes a comma delimited string of arguments which EditOptions
 * parses into an array. The array elements are then casted if necessary and sent
 * to the chosen operation as parameters.
 * 
 * Supported Operations List:
 * OP CODE		Called Function:
 * 1			updateOptionPrice(String Modelname, String Optionname, String Option, float newprice)
 * 2			setOptionChoice(String modelname, String setName, String optionName)
 * 3			ThreadedUpdateOptionSetName(String modelname, String setName, String newName) <-- Updates a field used as a key
 */
public interface ScaleThread {
	// Interfaces can't declare a constructor, so it's commented out
	// EditOptions(int operation, String arguments);
}
