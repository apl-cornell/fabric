package jif.extension;

import java.util.Iterator;
import java.util.Set;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.types.SemanticException;
import polyglot.util.Position;

/** The Jif extension of the <code>EndorseExpr</code> node. 
 * 
 *  @see jif.ast.EndorseExpr
 */
public class JifEndorseExprExt extends JifDowngradeExprExt
{
    public JifEndorseExprExt(ToJavaExt toJava) {
        super(toJava);
    }

    protected void checkOneDimenOnly(LabelChecker lc, 
            final JifContext A,
            Label labelFrom, 
            Label labelTo, Position pos) 
    throws SemanticException {
        checkOneDimen(lc, A, labelFrom, labelTo, pos, true, false);
    }
    protected static void checkOneDimen(LabelChecker lc, 
            final JifContext A,
            Label labelFrom, 
            Label labelTo, Position pos,
            boolean isExpr,
            final boolean isAutoEndorse) 
    throws SemanticException {
        final String exprOrStmt = (isExpr?"expression":"statement");
        JifTypeSystem jts = lc.jifTypeSystem();
        Label botIntegLabel = jts.pairLabel(pos, 
                                            jts.topConfPolicy(pos),
                                            jts.bottomIntegPolicy(pos));

        lc.constrain(new NamedLabel(isAutoEndorse?"pcBound":"endorse_from", labelFrom).
                                         meet(lc, "bottom_integ", botIntegLabel), 
                     LabelConstraint.LEQ, 
                     new NamedLabel(isAutoEndorse?"autoendorse_to":"endorse_to", labelTo),
                     A.labelEnv(),       
                     pos,
                     new ConstraintMessage() {
            public String msg() {
                if (isAutoEndorse) return "Auto-endorse cannot downgrade confidentiality.";
                return "Endorse " + exprOrStmt + "s cannot downgrade confidentiality.";
            }
            public String detailMsg() { 
                if (isAutoEndorse) return "The auto endorse label has lower confidentiality than the start label of the method.";
                return "The endorse_to label has lower confidentiality than the " +
                "endorse_from label; endorse " + exprOrStmt + "s " +
                "cannot downgrade confidentiality.";
            }                     
        }
        );
    }

    protected void checkAuthority(LabelChecker lc, 
            final JifContext A,
            Label labelFrom, 
            Label labelTo, Position pos) 
    throws SemanticException {
        checkAuth(lc, A, labelFrom, labelTo, pos, true, false);
    }

    protected static void checkAuth(LabelChecker lc, 
            final JifContext A,
            Label labelFrom, 
            Label labelTo, Position pos,
            boolean isExpr,
            final boolean isAutoEndorse) 
    throws SemanticException {
        Label authLabel = A.authLabelInteg();
        final String exprOrStmt = (isExpr?"expression":"statement");
        lc.constrain(new NamedLabel(isAutoEndorse?"pcBound":"endorse_from", labelFrom).
                                 meet(lc, "auth_label", authLabel),
                     LabelConstraint.LEQ, 
                     new NamedLabel(isAutoEndorse?"autoendorse_to":"endorse_to", labelTo),
                     A.labelEnv(),
                     pos,
                     new ConstraintMessage() {
            public String msg() {
                return "The method does not have sufficient " +
                "authority to " + (isAutoEndorse?"auto-endorse this method":"endorse this " + exprOrStmt) + ".";
            }
            public String detailMsg() { 
                StringBuffer sb = new StringBuffer();
                Set authorities = A.authority();
                if (authorities.isEmpty()) {
                    sb.append("no principals");
                }
                else {
                    sb.append("the following principals: ");
                }
                for (Iterator iter = authorities.iterator(); iter.hasNext() ;) {
                    Principal p = (Principal)iter.next();
                    sb.append(p.toString());
                    if (iter.hasNext()) {
                        sb.append(", ");
                    }
                }

                if (isAutoEndorse) {
                    return "The start label of this method is " + namedLhs() + 
                    ", and the auto-endorse label is " + namedRhs() + 
                    ". However, the method has " +
                    "the authority of " + sb.toString() + ". " +
                    "The authority of other principals is " +
                    "required to perform the endorse.";
                }

                return "The " + exprOrStmt + " to endorse has label " + 
                namedLhs()+ ", and the " + exprOrStmt + " " +
                "should be downgraded to label " + namedRhs() +
                ". However, the method has " +
                "the authority of " + sb.toString() + ". " +
                "The authority of other principals is " +
                "required to perform the endorse.";
            }
            public String technicalMsg() {
                return "Invalid endorse: the method does " +
                "not have sufficient authorities.";
            }                     
        }
        );
    }

    protected void checkRobustness(LabelChecker lc, 
            JifContext A,
            Label labelFrom, 
            Label labelTo, Position pos) 
    throws SemanticException {
        checkRobustEndorse(lc, A, labelFrom, labelTo, pos, true);
    }

    protected static void checkRobustEndorse(LabelChecker lc, 
            JifContext A,
            Label labelFrom, 
            Label labelTo, Position pos, 
            boolean isExpr) 
    throws SemanticException {


        JifTypeSystem jts = lc.typeSystem();
        final String exprOrStmt = (isExpr?"expression":"statement");
        Label pcInteg = lc.upperBound(A.pc(),
                                      jts.pairLabel(pos,
                                                    jts.topConfPolicy(pos),
                                                    jts.bottomIntegPolicy(pos)));        

        lc.constrain(new NamedLabel("endorse_from_label", labelFrom).
                                         meet(lc, "pc_integrity", pcInteg), 
                     LabelConstraint.LEQ, 
                     new NamedLabel("endorse_to_label", labelTo),
                     A.labelEnv(),
                     pos,
                     new ConstraintMessage() {
            public String msg() {
                return "Endorsement not robust: a removed writer " +
                "may influence the decision to " +
                "endorse.";
            }
            public String detailMsg() { 
                return "The endorsement of this " + exprOrStmt + " is " +
                "not robust; at least one of the principals that is " +
                "regarded as no longer influencing the information after " +
                "endorsement may be able to influence the " +
                "decision to endorse.";
            }
        }
        );
    }


}
