package jif.types;

import polyglot.ast.*;

/** The control flow path derived from GOTO like statements.
 *  Jif only has two GOTO like statements: <code>break</code>
 *  and <code>continue</code>
 */
public class GotoPath_c implements Path {
    final String label; // Name of the target label, null if none
    final Branch.Kind kind;

    public GotoPath_c(Branch.Kind kind, String label) {
	this.kind = kind;
	this.label = label;
    }

    public String toString() {
	return (kind == Branch.BREAK ? "break " : "continue ")
	    + label;
    }

    public boolean equals(Object o) {
	if (! (o instanceof GotoPath_c)) {
	    return false;
	}

	GotoPath_c that = (GotoPath_c) o;

	if (this.kind != that.kind) {
	    return false;
	}

	if (this.label == null) {
	    return that.label == null;
	}
	else {
	    return this.label.equals(that.label);
	}
    }

    public int hashCode() {
	if (label == null) {
	    return kind.hashCode();
	}
	else {
	    return kind.hashCode() + label.hashCode();
	}
    }
}

