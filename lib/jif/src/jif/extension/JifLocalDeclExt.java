package jif.extension;

import jif.ast.JifUtil;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.types.label.VarLabel;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.LocalDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;

/** The Jif extension of the <code>LocalDecl</code> node. 
 * 
 *  @see polyglot.ast.LocalDecl
 */
public class JifLocalDeclExt extends JifStmtExt_c
{
    public JifLocalDeclExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        LocalDecl decl = (LocalDecl) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) decl.del().enterScope(A);

        PathMap X = ts.pathMap();
        X = X.N(A.pc());

        JifLocalInstance li = (JifLocalInstance) decl.localInstance();

        if (polyglot.main.Report.should_report(jif.Topics.jif, 4))
            polyglot.main.Report.report(4, "Processing declaration for " + li);

        //deal with the special cases "final label l = new label(...)"
        // and "final principal p = ..."
        if (li.flags().isFinal() && JifUtil.isFinalAccessExprOrConst(ts, decl.init())) { 
            if (ts.isLabel(li.type())) {
                Label dl = ts.dynamicLabel(decl.position(), JifUtil.varInstanceToAccessPath(li, li.position()));                
                Label rhs_label = JifUtil.exprToLabel(ts, decl.init(), lc.context());
                lc.context().addDefinitionalAssertionEquiv(dl, rhs_label);
            }
            else if (ts.isImplicitCastValid(li.type(), ts.Principal())) {
                DynamicPrincipal dp = ts.dynamicPrincipal(decl.position(), JifUtil.varInstanceToAccessPath(li, li.position()));                
                Principal rhs_principal = JifUtil.exprToPrincipal(ts, decl.init(), lc.context());
                lc.context().addDefinitionalEquiv(dp, rhs_principal);                    
            }
            else if (!decl.init().type().isNull()) {
                // we can also add an access path equivalence
                lc.context().addEquiv(JifUtil.varInstanceToAccessPath(li, li.position()),
                                      JifUtil.exprToAccessPath(decl.init(), li.type(), lc.context()));

            }
        }

        // Equate the variable label with the declared label.
        Label L = li.label();
        Type t = decl.declType();
        if (L instanceof VarLabel) {            
            if (ts.isLabeled(t)) {
                Label declaredLabel = ts.labelOfType(t);
                final JifLocalInstance fli = li;
                lc.constrain(new NamedLabel("local_label", 
                                            "inferred label of local var " + li.name(), 
                                            L), 
                            LabelConstraint.EQUAL, 
                            new NamedLabel("PC", 
                                           "Information revealed by program counter being at this program point", 
                                           A.pc()).
                                           join(lc, "declared label of local var " + li.name(), declaredLabel), 
                           A.labelEnv(),
                           decl.position(), 
                           false,
                           new ConstraintMessage() {
                    public String msg() {
                        return "Declared label of local variable " + fli.name() + 
                        " is incompatible with label constraints.";
                    }
                }
                );
            }
            else {
                // Label the type, and update the node and local instance to reflect it.
                t = ts.labeledType(t.position(), t, L);
                li.setType(t);
                decl = decl.type(decl.type().type(t));
            }
        }

        PathMap Xd;
        Expr init = null;

        if (decl.init() != null) {
            init = (Expr) lc.context(A).labelCheck(decl.init());
            decl = decl.init(init);
            decl = finishedInitLabelCheck(decl);
            li = (JifLocalInstance)decl.localInstance();
            t = li.type();

            if (init instanceof ArrayInit) {
                ((JifArrayInitExt)(JifUtil.jifExt(init))).labelCheckElements(lc, decl.type().type()); 
            }
            else {
                // Must check that the expression type is a subtype of the
                // declared type.  Most of this is done in typeCheck, but if
                // they are instantitation types, we must add constraints for
                // the labels.
//                System.err.println("Adding subtype constraints for " + decl.name());
//                System.err.println("     " + init.type() + " <= " + t);
                SubtypeChecker subtypeChecker = new SubtypeChecker(t, init.type());
                subtypeChecker.addSubtypeConstraints(lc, init.position());
            }

            PathMap Xe = getPathMap(init);

            final JifLocalInstance fli = li;
            lc.constrain(new NamedLabel("init.nv", 
                                        "label of successful evaluation of initializing expression", 
                                        Xe.NV()), 
                        LabelConstraint.LEQ, 
                        new NamedLabel("label of local variable " + li.name(), L),
                        A.labelEnv(),
                        init.position(),
                        new ConstraintMessage() {
                public String msg() {
                    return "Label of local variable initializer not less " + 
                    "restrictive than the label for local variable " + 
                    fli.name();
                }
                public String detailMsg() { 
                    return "More information is revealed by the successful " +
                    "evaluation of the intializing expression " +
                    "than is allowed to flow to " +
                    "the local variable " + fli.name() + ".";
                }
                public String technicalMsg() {
                    return "Invalid assignment: NV of initializer is " +
                    "more restrictive than the declared label " +
                    "of local variable " + fli.name() + ".";
                }                     
            }
            );
            Xd = Xe;            
        }
        else {
            Xd = ts.pathMap();
            Xd = Xd.N(A.pc());
        }

        X = X.N(ts.notTaken()).join(Xd);

        decl = (LocalDecl) updatePathMap(decl.init(init), X);
        return decl;
    }

    protected LocalDecl finishedInitLabelCheck(LocalDecl decl) {
//        JifLocalInstance li = (JifLocalInstance)decl.localInstance();
//        li.setType(decl.init().type());
        return decl;
    }
}
