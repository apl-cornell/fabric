package jif.extension;

import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.Local;
import polyglot.ast.Node;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;

/** The Jif extension of the <code>Local</code> node. 
 * 
 *  @see polyglot.ast.Local
 */
public class JifLocalExt extends JifExprExt
{
    public JifLocalExt(ToJavaExt toJava) {
        super(toJava);
    }


    public Node labelCheckIncrement(LabelChecker lc) throws SemanticException
    {
        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();

        Local lve = (Local) node();

        final LocalInstance li = lve.localInstance();
        PathMap X = A.pathMapForLocal(li, lc);
        Label L = ts.labelOfLocal(li, A.pc());

        // original constraint was X.NV() <= L
        // simplified to the equivalent constraint A.pc() <= L
        // (equivalent because X.NV == A.pc() join L)
        lc.constrain(new NamedLabel("pc", 
                                    "information revealed by reaching this program point", 
                                    A.pc()), 
                    LabelConstraint.LEQ, 
                    new NamedLabel("label of local variable " + li.name(), L),
                    A.labelEnv(),
                    lve.position(),
                    new ConstraintMessage() {
            public String msg() {
                return "Program counter at increment " + 
                "more restrictive than the label for " + 
                "local variable " + li.name();
            }
            public String detailMsg() { 
                return "More information may be revealed by the program " +
                "reaching this program point " +
                "than is allowed to flow to " +
                "the local variable " + li.name() + ". Because " +
                li.name() + " is updated at this program point, " +
                "the value of " + li.name() + " can reveal " +
                "information at level A.pc. But this is more " +
                "information than is allowed to flow to " + 
                li.name() + ".";
            }
            public String technicalMsg() {
                return "Invalid increment: [Xe.nv <= label(" + li.name() + 
                ")] is not satisfied.";
            }                     
        }
        );

        return updatePathMap(lve, X);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException
    {
        JifContext A = lc.jifContext();

        Local lve = (Local) node();

        LocalInstance li = lve.localInstance();

        PathMap X = A.pathMapForLocal(li, lc);

        return updatePathMap(lve, X);
    }
}
