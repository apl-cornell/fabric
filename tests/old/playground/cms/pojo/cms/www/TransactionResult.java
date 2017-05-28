/*
 * Created on Mar 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cms.www;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Jon
 * 
 * This class represents the result of a transaction.
 * It keeps track of overall success or failure, as well
 * as any errors that occurred.
 */
public class TransactionResult {
	
	private LinkedList errors = new LinkedList(), //holds TransactionErrors
		warnings = new LinkedList(); //holds Strings
	private Object value = null;
	private Exception e = null;
	
	
	/**
	 * Creates a new TransactionResult
	 * (Default result is a failure)
	 */
	public TransactionResult() {}
	
	/**
	 * Creates a new TransactionResult containing
	 * a single error (success is false)
	 * @param error
	 */
	public TransactionResult(String error) {
		addError(error);
	}
	
	/**
	 * Adds a TransactionError to this TransactionResult
	 * (Null errors are ignored)
	 * @param e The TransactionError to add
	 */
	public void addError(TransactionError e) {
		if (e != null) errors.add(e);
	}
	
	/**
	 * Adds a new TransactionError to this TransactionResult
	 * (Null and empty strings are ignored)
	 * @param error String from which to create a new TransactionError
	 */
	public void addError(String error) {
		if (error != null && error.length() > 0) errors.add(new TransactionError(error));
	}
	
	public void addError(String error, Exception e) {
		errors.add(new TransactionError(error, e));
	}
	
	public void addError(String error, int location) {
		errors.add(new TransactionError(error, location));
	}
	
	public void addWarning(String warning)
	{
		if (warning != null && warning.length() > 0) warnings.add(warning);
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	/**
	 * Add to our error list all the errors r is carrying;
	 * do nothing to r
	 * @param r Another TransactionResult
	 */
	public void appendErrors(TransactionResult r) {
	    Iterator i = r.getErrors().iterator();
	    while (i.hasNext()) {
	        addError((TransactionError) i.next());
	    }
	}
	
	/**
	 * @return All error messages in this result, separated by line breaks (<br>)
	 */
	public String getMessage() {
		String message = "";
		if (errors.size() == 0) {
		    if (value instanceof String) {
		        return (String) value;
		    } else {
		        return message;
		    }
		} else {
			Iterator i = errors.iterator();
			if (errors.size() == 1) {
			    TransactionError err = (TransactionError) i.next();
			    message = err.getError();
		    } else {
		        message = "<ul>";
				while (i.hasNext()) {
					TransactionError err = (TransactionError) i.next();
					message += "<li>" + err.getError() + "</li>";
				}
				message += "</ul>";
		    }
		}
		return message;
	}
	
	/**
	 * @return All warning messages in this result, separated by line breaks (<br>)
	 */
	public String getWarningMessage()
	{
		String message = "";
		if (warnings.size() == 0) {
		    if (value instanceof String) {
		        return (String) value;
		    } else {
		        return message;
		    }
		} else {
			Iterator i = warnings.iterator();
			if (warnings.size() == 1) {
			    message = (String)i.next();
		    } else {
		        message = "<ul>";
				while (i.hasNext()) {
					message += "<li>" + (String)i.next() + "</li>";
				}
				message += "</ul>";
		    }
		}
		return message;
	}
	
	public Collection getErrors() {
		return errors;
	}
	
	public Collection getWarnings()
	{
		return warnings;
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	public boolean hasWarnings()
	{
		return !warnings.isEmpty();
	}
	
	public int numOfErrors() {
		return errors.size();
	}
	
	public boolean getSuccess() {
		return !hasErrors();
	}
	
	public void setException(Exception e) {
		this.e = e;
	}
	
	public Exception getException() {
		return e;
	}
}
