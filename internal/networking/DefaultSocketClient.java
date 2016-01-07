package networking;

import java.net.*;
import java.io.*;

public abstract class DefaultSocketClient extends Thread implements
		SocketClientInterface, SocketClientConstants {
	protected ObjectInputStream reader;
	protected ObjectOutputStream writer;
	protected Socket sock;
	protected String strHost;
	protected int iPort;

	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}// constructor

	public void run() {
		if (DEBUG) System.out.println("Running DSC.run()");
		openConnection();
	}

	public boolean openConnection() {

		if (sock == null) {
			if (DEBUG)
				System.out.println("Socket is null - Making a new one with "
						+ strHost + " on port " + iPort);
			try {
				sock = sock == null ? new Socket(strHost, iPort) : sock;
			} catch (IOException socketError) {
				if (DEBUG)
					System.err.println("Unable to connect to " + strHost);
				return false;
			}
		}

		try {
			if (DEBUG)
				System.out.println("Trying to esablish writer");
			writer = new ObjectOutputStream(sock.getOutputStream());
			if (DEBUG)
				System.out.println("Trying to esablish reader on port" + this.iPort);
			reader = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream to/from " + strHost);
			return false;
		}
		if (DEBUG)
			System.out.println("Stream set up successfully");
		return true;
	}

	public void handleSession() {
		Object o;
		if (DEBUG)
			System.out
					.println("Handling session with " + strHost + ":" + iPort);
		try {
			while ((o = reader.readObject()) != null) {
				if (DEBUG)
					System.out.println("Received some input!");
				handleInput(o);
			}
		} catch (IOException | ClassNotFoundException e) {
			if (DEBUG)
				System.err.println("Handling session with " + strHost + ":"
						+ iPort + e.toString());
			//System.exit(1); This causes the server to crash
		}
	}

	/*
	 * Default Socket Client's job is to make setting up streams
	 * "under the hood" - It does it when its run method is called. In that
	 * vein, I decided to wrap reading and writing as well, so that I could
	 * place exception handling "under the hood". By doing it here rather than
	 * in other code, I can keep the rest of my code unlittered by try/catch
	 * blocks. The disadvantage is that now all exception handling is the same -
	 * And so to aid debugging my catch blocks print the error message before
	 * exiting. Exiting is important - if I don't, finding a problem becomes
	 * more difficult because they remain hidden.
	 */

	// Exchange takes some input and returns output - Used by client.
	public Object Exchange(Object input) {
		Write(input);
		return Read();
	}

	/*
	 * Write simply wraps around objectStreamWriter.write so that exception
	 * handling can be moved to DSC.
	 */
	public void Write(Object input) {
		try {
			writer.writeObject(input);
		} catch (IOException e) {
			System.err
					.println("Error in Default Socket Client's Write method.");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 * Wrapping reader's read functions makes code outside of DSC less messy by
	 * hiding the try catch statements inside the parent class
	 */
	public Object Read() {
		try {
			return reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err
					.println("Error in Default Socket Client's Write method.");
			e.printStackTrace();
			System.exit(1);
			return null; // Won't compile without this seemingly useless
							// statement.
		}
	}
	// Client and Server both implement handleInput differently, so I thought, in
	// addition to overloading, why not get fancy and make it abstract.
	public abstract void handleInput(Object o);

	public void closeSession() {
		if (DEBUG)
			System.out.println("\tClosing Session");
		try {
			writer = null;
			reader = null;
			sock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}
	}

	public void setHost(String strHost) {
		this.strHost = strHost;
	}

	public void setPort(int iPort) {
		this.iPort = iPort;
	}
}