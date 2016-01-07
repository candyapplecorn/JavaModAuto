package scale;

import model.ModAuto;
import adapter.ProxyModAuto;

public class EditOptions extends ProxyModAuto implements ScaleThread, Runnable {

	private Thread t;
	private boolean executed = false;
	private int arg;
	private String[] args;
	
	public EditOptions(int arg, String args){
		t = new Thread(this);
		this.arg = arg;
		this.args = args.split(",\\s*");
	}
	
	public void run() {
		if (!executed){
			switch(arg){
				case 1: ThreadedupdateOptionPrice(); break;
				case 2: ThreadedSetOptionChoice(); break;
				case 3: ThreadedUpdateOptionSetName(); break;
			}
			executed = true;
		}
	}
	
	public void start() {
		t.start();
	}
	
	public void stop() {
		executed = true;
	}
	
	// Since this uses a thread but isn't a thread, must wrap t's isAlive()
	public boolean isAlive(){ return t.isAlive(); }
	
	/*
	 * Synchronized methods
	 */
	
	private void ThreadedUpdateOptionSetName(){
		ModAuto ma;
		if ( (ma = f.getModAutoByName(args[0]) ) != null )
			synchronized(ma){
				this.updateOptionSetName(args[0], args[1], args[2]);
			}
	}
	
	private void ThreadedupdateOptionPrice(){
		int index;
		ModAuto ma;
		if ( (ma = f.getModAutoByName(args[0]) ) != null )
				if( (index = ma.getElem(args[1]) ) != -1)
					synchronized(ma){
						ma.updateOptionPrice(index, args[2], Double.parseDouble(args[3]));
					}
	}
	
	private void ThreadedSetOptionChoice(){
		ModAuto n = f.getModAutoByName(args[0]);
		synchronized(n){
			if (n != null) {
				n.setOptionChoice(args[1], args[2]);
				
				// In order to demonstrate that threading and synchronization are working correctly for lab 4, I
				// added this piece of code, as well as "randomWait()", copy and pasted from SamplePrograms.txt
				// If I don't synchronize System.out, it gets mixed with main's System.out. If neither n nor System.out
				// were synchronized, the output would be very messy
				synchronized(System.out){
					for(String s : args){
						for (int i = 0, x = s.length(); i < x; randomWait(), i++)
							System.out.print(s.charAt(i));
						System.out.print(" ");
					}
					System.out.print("\n");
				} // End synchronized(system.out)
			} // end if
		} // end synchronized(n)
	}
	
	// randomWait is a helper function to demonstrate multithreading in lab4 and will be removed for lab 5 and beyond.
	@SuppressWarnings("static-access")
	private void randomWait() {
        try {
            Thread.currentThread().sleep((long)(100*Math.random()));
        } catch(InterruptedException e) {
            System.out.println("Interrupted!");
        }
    }   

}
