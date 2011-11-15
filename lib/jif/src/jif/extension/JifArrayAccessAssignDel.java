package jif.extension;

import java.util.ArrayList;
import java.util.List;

import jif.types.ConstArrayType;

import polyglot.ast.*;
import polyglot.ast.ArrayAccessAssign;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.visit.TypeChecker;

/** The Jif extension of the <code>ArrayAccessAssign</code> node. 
 */
public class JifArrayAccessAssignDel extends JifAssignDel {
    public JifArrayAccessAssignDel() {}

    /** 
     *  List of Types of exceptions that might get thrown.
     * 
     * This differs from the method defined in ArrayAccess_c in that it does not
     * throw a null pointer exception if the array is guaranteed to be 
     * non-null
     */
    public List throwTypes(TypeSystem ts) {
        ArrayAccessAssign a = (ArrayAccessAssign)node();
        List l = super.throwTypes(ts);
        if (a.throwsArrayStoreException()) {
            l.add(ts.ArrayStoreException());
        }

        if (!((JifArrayAccessDel)a.left().del()).arrayIsNeverNull()) {
            l.add(ts.NullPointerException());
        }

        if (((JifArrayAccessDel)a.left().del()).outOfBoundsExcThrown()) {
            l.add(ts.OutOfBoundsException());
        }

        return l;
    }
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        ArrayAccessAssign aa = (ArrayAccessAssign)super.typeCheck(tc);
        Expr array = ((ArrayAccess)aa.left()).array();
        if (array.type() instanceof ConstArrayType) {
            ConstArrayType cat = (ConstArrayType)array.type();
            if (cat.isConst()) {
                throw new SemanticException("Cannot assign to elements of const arrays.", aa.position());
            }
        }
        return aa;        
    }
    
}
