/*
 * Created on May 22, 2007
 *
 */
package cms.www.util;

/**
 * @author jfg32
 */
public class CSVParseException extends Exception {
	
	private String message;
	
	public CSVParseException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

}
