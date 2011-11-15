package jif.extension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.ast.JifUtil;
import jif.types.JifTypeSystem;
import jif.visit.IntegerBoundsChecker;
import polyglot.ast.Expr;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.visit.TypeChecker;


public class JifNewArrayDel extends JifJL_c
{
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        NewArray na = (NewArray)super.typeCheck(tc);
        if (na.type().isArray()) {
            // strip off the label of the base type, and replace them with variables, and
            // replace the array types with array types that are both const and non-const
            JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();            
            na = (NewArray)na.type(JifArrayInitDel.relabelBaseType(na.type().toArray(), ts));
            
        }
        return na;
    }
    /** 
     *  List of Types of exceptions that might get thrown.
     * 
     * This differs from the method defined in NewArray_c in that it does not
     * throw a negative array size exception if the indices is guaranteed to be 
     * non-null
     */
    public List throwTypes(TypeSystem ts) {
        List l = new ArrayList(1);
        if (!noNegArraySizeExcThrown()) {
            try {
                l.add(ts.typeForName("java.lang.NegativeArraySizeException"));
            }
            catch (SemanticException e) {
                throw new InternalCompilerError("Cannot find class java.lang.NegativeArraySizeException", e);
            }
        }
        return l;
    }
    
    private boolean noNegArraySizeExcThrown = false;
    public void setNoNegArraySizeExcThrown() {
        noNegArraySizeExcThrown = true;
    }
    public boolean noNegArraySizeExcThrown() {
        return noNegArraySizeExcThrown;
    }
}
