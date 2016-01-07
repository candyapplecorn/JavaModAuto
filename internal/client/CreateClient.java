/*
 * CreateClient accepts input from the user until terminating
 * the application by entering "99" (or just alt-f4'ing). Input
 * is passed to a switch which determines what actions to take. 
 */
package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CreateClient {
	private SelectCarOption sco;
	private String menuPrompt = "";
	private Scanner in;
	private final int QUIT = 99;

	public CreateClient(String IP, int PORT, boolean DEBUG_MODE) {
		this(IP, PORT);
		sco.setDebug(DEBUG_MODE);
	}

	public CreateClient(String IP, int PORT) {
		sco = new SelectCarOption(new CarModelOptionIO(IP, PORT));
		in = new Scanner(System.in);

		// Java doesn't have HERE docs, but it has text file io!
		String unixPath = "internal/client/ClientMenu.txt";
		boolean triedAlternate = false;
		do{
			try {
				BufferedReader b = new BufferedReader(new FileReader(
						unixPath));
				String line;
				while ((line = b.readLine()) != null)
					menuPrompt += line + "\n";
				b.close();
				break;
			} catch (IOException e) {
				if (!triedAlternate){
					unixPath = unixPath.replaceAll("/", "\\");
					triedAlternate = true;
					continue;
				}
				System.err.println("Menu file missing.");
				System.exit(1);
			}
		} while (!triedAlternate);
	}

	private int SelectServiceOption() {
		System.out.println(menuPrompt);
		int choice = QUIT, flag;
		do {
			flag = 0;
			try {
				choice = in.nextInt();
			} catch (InputMismatchException e) {
				flag = 1;
				System.out.println("Please enter a number (99 to quit).");
			}
		} while (flag != 0);
		return PerformOperation(choice);
	}

	/*
	 * startClient loops until the user elects to quit, or disconnects
	 */
	public void startClient() {
		sco.start();
		while (SelectServiceOption() != QUIT)
			;
	}

	// =============
	// CALL SELECTED SCO METHODS
	private int PerformOperation(int choice) {
		switch (choice) {
		case 1:
			getFile(1);
			return choice;
		case 2:
			getFile(0);
			// Upload a regular auto file
			return choice;
		case 3:
			displayModels(sco.getModels());
			// Get a string of all models
			return choice;
		case 4:
			configureOptions();
			// Get a model object (and then choose its options - submenu)
			return choice;
		case 99:
			// The user is quiting, so call closeConnection
			closeConnection();
		default:
			return choice;
		}
	}

	private void closeConnection() {
		sco.closeConnection();
	}

	// getFile is a helper function that checks to see if a file is valid. It
	// initializes
	// a fileInputStream, which will throw an exception if the filename isn't
	// found. I thought
	// it was a good way to check to see if a file name exists.
	private void getFile(int type) {
		String filename = "";
		boolean flag;
		do {
			flag = false;
			System.out.print("Please enter the "
					+ (type == 1 ? "properties" : "Auto")
					+ " file name, or quit to return to the main menu:\t");
			filename = in.next();
			try {
				// If the filename isn't valid, then the catch clause will activate
				new FileInputStream(filename);
				// Else if it is valid, then add the auto and return.
				if (sco.BuildAuto(filename, type))
					System.out.println("Auto built!");
			} catch (FileNotFoundException e) {
				flag = true;
				System.out.println("File not found: " + filename
						+ "\nEnter quit to return to menu");
			}
		} while (flag && !filename.toLowerCase().matches(".*quit.*"));
	}

	private void displayModels(String unparsed) {
		if (unparsed != null)
			for (String s : unparsed.split(",[ ]?"))
				System.out.println("[MODEL]:\t" + s);
		else
			System.err.println("models is null");
	}

	private void configureOptions() {
		String modelname = "";
		boolean flag;
		do {
			flag = false;
			System.out
			.println("Please enter a model name. Enter quit to return to menu");
			// modelname = in.nextLine();
			while ((modelname = in.nextLine()) == null || modelname.equals("")
					|| modelname.equals("\n"))
				;
			if (modelname.equalsIgnoreCase("quit"))
				return;
			else if (sco.getByName(modelname) == null)
				flag = true;
			else
				sco.addAuto(sco.getByName(modelname));
		} while (flag);

		// Print a message saying it's car configure time
		// Print the options to choose from
		// Let the user select favorite options until user is finished
		// then print the auto's selected options
		String choice = "", optName, optSetName;
		do {
			sco.printAuto(modelname);
			System.out
			.println("Please enter an option set name, and then an option. Enter quit to quit.");
			System.out.print("OptionSet name:\t");

			optSetName = choice = in.nextLine();
			if (choice.toLowerCase().matches(".*quit.*"))
				break;
			System.out.print("\nOption:\t");
			optName = choice = in.nextLine();
			if (choice.toLowerCase().matches(".*quit.*"))
				break;

			sco.setOptionChoice(modelname, optSetName, optName);

		} while (!choice.toLowerCase().matches(".*quit.*"));

		System.out.println("Selected options for model " + modelname);
		System.out.println(sco.getSelectedOptions(modelname));
		System.out.println("Total options price: $" + String.format("%.2f", sco.getTotalPrice(modelname)));
	}

}
