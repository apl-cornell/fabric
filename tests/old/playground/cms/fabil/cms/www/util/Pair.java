/*
 * Created on Nov 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cms.www.util;

/**
 * @author jfg32
 * A pair is a logical ordered pair linking two objects
 */
public class Pair {

	private Object one;
	private Object two;
	
	public Pair(Object one, Object two) {
		this.one = one;
		this.two = two;
	}
	
	public Object one() {
		return one;
	}
	
	public Object two() {
		return two;
	}
	
}
