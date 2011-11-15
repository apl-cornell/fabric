package jif.ast;

import java.util.Iterator;
import java.util.List;

import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;

/** An implementation of the <code>Jif</code> interface. 
 */
public class Jif_c extends Ext_c implements Jif
{
    protected PathMap X;
    protected ToJavaExt toJava;

    public Jif_c(ToJavaExt toJava) {
        this.toJava = toJava;
    }

    public ToJavaExt toJava() {
        return toJava;
    }

    public void init(Node node) {
        super.init(node);
        toJava.init(node);
    }

    public Jif toJava(ToJavaExt toJava) {
        // Set toJava to null to prevent it from being copied unnecessarily.
        ToJavaExt old = this.toJava;
        this.toJava = null;

        Jif_c copy = (Jif_c) copy();
        copy.toJava = toJava;

        // Restore the old pointer.
        this.toJava = old;

        return copy;
    }

    public Object copy() {
        Jif_c copy = (Jif_c) super.copy();
        if (toJava != null) {
            copy.toJava = (ToJavaExt) toJava.copy();
        }
        return copy;
    }


    public PathMap X() {
	return X;
    }

    public Jif X(PathMap X) {
	Jif_c n = (Jif_c) copy();
	n.X = X;
	return n;
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException {
        JifContext A = lc.jifContext(); 
        A = (JifContext) node().del().enterScope(A);
        return node();
    }

    // Some utility functions used to avoid casts.
    public static PathMap getPathMap(Node n) {
        return JifUtil.getPathMap(n);
    }

    public static Node updatePathMap(Node n, PathMap X) {
        return JifUtil.updatePathMap(n, X);
    }   

    /**
     * Check that the type excType is indeed in the list of types thrown, 
     * throwTypes, and remove excType from that list.
     * @param throwTypes
     * @param excType
     */
    public static void checkAndRemoveThrowType(List throwTypes, Type excType) {
        if (!throwTypes.remove(excType)) {
            throw new InternalCompilerError("The type " + excType + " is not "
                                            + "declared to be thrown!");
        }
    }

    /**
     * Check that the list of types thrown, 
     * throwTypes, does not contain any checked exceptions, i.e., all throw 
     * types have been correctly label checked.
     * @param throwTypes
     */
    public static void checkThrowTypes(List throwTypes) {
        for (Iterator iter = throwTypes.iterator(); iter.hasNext();) {
            Type thrw = (Type)iter.next();
            if (thrw.typeSystem().isUncheckedException(thrw)) {
                iter.remove();
            }            
        }
        if (!throwTypes.isEmpty()) {
            throw new InternalCompilerError("The types " + throwTypes + " are " +
                                            "declared to be thrown, but " +
                                            "are not label checked!");
        }
    }

    public void integerBoundsCalculated() {
        
    }
    
    public void dump(CodeWriter w) {
        if (toJava != null) {
            w.write("(" + toString() + " toJava ");
            toJava.dump(w);
            w.write(")");
        } else {
            super.dump(w);
        }
    }

    
}
