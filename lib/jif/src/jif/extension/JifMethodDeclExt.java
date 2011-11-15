package jif.extension;

import java.util.Iterator;
import java.util.List;

import jif.ast.JifMethodDecl;
import jif.ast.JifMethodDecl_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.ArgLabel;
import jif.types.label.Label;
import jif.types.label.ThisLabel;
import jif.visit.LabelChecker;
import polyglot.ast.Block;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.ast.ProcedureDecl;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

/** The Jif extension of the <code>JifMethodDecl</code> node. 
 * 
 *  @see jif.ast.JifMethodDecl
 */
public class JifMethodDeclExt extends JifProcedureDeclExt_c
{
    public JifMethodDeclExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException
    {
        JifMethodDecl mn = (JifMethodDecl) node();
        JifMethodInstance renamedMI = (JifMethodInstance) mn.methodInstance();
        JifMethodInstance mi = JifMethodDecl_c.unrenameArgs(renamedMI);

        // check covariance of labels
        checkCovariance(mi, lc);
        
        // check that the labels in the method signature conform to the
        // restrictions of the superclass and/or interface method declaration.
        overrideMethodLabelCheck(lc, renamedMI);

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) mn.del().enterScope(A);
        lc = lc.context(A);

        // let the label checker know that we are about to enter a method decl
        lc.enteringMethod(mi);

        // First, check the arguments, and adjust the context.
        Label Li = checkEnforceSignature(mi, lc);

        // check formals
        List formals = checkFormals(mn.formals(), mi, lc);

        Block body = null;
        PathMap X;

        if (! mi.flags().isAbstract() && ! mi.flags().isNative()) {
            // Now, check the body of the method in the new context.

            // Visit only the body, not the formal parameters.
            body = (Block) lc.context(A).labelCheck(mn.body());
            X = getPathMap(body);

            if (Report.should_report(jif_verbose, 3))
                Report.report(3, "Body path labels = " + X);

            addReturnConstraints(Li, X, mi, lc, mi.returnType());
        }
        else {
            // for an abstract or native method, just set the 
            // normal termination path to the entry PC of the
            // method.
            X = ts.pathMap();
            X = X.N(A.currentCodePCBound());
        }

        mn = (JifMethodDecl) updatePathMap(mn.formals(formals).body(body), X);

        // let the label checker know that we have left the method
        mn = lc.leavingMethod(mn);

        return mn;
    }

    /**
     * This method checks that covariant labels are not used in contravariant
     * positions.
     * @throws SemanticDetailedException
     *
     */
    protected void checkCovariance(JifMethodInstance mi, LabelChecker lc) throws SemanticDetailedException {
        if (mi.flags().isStatic()) {
            // static methods are ok, since they do not override other methods.
            return;
        }
        ProcedureDecl mn = (ProcedureDecl) node();
        Position declPosition = mn.position();

        // check pc bound
        Label Li = mi.pcBound();
        if (Li.isCovariant()) {
            throw new SemanticDetailedException("The pc bound of a method " +
                                                "can not be the covariant label " + Li + ".",
                                                "The pc bound of a method " +
                                                "can not be the covariant label " + Li + ". " +
                                                "Otherwise, information may be leaked by casting the " +
                                                "low-parameter class to a high-parameter class, and masking " +
                                                "the low side-effects that invoking the method may cause.",
                                                declPosition);
        }

        // check arguments
        JifTypeSystem ts = lc.jifTypeSystem();
        Iterator types = mi.formalTypes().iterator();

        int index = 0;
        while (types.hasNext()) {
            Type tj = (Type) types.next();

            // This is the declared label of the parameter.
            Label argBj = ((ArgLabel)ts.labelOfType(tj)).upperBound();
            if (argBj.isCovariant()) {
                String name = ((Formal)mn.formals().get(index)).name();
                throw new SemanticDetailedException("The method " +
                                                    "argument " + name + 
                                                    " can not be labeled with the covariant label " + argBj + ".",
                                                    "The method argument " + name + 
                                                    " can not be labeled with the covariant label " + argBj + ". " +
                                                    "Otherwise, information may be leaked by casting the " +
                                                    "low-parameter class to a high-parameter class, and calling " +
                                                    "the method with a high security parameter, which the " +
                                                    "method regards as low security information.",
                                                    argBj.position());
            }

            index++;
        }

        // check label constraints
        for (Iterator iter = mi.constraints().iterator(); iter.hasNext(); ) {
            Assertion a = (Assertion)iter.next();
            if (a instanceof LabelLeAssertion) {
                LabelLeAssertion lla = (LabelLeAssertion)a;
                // no covariant labels can occur on the RHS of a constraint,
                // as subtypes may violate this constraint.
                // They may, however, occur on the LHS, since if a supertype
                // satisfies the constraint, then the subtype will too.
                CovariantLabelChecker clc = new CovariantLabelChecker(a.position());
//              !@!                lla.rhs().subst(clc);
            }
        }

    }

    /**
     * Check that this method instance <mi> conforms to the signatures of any
     * methods in the superclasses or interfaces that it is overriding.
     * 
     * In particular, argument labels and pc bounds are contravariant,
     * return labels, return value labels and labels on exception types are 
     * covariant.
     */
    protected void overrideMethodLabelCheck(LabelChecker lc, final JifMethodInstance mi) throws SemanticException {
        JifTypeSystem ts = lc.jifTypeSystem();
        for (Iterator iter = mi.implemented().iterator(); iter.hasNext(); ) {
            final JifMethodInstance mj = (JifMethodInstance) iter.next();

            if (! ts.isAccessible(mj, lc.context())) {
                continue;
            }            
            lc.createOverrideHelper(mj, mi).checkOverride(lc);
        }
    }

    /**
     * Checker to ensure that no covariant label occurs in the label
     */    
    protected static class CovariantLabelChecker extends LabelSubstitution {
        private final Position errPosition;

        CovariantLabelChecker(Position errPosition) {
            this.errPosition = errPosition;
        }
        public Label substLabel(Label L) throws SemanticException {
            if (L instanceof ThisLabel) {
//              throw new SemanticDetailedException("The \"this\" label " +
//              "can not occur on the right hand side of " +
//              "a label constraint.", 
//              "The \"this\" label " +
//              "can not occur on the right hand side of " +
//              "a label constraint, since it is covariant, and " +
//              "subclasses may " +
//              "not satisfy the constraint.",
//              errPosition);
            }
            else if (L.isCovariant()) {
                throw new SemanticDetailedException("Covariant labels " +
                                                    "can not occur on the right hand side of " +
                                                    "a label constraint.", 
                                                    "Covariant labels " +
                                                    "can not occur on the right hand side of " +
                                                    "a label constraint, since subclasses may " +
                                                    "not satisfy the constraint.",
                                                    errPosition);
            }
            return L;
        }

        /**
         * We do not want to check the labelOf components of fields.
         */
        public boolean recurseIntoLabelOf() {
            return false;
        }

    }


}
