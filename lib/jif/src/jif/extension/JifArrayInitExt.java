package jif.extension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.ast.JifUtil;
import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;

/** The Jif extension of the <code>ArrayInit</code> node. 
 */
public class JifArrayInitExt extends JifExprExt
{
    public JifArrayInitExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException
    {
        ArrayInit init = (ArrayInit) node();

        JifTypeSystem ts = lc.jifTypeSystem();


        JifContext A = lc.jifContext();
        A = (JifContext) init.del().enterScope(A);

        A = (JifContext) A.pushBlock();

        PathMap X = ts.pathMap();
        X = X.N(A.pc());

        List l = new ArrayList(init.elements().size());

        for (Iterator i = init.elements().iterator(); i.hasNext(); ) {
            Expr e = (Expr) i.next(); 
            e = (Expr) lc.context(A).labelCheck(e);
            l.add(e);

            PathMap Xe = getPathMap(e);
            X = X.N(ts.notTaken()).join(Xe);

            A.setPc(X.N(), lc);
        }

        A = (JifContext) A.pop();

        return updatePathMap(init.elements(l), X);
    }

    public void labelCheckElements(LabelChecker lc, Type lhsType) throws SemanticException {
        ArrayInit init = (ArrayInit) node();

        // Check if we can assign each individual element.
        Type t = lhsType.toArray().base();
        Label L = null;
        if (lc.typeSystem().isLabeled(t)) {
            L = lc.typeSystem().labelOfType(t);
        }

        for (Iterator i = init.elements().iterator(); i.hasNext(); ) {
            Expr e = (Expr) i.next();
            Type s = e.type();

            if (e instanceof ArrayInit) {
                ((JifArrayInitExt) JifUtil.jifExt(e)).labelCheckElements(lc, t);
            }

            SubtypeChecker subtypeChecker = new SubtypeChecker(t, s);
            subtypeChecker.addSubtypeConstraints(lc, e.position());

            if (L != null) {
                // check that the element can be assigned to the base type.
                PathMap Xe = getPathMap(e);
                lc.constrain(new NamedLabel("array_init_elem.nv", 
                                            "label of successful evaluation of array element " + e, 
                                            Xe.NV()), 
                            LabelConstraint.LEQ, 
                            new NamedLabel("label of array base type" , L),
                            lc.context().labelEnv(),
                            e.position(),
                            new ConstraintMessage() {
                    public String msg() {
                        return "Label of the array element not less " + 
                        "restrictive than the label of the array base type.";
                    }
                    public String detailMsg() { 
                        return "More information is revealed by the successful " +
                        "evaluation of the intializing expression " +
                        "than is allowed to flow to " +
                        "the array base type.";
                    }
                }
                );
            }

        }        
    }
}
