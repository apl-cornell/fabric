/*
 * Created on Mar 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cms.www;

/**
 * @author Jon
 *
 * This class represents an error which prevented a
 * transaction from completing
 */
public class TransactionError {

	private String error;
	private Exception exception = null;
	private int location = 0;
	
	public TransactionError(String error) {
		this.error = new String(error);
	}
	
	public TransactionError(String error, Exception e) {
		this(error);
		this.exception = e;
	}
	
	public TransactionError(String error, int location) {
		this(error);
		this.location = location;
	}
	
	/**
	 * Same as getError except prepends 'Error: ' to the 
	 * string for purposes of printing to screen
	 * @return
	 */
	public String getMessage() {
		return "Error: " + error;
	}
	
	public String getError() {
		return error;
	}
	
	public boolean hasException() {
		return exception != null;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public void setException(Exception e) {
		this.exception = e;
	}
	
	public void setLocation(int location) {
		this.location = location;
	}
	
	public int getLocation() {
		return location;
	}
	
	public void print()
	{
		System.out.println("TransactionError: " + error);
		if (hasException()) getException().printStackTrace();
	}
}
