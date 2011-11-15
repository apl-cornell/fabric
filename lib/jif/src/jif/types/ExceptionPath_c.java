package jif.types;

import polyglot.types.*;

/** An implementation of the <code>ExceptionPath</code> interface. 
 */
public class ExceptionPath_c implements ExceptionPath
{
    Type type;

    public ExceptionPath_c(Type type) {
	this.type = type;
    }

    public Type exception() {
	return type;
    }

    public String toString() {
	return type.toString();
    }

    public boolean equalsImpl(TypeObject o) {
	if (! (o instanceof ExceptionPath)) {
	    return false;
	}
	else {
	    return this.type.equals(((ExceptionPath) o).exception());
	}
    }

    public int hashCode() {
	return type.hashCode();
    }
}
