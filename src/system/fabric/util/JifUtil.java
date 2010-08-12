package fabric.util;
import fabric.lang.security.Label;
import fabric.lang.Object;
import fabric.lang.JifObject;
import fabric.lang.JifWrappedObject;

public class JifUtil {	
	
	public static int hashCode(Label lbl, Object o) {
		return o.hashCode();
	}

	public static String toString(Label lbl, Object o) {
		return o.toString();
	}
	
	public static boolean equals(Object o1, Label lbl, Object o2) {
		return o1.equals(o2);
	}
	
	public static Object unwrap(JifObject jobj) {
		if(jobj instanceof JifWrappedObject)
			return ((JifWrappedObject)jobj).unwrap();
		else 
			return (Object)jobj;
	}
}
