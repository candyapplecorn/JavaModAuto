/*
 * CIS35B - Advanced Java - Lab 5
 * 2015/6/16
 * Joseph Burger
 * 
 * The Client Driver instantiates a CreateClient object, which acts as the client
 * application for the car congfiguration application
 */

package driver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import client.CreateClient;

public class ClientDriver {
	final static boolean DEBUG_MODE = false;
	
	public static void main(String[] args) {
		String strLocalHost = "";
		  try{
		      strLocalHost = 
		        InetAddress.getLocalHost().getHostName();
		  }
		 catch (UnknownHostException e){
		      System.err.println ("Unable to find local host");
		 }
		CreateClient cc = new CreateClient(strLocalHost, 3000, DEBUG_MODE);
		cc.startClient();
	}
}
// Test Run:
/*
-=-		Welcome to AutoClient. Please select an option:		-=-
(1) - Upload a properties file		(3) - Get a list of available Autos	
(2) - Upload an Auto input file		(4) - Configure an Auto

(99) - Quit AutoClient

3
[MODEL]:	Ford Lamborghini Pastaroni Racerbox
[MODEL]:	Focus Wagon ZTW
[MODEL]:	Beast
-=-		Welcome to AutoClient. Please select an option:		-=-
(1) - Upload a properties file		(3) - Get a list of available Autos	
(2) - Upload an Auto input file		(4) - Configure an Auto

(99) - Quit AutoClient

1
Please enter the properties file name, or quit to return to the main menu:	testCar.properties
Auto built!
-=-		Welcome to AutoClient. Please select an option:		-=-
(1) - Upload a properties file		(3) - Get a list of available Autos	
(2) - Upload an Auto input file		(4) - Configure an Auto

(99) - Quit AutoClient

2
Please enter the Auto file name, or quit to return to the main menu:	testauto.txt
Auto built!
-=-		Welcome to AutoClient. Please select an option:		-=-
(1) - Upload a properties file		(3) - Get a list of available Autos	
(2) - Upload an Auto input file		(4) - Configure an Auto

(99) - Quit AutoClient

3
[MODEL]:	Ford Lamborghini Pastaroni Racerbox
[MODEL]:	Focus Wagon ZTW
[MODEL]:	Beast
[MODEL]:	Beast
[MODEL]:	Ford Lamborghini Pastaroni Racerbox
-=-		Welcome to AutoClient. Please select an option:		-=-
(1) - Upload a properties file		(3) - Get a list of available Autos	
(2) - Upload an Auto input file		(4) - Configure an Auto

(99) - Quit AutoClient

4
Please enter a model name. Enter quit to return to menu
Ford Lamborghini Pastaroni Racerbox
[MODEL]              Ford Lamborghini Pastaroni Racerbox
[COLOR]              Red: $0.00, Blue: $0.00, Green: $0.00, Turqoise: $0.00, Silver: $0.00, White: $0.00, Black: $0.00
[AIRBAGS]            Yes: $0.00, No: $-300.00, Filled with sand: $-300.00
[WHEELS]             Gangsta rimz: $400.00, Normally nominal: $0.00, Ghetto ridaz: $250.00
[UPHOLSTERY]         Standard: $0.00, Luxury: $1000.00, Racer: $700.00
[ENGINE]             Big: $2000.00, Small: $-1000.00, Normal: $0.00
[MOONROOF]           Yes: $200.00, No: $0.00

Please enter an option set name, and then an option. Enter quit to quit.
OptionSet name:	color

Option:	green
[MODEL]              Ford Lamborghini Pastaroni Racerbox
[COLOR]              Red: $0.00, Blue: $0.00, Green: $0.00, Turqoise: $0.00, Silver: $0.00, White: $0.00, Black: $0.00
[AIRBAGS]            Yes: $0.00, No: $-300.00, Filled with sand: $-300.00
[WHEELS]             Gangsta rimz: $400.00, Normally nominal: $0.00, Ghetto ridaz: $250.00
[UPHOLSTERY]         Standard: $0.00, Luxury: $1000.00, Racer: $700.00
[ENGINE]             Big: $2000.00, Small: $-1000.00, Normal: $0.00
[MOONROOF]           Yes: $200.00, No: $0.00

Please enter an option set name, and then an option. Enter quit to quit.
OptionSet name:	airbags

Option:	filled with sand
[MODEL]              Ford Lamborghini Pastaroni Racerbox
[COLOR]              Red: $0.00, Blue: $0.00, Green: $0.00, Turqoise: $0.00, Silver: $0.00, White: $0.00, Black: $0.00
[AIRBAGS]            Yes: $0.00, No: $-300.00, Filled with sand: $-300.00
[WHEELS]             Gangsta rimz: $400.00, Normally nominal: $0.00, Ghetto ridaz: $250.00
[UPHOLSTERY]         Standard: $0.00, Luxury: $1000.00, Racer: $700.00
[ENGINE]             Big: $2000.00, Small: $-1000.00, Normal: $0.00
[MOONROOF]           Yes: $200.00, No: $0.00

Please enter an option set name, and then an option. Enter quit to quit.
OptionSet name:	wheels

Option:	ghetto ridaz
[MODEL]              Ford Lamborghini Pastaroni Racerbox
[COLOR]              Red: $0.00, Blue: $0.00, Green: $0.00, Turqoise: $0.00, Silver: $0.00, White: $0.00, Black: $0.00
[AIRBAGS]            Yes: $0.00, No: $-300.00, Filled with sand: $-300.00
[WHEELS]             Gangsta rimz: $400.00, Normally nominal: $0.00, Ghetto ridaz: $250.00
[UPHOLSTERY]         Standard: $0.00, Luxury: $1000.00, Racer: $700.00
[ENGINE]             Big: $2000.00, Small: $-1000.00, Normal: $0.00
[MOONROOF]           Yes: $200.00, No: $0.00

Please enter an option set name, and then an option. Enter quit to quit.
OptionSet name:	upholstery

Option:	luxury
[MODEL]              Ford Lamborghini Pastaroni Racerbox
[COLOR]              Red: $0.00, Blue: $0.00, Green: $0.00, Turqoise: $0.00, Silver: $0.00, White: $0.00, Black: $0.00
[AIRBAGS]            Yes: $0.00, No: $-300.00, Filled with sand: $-300.00
[WHEELS]             Gangsta rimz: $400.00, Normally nominal: $0.00, Ghetto ridaz: $250.00
[UPHOLSTERY]         Standard: $0.00, Luxury: $1000.00, Racer: $700.00
[ENGINE]             Big: $2000.00, Small: $-1000.00, Normal: $0.00
[MOONROOF]           Yes: $200.00, No: $0.00

Please enter an option set name, and then an option. Enter quit to quit.
OptionSet name:	engine

Option:	big
[MODEL]              Ford Lamborghini Pastaroni Racerbox
[COLOR]              Red: $0.00, Blue: $0.00, Green: $0.00, Turqoise: $0.00, Silver: $0.00, White: $0.00, Black: $0.00
[AIRBAGS]            Yes: $0.00, No: $-300.00, Filled with sand: $-300.00
[WHEELS]             Gangsta rimz: $400.00, Normally nominal: $0.00, Ghetto ridaz: $250.00
[UPHOLSTERY]         Standard: $0.00, Luxury: $1000.00, Racer: $700.00
[ENGINE]             Big: $2000.00, Small: $-1000.00, Normal: $0.00
[MOONROOF]           Yes: $200.00, No: $0.00

Please enter an option set name, and then an option. Enter quit to quit.
OptionSet name:	moonroof

Option:	yes
[MODEL]              Ford Lamborghini Pastaroni Racerbox
[COLOR]              Red: $0.00, Blue: $0.00, Green: $0.00, Turqoise: $0.00, Silver: $0.00, White: $0.00, Black: $0.00
[AIRBAGS]            Yes: $0.00, No: $-300.00, Filled with sand: $-300.00
[WHEELS]             Gangsta rimz: $400.00, Normally nominal: $0.00, Ghetto ridaz: $250.00
[UPHOLSTERY]         Standard: $0.00, Luxury: $1000.00, Racer: $700.00
[ENGINE]             Big: $2000.00, Small: $-1000.00, Normal: $0.00
[MOONROOF]           Yes: $200.00, No: $0.00

Please enter an option set name, and then an option. Enter quit to quit.
OptionSet name:	quit
Selected options for model Ford Lamborghini Pastaroni Racerbox
Selected Option Choices: 
[MODEL]:	Ford Lamborghini Pastaroni Racerbox
[COLOR]:	Green: $0.00
[AIRBAGS]:	Filled with sand: $-300.00
[WHEELS]:	Ghetto ridaz: $250.00
[UPHOLSTERY]:	Luxury: $1000.00
[ENGINE]:	Big: $2000.00
[MOONROOF]:	Yes: $200.00
Total options price: $3150.00
-=-		Welcome to AutoClient. Please select an option:		-=-
(1) - Upload a properties file		(3) - Get a list of available Autos	
(2) - Upload an Auto input file		(4) - Configure an Auto

(99) - Quit AutoClient

99
*/