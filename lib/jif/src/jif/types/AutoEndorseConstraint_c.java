package jif.types;

import jif.types.label.Label;
import polyglot.types.TypeObject_c;
import polyglot.util.Position;

/** An implementation of the <code>CallerConstraint</code> interface. 
 */
public class AutoEndorseConstraint_c extends TypeObject_c implements AutoEndorseConstraint {
    protected Label endorseTo;

    public AutoEndorseConstraint_c(JifTypeSystem ts, Position pos,
	                        Label endorseTo) {
	super(ts, pos);
	this.endorseTo = endorseTo;
    }

    public AutoEndorseConstraint endorseTo(Label endorseTo) {
	AutoEndorseConstraint_c n = (AutoEndorseConstraint_c) copy();
	n.endorseTo = endorseTo;
	return n;
    }

    public Label endorseTo() {
	return endorseTo;
    }

    public String toString() {
	String s = "autoendorse(";
	s += endorseTo.toString();
	s += ")";
	return s;
    }

    public boolean isCanonical() {
	return true;
    }
}
