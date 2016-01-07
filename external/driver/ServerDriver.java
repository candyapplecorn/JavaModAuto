/*
 * CIS35B - Advanced Java - Lab 5
 * 2015/6/16
 * Joseph Burger
 */
/*
The Server Driver instantiates a CreateServer object, which acts as the
server for the car configuration application. It should be run prior to
running ClientDriver. 
*/

package driver;

import adapter.BuildModAuto;
import server.CreateServer;

public class ServerDriver {
	
	public static void main(String[] args) {
		BuildModAuto bma = new BuildModAuto();
		bma.BuildAuto("testauto.txt", 1); 
		bma.BuildAuto("testauto2.txt", 1);
		bma.BuildAuto("testCar.properties", 2);
		
		CreateServer cs = new CreateServer(3000);
		cs.startServer();
	}
}
// Test run:
/*
Waiting to accept on port 3000
ModAutoDefaultSocketClient listening on port3000
Accepted & Started.
Waiting to accept on port 3000
*/