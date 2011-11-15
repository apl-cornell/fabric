package jif.ast;

import jif.translate.ToJavaExt;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

/** This class represents a Jif extension node. It is the root of all
 *  the Jif extension node classes. 
 */
public interface Jif extends Ext
{
    ToJavaExt toJava();
    Jif toJava(ToJavaExt toJava);

    /** Gets the path map of this extension. */
    PathMap X();
    
    /** Returns a copy of this object with the path map updated. */
    Jif X(PathMap X);

    /** Label check the node to which this extension is attached. */
    Node labelCheck(LabelChecker lc) throws SemanticException;
    
    /**
     * A method that is called to notify the extension that
     * the numeric bounds have been calculated.
     */
    void integerBoundsCalculated();    
}
