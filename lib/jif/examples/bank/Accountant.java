import java.lang.*;
import java.io.*;

class Accountant implements Runnable {
    private Log log;
    private Bank bank;

    // This delay means that the account runs about twice a day
    private static final int INTERVAL = 2000;  // sleep delay (in milliseconds)

    private int checkpoint;  // The last known good sequence number of the bank's log
    private double totalAssets;  // The accountant's view of totalAssets, known to agree with the log.

    public Accountant (Bank bank) throws IOException {
	this.bank = bank;
	this.log = new Log("Accountant");
	this.checkpoint = 0;
	this.totalAssets = bank.getTotalAssets();
    }

    public void run() {
	int seqNumber;
	double currentAssets;
	double bankAssets;
	while (true) {
	    try {
		Thread.currentThread().sleep(INTERVAL);
		log.logMessage("Begining scan from " + checkpoint);
	    } catch (InterruptedException ie) {
	    }
	    // start at the known good sum
	    currentAssets = totalAssets; 
	    // replay the log, accumulating deltas
	    double delta;
	    try {
		// Now start replaying the log, the bank may be adding entries while
		// this loop runs.
		seqNumber = checkpoint;
		while (bank.logAvailable(seqNumber)) {
		    delta = bank.logReplay(seqNumber);
		    log.logMessage("Got delta: " + delta);
		    currentAssets += delta;
		    seqNumber++;
		}
		log.logMessage("Synchronizing with bank...");
		// OK, we've replayed most of the log,
		// lock the bank temporarily to keep it from writing any more entries
		synchronized (bank) {
		    // replay the rest of the log (should be only a few entries)
		    while (bank.logAvailable(seqNumber)) {
			delta = bank.logReplay(seqNumber);
			log.logMessage("Got delta: " + delta);
			currentAssets += delta;
			seqNumber++;
		    }
		    
		    seqNumber = bank.getLogSeqNumber();
		    bankAssets = bank.getTotalAssets();
		    
		    if (bankAssets == currentAssets) {
			log.logMessage("Bank is consistent to " + seqNumber + " assets = " + bankAssets);
			totalAssets = bankAssets;
			checkpoint = seqNumber;
		    } else {
			log.logMessage("Bank is inconsistent at " + seqNumber + " difference = " + (currentAssets - bankAssets));
			// just for sake of liveness
			totalAssets = bankAssets;
			checkpoint = seqNumber;
		    }
		}
	    } catch (Exception e) {
		log.logMessage("Exception : " + e);
	    }
	}
    }

}
