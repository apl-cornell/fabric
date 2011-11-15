package jif.extension;

import jif.ast.JifUtil;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.SemanticDetailedException;
import jif.visit.LabelChecker;
import polyglot.ast.Assign;
import polyglot.ast.Node;
import polyglot.ast.Special;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Assign</code> node. 
 */
public abstract class JifAssignExt extends JifExprExt
{
    public JifAssignExt(ToJavaExt toJava) {
        super(toJava);
    }
    
    public Node labelCheck(LabelChecker lc) throws SemanticException {
        Assign a = (Assign) node();

        JifContext A = lc.jifContext();
        A = (JifContext) a.del().enterScope(A);

        if (A.checkingInits()) {
            // in the constructor prologue, the this object cannot value on the RHS
            if (JifUtil.effectiveExpr(a.right()) instanceof Special) {
                throw new SemanticDetailedException("The \"this\" object cannot be the value assigned in a constructor prologue.", 
                                                    "In a constructor body before the call to the super class, no " +
                                                    "reference to the \"this\" object is allowed to escape. This means " +
                                                    "that the right hand side of an assignment is not allowed to refer " +
                                                    "to the \"this\" object.", 
                                                    a.right().position());
            }
        }

        if (!A.updateAllowed(a.left())) {
            throw new SemanticException("Cannot assign to \"" + a.left() + "\" in this context.", a.left().position());
        }

        Assign checked = (Assign)labelCheckLHS(lc);

        // Only need subtype constraints for "=" operator.  No other
        // assignment operator works with class types.
        if (a.operator() == Assign.ASSIGN) {
            // Must check that the RHS is a subtype of the LHS.
            // Most of this is done in typeCheck, but if lhs and rhs are
            // instantitation types, we must add constraints for the labels.
            SubtypeChecker subtypeChecker = new SubtypeChecker(checked.left().type(),
                                                               checked.right().type());
            subtypeChecker.addSubtypeConstraints(lc, a.position());
        }

        return checked;
    }

    protected abstract Node labelCheckLHS(LabelChecker lc) throws SemanticException;
}
