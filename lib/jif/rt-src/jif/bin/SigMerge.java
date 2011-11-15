package jif.bin;

import java.io.*;
import java.util.*;

public class SigMerge
{    
    private final static String MARK = "public static final String jlc$CompilerVersion";
    private final static String WHITE = " \t\n";
    
    private static void usage() {
	System.out.println("usage: java jif.bin.SigMerge f1 f2 [f3]");
	System.out.println("f1           file containing the signature");
	System.out.println("f2           java source file");
	System.out.println("f3           output file");
    }
    
    private static List readSig(String sigFile) throws IOException {
	List sig = new LinkedList();
	BufferedReader reader = new BufferedReader(new FileReader(sigFile));
	String line;
	boolean sigStart = false;
	
	while ((line = reader.readLine())!=null) {
	    if (!sigStart && line.indexOf(MARK) != -1) sigStart = true;
	    if (sigStart) {
		sig.add(line);
	    }
	}
	reader.close();
	return sig;
    }
    
    private static boolean endWith(String line, String s) {
	int index = line.lastIndexOf(s);
	if (index == -1) return false;
	
	for (int i = index + 1; i < line.length(); i++) {
	    char c = line.charAt(i);
	    if (WHITE.indexOf(c) == -1) return false;
	}
	return true;
    }
    
    public static boolean isEmpty(String line) {
	for (int i=0; i<line.length(); i++) {
	    if (WHITE.indexOf(line.charAt(i))==-1) return false;
	}
	return true;
    }
    
    public static void main(String[] args) throws IOException
    {
	if (args.length >3 || args.length < 2) {
	    usage();
	    return;
	}
	
	String sigFile = args[0];
	String javaFile = args[1];
	String outputFile;
	if (args.length == 2) outputFile = javaFile;
	else outputFile = args[2];
	
        List sig = readSig(sigFile);
	if (sig.isEmpty()) {
	    System.err.println("signature file does not contain a signature.");
	    return;
	}
	
	List out = new LinkedList();
	List buf = new LinkedList();	
	boolean buffering = false;
	String line;
	BufferedReader reader = new BufferedReader(new FileReader(javaFile));
	while ((line = reader.readLine()) != null) {
	    if (line.indexOf(MARK) != -1) {
		out.addAll(buf);
		buf.clear();
		break;
	    }
	    
	    if (buffering) {
		if (!isEmpty(line)) {
		    out.addAll(buf);
		    buf = new LinkedList();
		}
	    }
	    
	    if (endWith(line, "}")) {
		buffering = true;
	    }
	    	    
	    if (buffering) buf.add(line);
	    else out.add(line);
	}
	
	String lastLine = "";
	
	if (!buf.isEmpty()) {
	    lastLine = (String) buf.get(0);
	    int last = lastLine.indexOf("}");
            lastLine = lastLine.substring(0, last);
	}
	
	if (!isEmpty(lastLine)) out.add(lastLine);
	out.addAll(sig);
	
	reader.close();
	
	BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));	 
	for (Iterator iter = out.iterator(); iter.hasNext(); ) {
	    String str = (String) iter.next();
	    writer.write(str);
	    writer.newLine();
	}
	
	writer.close();
    }
}
