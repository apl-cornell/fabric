import java.lang.*;
import java.io.*;

/* The time class invokes the accrue method of the bank once each "day" */

class Time implements Runnable {
    public static final int DAYLENGTH = 5000;
    private Bank bank;

    public Time(Bank bank) {
	this.bank = bank;
    }

    public void run() {
	while (true) {
	    try {
		Thread.currentThread().sleep(DAYLENGTH);
	    } catch (InterruptedException ie) {
	    }
	    bank.accrue();
	}
    }
}
