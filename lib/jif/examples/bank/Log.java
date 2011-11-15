/*
 Message logging to file (echoed also to console)
 */

import java.io.*;
import java.text.*;
import java.util.*;
class Log {
    FileWriter logfile;
    String basename;
    int sequenceNumber;

    public void fail(String s){
	System.out.println("FAIL: " + s);
	System.exit(1);
    }

    public Log(String basename) throws IOException{
	this.basename = basename;
	sequenceNumber = 0;
	logfile = new FileWriter(basename + ".log", true);
    }

    public synchronized void logMessage(String s){
	try{
	    System.out.println(basename + " LOG: " + s);
	    String timestamp = DateFormat.getDateTimeInstance().format(new Date());
	    logfile.write("" + sequenceNumber + timestamp +": " + s + "\n");
	    logfile.flush();
	    sequenceNumber++;
	} catch(IOException e) {
	    fail("Log file inaccessible:" + e);
	}
    }	
    
    public synchronized int getSequenceNumber() {
	return sequenceNumber;
    }
}
