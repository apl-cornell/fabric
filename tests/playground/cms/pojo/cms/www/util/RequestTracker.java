/*
 * Created on Feb 21, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cms.www.util;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.*;

import org.w3c.dom.*;

import cms.www.xml.XMLBuilder;


/**
 * @author jfg32
 *
 * This class encapsulates a the call path of a single request as it
 * runs through the system.
 */
public class RequestTracker {
	
	class RequestEntry {
		public final static int IN = 0, OUT = 1;
		
		int type;
		String methodName, context;
		long time, period;
		float percent;
		public RequestEntry(String methodName, String context, int type) {
			this.type = type;
			this.methodName = methodName;
			this.context = context;
			this.time = System.currentTimeMillis();
		}
	}
	
	// The most recently called method should be at the front of this list
	private LinkedList outstandingCalls;
	// Stores information about each individual method call
	private ArrayList calls;
	// True after compute() has been called
	boolean computed;
	
	public RequestTracker() {
		outstandingCalls = new LinkedList();
		calls = new ArrayList();
		computed = false;
	}
	
	public void enterMethod(String methodName, String context) {
		RequestEntry entry = new RequestEntry(methodName, context, RequestEntry.IN);
		outstandingCalls.addFirst(entry);
		calls.add(entry);
	}

	public void exitMethod(String methodName, String context) {
		RequestEntry entry = new RequestEntry(methodName, context, RequestEntry.OUT);
		RequestEntry firstCall = (RequestEntry) outstandingCalls.getFirst();
		calls.add(entry);
		if (firstCall == null || !entry.methodName.equals(firstCall.methodName)) {
			System.out.println("Error: profiler received unexpected exit from method " + methodName +
			        ((firstCall == null) ? " -- no methods are on the stack." : " -- expecting an exit from this method first: " + firstCall.methodName));
			return;
		}
		outstandingCalls.removeFirst();
		entry.period = entry.time - firstCall.time;
	}
	
	public void compute() {
		computed = true;
		RequestEntry finalEntry = (RequestEntry) calls.get(calls.size() - 1);
		if (finalEntry == null) return;
		long totaltime = finalEntry.period;
		for (int i=0; i < calls.size(); i++) {
			RequestEntry entry = (RequestEntry) calls.get(i);
			if (entry.type == RequestEntry.OUT) {
				entry.percent = 100.0f * ((float) entry.period) / ((float) totaltime);
			}
		}
	}
	
	public void output(Document xml) {
		if (!computed) compute();
		Element root = (Element) xml.getFirstChild();
		Element request = xml.createElement(XMLBuilder.TAG_REQUESTPROFILE);
		for (int i=0; i < calls.size(); i++) {
			RequestEntry entry = (RequestEntry) calls.get(i);
			Element method = xml.createElement(XMLBuilder.TAG_METHODCALL);
			method.setAttribute(XMLBuilder.A_METHODNAME, entry.methodName);
			method.setAttribute(XMLBuilder.A_CONTEXT, entry.context);
			method.setAttribute(XMLBuilder.A_TIME, new Timestamp(entry.time).toString());
			if (entry.type == RequestEntry.IN) {
				method.setAttribute(XMLBuilder.A_TYPE, "IN");
			} else {
				method.setAttribute(XMLBuilder.A_TYPE, "OUT");
				method.setAttribute(XMLBuilder.A_TIMEPERIOD, entry.period + " ms");
				method.setAttribute(XMLBuilder.A_PERCENT, StringUtil.roundToOne(String.valueOf(entry.percent)) + "%");
			}
			request.appendChild(method);
		}
		root.appendChild(request);
	}
	
}
