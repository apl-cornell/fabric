/*
 * Created on Feb 21, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cms.www.util;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

//import org.apache.crimson.jaxp.DocumentBuilderFactoryImpl;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import cms.www.xml.XMLBuilder;

import cms.www.AccessController;

/**
 * @author jfg32
 *
 * Utility class for tracking method calls and timing
 */
public class Profiler {
	
	// Maximum number of requests to store in memory
	private final static int MAX_REQUESTS = 100;
	// If true, completed requests are outputted from least to most recent
	private final static boolean ASC_SORT = false;
	
	// Maps thread names to the incomplete RequestTrackers they correspond to 
	private static Hashtable requests = new Hashtable();
	// Set of enabled flags
	private static HashSet flags = new HashSet();
	// Completed RequestTrackers
	private static LinkedList finishedRequests = new LinkedList();
	
	private static DocumentBuilder db;
	
	static {
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {}
	}
	
	public synchronized static void beginAction(String action) {
		if (!AccessController.debug) return;
		Thread thread = Thread.currentThread();
		System.out.println("Using thread: " + thread.getName());
		if (requests.get(thread.getName()) != null) {
			//System.out.println("...www.util.Profiler error: This thread is already fulfilling a request");
			return;
		}
		RequestTracker request = new RequestTracker();
		request.enterMethod("AccessController.handleSpecificAction", "Action: " + action);
		requests.put(thread.getName(), request);
	}
	
	public synchronized static void endAction(String action) {
		if (!AccessController.debug) return;
		Thread thread = Thread.currentThread();
		RequestTracker request = (RequestTracker) requests.get(thread.getName());
		if (request == null) {
			//System.out.println("...www.util.Profiler error: This thread was not found to be fulfilling a request");
			return;
		}
		requests.remove(thread.getName());
		request.exitMethod("AccessController.handleSpecificAction", "Action: " + action);
		if (ASC_SORT) {
			if (finishedRequests.size() == MAX_REQUESTS) {
				finishedRequests.removeFirst();
			}
			finishedRequests.addLast(request);
		} else {
			if (finishedRequests.size() == MAX_REQUESTS) {
				finishedRequests.removeLast();
			}
			finishedRequests.addFirst(request);
		}
	}
	
	public synchronized static void enableFlag(String flag) {
		flags.add(flag);
	}
	
	public synchronized static void disableFlag(String flag) {
		flags.remove(flag);
	}
	
	public synchronized static void enterMethod(String methodName, String context, String ifFlag) {
		if (flags.contains(ifFlag)) enterMethod(methodName, context);
	}
	
	public synchronized static void exitMethod(String methodName, String context, String ifFlag) {
		if (flags.contains(ifFlag)) exitMethod(methodName, context);
	}
	
	public synchronized static void enterMethod(String methodName, String context) {
		if (!AccessController.debug) return;
		Thread thread = Thread.currentThread();
		RequestTracker request = (RequestTracker) requests.get(thread.getName());
		if (request == null) {
			//System.out.println("...www.util.Profiler error: This thread was not found to be fulfilling a request");
			//System.out.println("  " + methodName + ": " + context);
			return;
		}
		request.enterMethod(methodName, context);
	}
	
	public synchronized static void exitMethod(String methodName, String context) {
		if (!AccessController.debug) return;
		Thread thread = Thread.currentThread();
		RequestTracker request = (RequestTracker) requests.get(thread.getName());
		if (request == null) {
			//System.out.println("...www.util.Profiler error: This thread was not found to be fulfilling a request");
			//System.out.println("  " + methodName + ": " + context);
			return;
		}
		request.exitMethod(methodName, context);
	}
	
	/* Writes current profile info into the xml file, with num being the
	 * maximum number of requests to print out */
	public synchronized static Document output(int num) {
		Document xml = db.newDocument();
		Element root = xml.createElement(XMLBuilder.TAG_ROOT);
		xml.appendChild(root);
		int bound = num < finishedRequests.size() ? num : finishedRequests.size();
		for (int i=0; i < bound; i++) {
			RequestTracker request = (RequestTracker) finishedRequests.get(i);
			request.output(xml);
		}
		return xml;
	}
	
}
