package jif.extension;

import java.util.*;

import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.types.label.NotTaken;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.Formal;
import polyglot.ast.ProcedureDecl;
import polyglot.main.Report;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;

/** The Jif extension of the <code>ProcedureDecl</code> node. 
 * 
 *  @see polyglot.ast.ProcedureDecl
 *  @see jif.types.JifProcedureInstance
 */
public class JifProcedureDeclExt_c extends Jif_c implements JifProcedureDeclExt
{
    public JifProcedureDeclExt_c(ToJavaExt toJava) {
        super(toJava);
    }

    static String jif_verbose = "jif";
    
    /**
     * Label check the formals.
     * @throws SemanticException 
     */
    protected List checkFormals(List formals, JifProcedureInstance ci, LabelChecker lc) throws SemanticException {
        List newFormals = new ArrayList(formals.size());
        boolean changed = false;
        for (Iterator iter = formals.iterator(); iter.hasNext();) {
            Formal formal = (Formal)iter.next();
            Formal newFormal = (Formal)lc.labelCheck(formal);
            if (newFormal != formal) changed = true;
            newFormals.add(newFormal);
        }
        if (changed) return newFormals;
        return formals;
    }

    /**
     * This methods corresponds to the check-arguments predicate in the
     * thesis (Figure 4.37).  It returns the start label of the method.
     * It mutates the local context (to the A'' in the rule).
     */
    protected Label checkEnforceSignature(JifProcedureInstance mi, LabelChecker lc)
    throws SemanticException
    {
        if (Report.should_report(jif_verbose, 2))
            Report.report(2, "Adding constraints for header of " + mi);

        JifContext A = lc.jifContext();
                
        // Set the "auth" variable.
        Set newAuth = constrainAuth(mi, A);

        for (Iterator iter = newAuth.iterator(); iter.hasNext(); ) {
            Principal p = (Principal) iter.next();
            // Check that there is a p' in the old "auth" set such that
            // p' actsFor p.
            checkActsForAuthority(p, A, lc);
        }

        
        addCallers(mi, A, newAuth);
        A.setAuthority(newAuth);       

        constrainLabelEnv(mi, A, null);

        checkConstraintVariance(mi, lc);

        // check that any autoendorse constraints are satisfied,
        // and set and constrain the inital PC
        Label Li = checkAutoEndorseConstrainPC(mi, lc);

        return Li;
    }

    protected Label checkAutoEndorseConstrainPC(JifProcedureInstance mi, LabelChecker lc) throws SemanticException {
        final JifContext A = lc.jifContext();
        JifTypeSystem ts = lc.jifTypeSystem();
        JifClassType ct = (JifClassType) A.currentClass();
        Label Li = mi.pcBound();
        Label endorseTo = ts.topLabel();

        for (Iterator iter = mi.constraints().iterator(); iter.hasNext(); ) {
            Assertion c = (Assertion) iter.next();

            if (c instanceof AutoEndorseConstraint) {
                AutoEndorseConstraint ac = (AutoEndorseConstraint) c;
                endorseTo = ts.meet(endorseTo, ac.endorseTo());
            }
        }

        Label callerPcLabel = ts.callSitePCLabel(mi);
        if (!mi.flags().isStatic())  {
            // for non-static methods, we know the this label
            // must be bounded above by the start label
            A.addAssertionLE(ct.thisLabel(), callerPcLabel);  
        }

        A.setPc(callerPcLabel, lc); 
        Label initialPCBound = Li;

        if (!endorseTo.isTop()) {
            // check that there is sufficient authority to endorse to 
            // the label endorseTo.
            JifEndorseExprExt.checkOneDimen(lc, A, Li, endorseTo, mi.position(), false, true);
            JifEndorseExprExt.checkAuth(lc, A, Li, endorseTo, mi.position(), false, true);            

            // the initial pc bound is the endorseTo label
            initialPCBound = endorseTo;

            // add a restriction on the "callerPC" label. It is less
            // than the endorseTo label
            A.addAssertionLE(callerPcLabel, endorseTo);
        }

        A.setCurrentCodePCBound(initialPCBound);        
        return initialPCBound;

    }

    /**
     * This method corresponds to the constraint-authority predicate in the
     * thesis (Figure 4.39).  It returns the set of principals for which the
     * method can act.
     */
    protected Set constrainAuth(JifProcedureInstance mi, JifContext A) {
        Set newAuth = new LinkedHashSet();

        for (Iterator iter = mi.constraints().iterator(); iter.hasNext(); ) {
            Assertion c = (Assertion) iter.next();

            if (c instanceof AuthConstraint) {
                AuthConstraint ac = (AuthConstraint) c;

                for (Iterator i = ac.principals().iterator(); i.hasNext(); ) {
                    Principal pi = (Principal) i.next();
                    newAuth.add(pi);
                }
            }
        }

        return newAuth;
    }

    /** Adds the caller's authorities into <code>auth</code> */
    protected static void addCallers(JifProcedureInstance mi, JifContext A, Set auth) {
        for (Iterator iter = mi.constraints().iterator(); iter.hasNext(); ) {
            Assertion c = (Assertion) iter.next();

            if (c instanceof CallerConstraint) {
                CallerConstraint cc = (CallerConstraint) c;

                for (Iterator i = cc.principals().iterator(); i.hasNext(); ) {
                    Principal pi = (Principal) i.next();
                    // auth.add(A.instantiate(pi));
                    auth.add(pi);
                }
            }
        }
    }

    /**
     * Check that there is a p' in the old "auth" set such that p' actsFor p.
     */
    protected void checkActsForAuthority(final Principal p, final JifContext A, LabelChecker lc)
    throws SemanticException
    {
        JifTypeSystem ts = (JifTypeSystem)A.typeSystem();
        Principal authority = ts.conjunctivePrincipal(null, A.authority());

        String codeName = A.currentCode().toString();
        if (A.currentCode() instanceof JifProcedureInstance) {
            codeName = ((JifProcedureInstance)A.currentCode()).debugString();
        }
        
        final String msgCodeName = codeName;
        
        lc.constrain(authority, 
                     PrincipalConstraint.ACTSFOR, 
                    p, 
                   A.labelEnv(),
                   A.currentCode().position(),
                   new ConstraintMessage() {
            public String msg() {
                return "The authority of the class " + A.currentClass().name() + 
                " is insufficient to act for principal " + p + ".";
            }
            public String detailMsg() {
                return "The " + msgCodeName + " states that it has the authority of the " +
                "principal " + p + ". However, the conjunction of the authority" +
                " set of the class is insufficient to act for " + p + ".";
            }
        });
    }

    /**
     * This method corresponds to the constraint-ph predicate in the thesis
     * (Figure 4.39).  It returns the principal hierarchy used to check the
     * body of the method.
     */
    protected static void constrainLabelEnv(JifProcedureInstance mi, JifContext A, CallHelper ch)
    throws SemanticException
    {
        for (Iterator i = mi.constraints().iterator(); i.hasNext(); ) {
            Assertion c = (Assertion) i.next();

            if (c instanceof ActsForConstraint) {
                ActsForConstraint ac = (ActsForConstraint) c;
                //A.addActsFor(A.instantiate(ac.actor()),
                //	     A.instantiate(ac.granter()));
                Principal actor = ac.actor();
                Principal granter = ac.granter();
                if (ch != null) {
                    actor = ch.instantiate(A, actor);
                    granter = ch.instantiate(A, granter);
                }

                if (ac.isEquiv()) {
                    A.addEquiv(actor, granter);
                }
                else {
                    A.addActsFor(actor, granter);
                }
            }
            if (c instanceof LabelLeAssertion) {
                LabelLeAssertion lla = (LabelLeAssertion)c;
                Label lhs = lla.lhs();
                Label rhs = lla.rhs();
                if (ch != null) {
                    lhs = ch.instantiate(A, lhs);
                    rhs = ch.instantiate(A, rhs);
                }
                A.addAssertionLE(lhs, rhs); 
            }
        }
    }

    /**
     * This method corresponds to most of the check-body predicate in the
     * thesis (Figure 4.40).  It assumes the body has already been checked
     * and that the path map X is the join of the body's path map and the
     * initial path map of the method.
     *
     * It adds the constraints that associate return termination and
     * return value labels in the path map X with the declared return
     * label and associates the exception labels in the path map X with
     * the declared labels in the methods "throws" clause.
     */
    protected void addReturnConstraints(Label Li, PathMap X,
            JifProcedureInstance mi, LabelChecker lc, final Type returnType) throws SemanticException
            {
        if (Report.should_report(jif_verbose, 2))
            Report.report(2, "Adding constraints for result of " + mi);

        ProcedureDecl mn = (ProcedureDecl) node();
        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();

        // Add the return termination constraints.


        // fold the call site pc into the return label
        Label Lr = lc.upperBound(mi.returnLabel(), ts.callSitePCLabel(mi)); 


        //Hack: If no other paths, the procedure must return. Therefore,
        //X.n is not taken, and X.r doesn't contain any information. 
        //TODO: implement a more precise single-path rule.
        if (! (X.N() instanceof NotTaken)) {
            boolean singlePath = true;
            for (Iterator iter = X.paths().iterator(); iter.hasNext(); ) {
                Path p = (Path) iter.next();
                if (p.equals(Path.N) || p.equals(Path.R)) continue;
                singlePath = false;
                break;
            }
            if (singlePath) {
                X = X.N(ts.notTaken());
                X = X.R(ts.bottomLabel());
            }
        }            

        lc.constrain(new NamedLabel("X.n",
                                    "information that may be gained by the body terminating normally",
                                    X.N()).
                                    join(lc,
                                         "X.r",
                                         "information that may be gained by exiting the body with a return statement",
                                         X.R()),
                     LabelConstraint.LEQ,
                     new NamedLabel("Lr", 
                                    "return label of the method",
                                    Lr),
                    A.labelEnv(),
                    mn.position(),
                    new ConstraintMessage()
        {
            public String msg() { 
                return "The non-exception termination of the " +
                "method body may reveal more information " +
                "than is declared by the method return label.";
            }
            public String detailMsg() { 
                return "The method return label, " + namedRhs() + 
                ", is an upper bound on how much " +
                "information can be gained by observing " +
                "that this method terminates normally " +
                "(i.e., terminates without throwing " +
                "an exception). The method body may " +
                "reveal more information than this. The " +
                "return label of a method is declared " +
                "after the variables, e.g. " +
                "\"void m(int i):{" + namedRhs() + "}\".";
            }
            public String technicalMsg() {
                return "the return (end) label is less restricted than " +
                namedLhs() + " of the body.";
            }
        }
        );

        // return value constraints are implemented at the "return" statement, in order
        // to make use of the (more precise) label environment there.


        // Add the exception path constraints.
        for (Iterator iter = X.paths().iterator(); iter.hasNext(); ) {
            Path path = (Path) iter.next();

            if (! (path instanceof ExceptionPath)) {
                continue;
            }

            ExceptionPath ep = (ExceptionPath) path;

            Label pathLabel = X.get(ep);

            if (pathLabel instanceof NotTaken)
                throw new InternalCompilerError(
                "An exception path cannot be not taken");

            Type pathType = ep.exception();
            NamedLabel pathNamedLabel = new NamedLabel("exc_"+pathType.toClass().name(), 
                                                       "upper bound on information that may be gained " +
                                                       "by observing the method throwing the exception " + pathType.toClass().name(),
                                                       pathLabel);

            for (Iterator j = mi.throwTypes().iterator(); j.hasNext(); ) {
                final Type tj = (Type) j.next();
                Label Lj = ts.labelOfType(tj, Lr);

                // fold the call site pc into the return label
                Lj = lc.upperBound(Lj, ts.callSitePCLabel(mi));

                if (ts.isSubtype(pathType, tj) ||
                        ts.isSubtype(tj, pathType)) {
                    if (ts.isSubtype(pathType, tj)) {
                        SubtypeChecker subtypeChecker = new SubtypeChecker(tj, pathType);
                        subtypeChecker.addSubtypeConstraints(lc, mn.position());
                    }
                    else {

                        SubtypeChecker subtypeChecker = new SubtypeChecker(pathType, tj);
                        subtypeChecker.addSubtypeConstraints(lc, mn.position());                        
                    }
                    if (Report.should_report(jif_verbose, 4))
                        Report.report(4,
                                      ">>> X[C'] <= Lj (for exception " + tj + ")");

                    lc.constrain(pathNamedLabel,
                                 LabelConstraint.LEQ,
                                 new NamedLabel("decl_exc_"+tj.toClass().name(),
                                                "declared upper bound on information that may be " +
                                                "gained by observing the method throwing the exception " + tj.toClass().name(),
                                                Lj),
                                A.labelEnv(),
                                mi.position(),
                                new ConstraintMessage()
                    {
                        public String msg() { 
                            return "More information may be gained " + 
                            "by observing a " + tj.toClass().fullName() +
                            " exception than is permitted by the " +
                            "method/constructor signature";
                        }
                        public String technicalMsg() {
                            return "the path of <" + tj + "> may leak information " +
                            "more restrictive than the join of the declared " +
                            "exception label and the return(end) label";
                        }
                    }
                    );
                }
            }
        }
            }
    
    /**
     * Check that covariant labels do not appear in contravariant positions
     * @param mi
     * @param lc
     * @throws SemanticException 
     */
    protected void checkConstraintVariance(JifProcedureInstance mi, LabelChecker lc) throws SemanticException {
        if (!(lc.context().currentCode() instanceof ConstructorInstance)) {
            for (Iterator iter = mi.constraints().iterator(); iter.hasNext(); ) {
                Assertion c = (Assertion) iter.next();

                if (c instanceof LabelLeAssertion) {
                    LabelLeAssertion lle = (LabelLeAssertion) c;
                    lle.rhs().subst(new ConstraintVarianceLabelChecker(c.position()));
                }
            }
        }
    }

    /**
     * Checker to ensure that labels do not use
     * covariant labels in the wrong places
     */    
    protected static class ConstraintVarianceLabelChecker extends LabelSubstitution {
        private Position declPosition;

        ConstraintVarianceLabelChecker(Position declPosition) {
            this.declPosition = declPosition;
        }
        public Label substLabel(Label L) throws SemanticException {
            if (L.isCovariant()) {
                throw new SemanticDetailedException("Covariant labels cannot occur on the right hand side of label constraints.",
                                                    "The right hand side of a label constraint cannot contain the covariant components such as " + 
                                                    L + ". ",
                                                    declPosition);
            }
            return L;
        }
    }    


}
