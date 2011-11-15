package jif.types.label;

import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.hierarchy.LabelEnv;
import jif.visit.LabelChecker;
import polyglot.util.Position;


/**
 * Represents a final access path root.
 * 
 * @see jif.types.label.AccessPath
 */
public abstract class AccessPathRoot extends AccessPath {
    protected AccessPathRoot(Position pos) {
        super(pos);    
    }
    
    public boolean isUninterpreted() {
        return false;
    }

    public final AccessPathRoot root() {
        return this;
    }
    
    public PathMap labelcheck(JifContext A, LabelChecker lc) {
        throw new UnsupportedOperationException("Cannot labelcheck an " + this.getClass());
    }
    public boolean equivalentTo(AccessPath p, LabelEnv env) {
        return (this.equals(p));
    }

}
