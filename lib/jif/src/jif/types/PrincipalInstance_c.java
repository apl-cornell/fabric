package jif.types;

import jif.types.principal.ExternalPrincipal;
import polyglot.types.Type;
import polyglot.types.VarInstance_c;
import polyglot.util.Position;

/** An implementation of the <code>PrincipalInstance</code> interface. 
 */
public class PrincipalInstance_c extends VarInstance_c implements PrincipalInstance
{
    ExternalPrincipal principal;

    public PrincipalInstance_c(JifTypeSystem ts, Position pos, ExternalPrincipal p) {
	super(ts, pos, ts.Public().Static().Final(), ts.Principal(), p.name());
	this.principal = p;
    }

    public ExternalPrincipal principal() {
	return principal;
    }

    public PrincipalInstance principal(ExternalPrincipal principal) {
	PrincipalInstance_c n = (PrincipalInstance_c) copy();
	n.principal = principal;
	return n;
    }

    public String toString() {
	return "principal " + name();
    }

    public void setType(Type t) {
    	//do nothing
    }
}
