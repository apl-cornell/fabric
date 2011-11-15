package jif.extension;

import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.Branch;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

/** The Jif extension of the <code>Branch</code> node. 
 *  
 *  @see polyglot.ast.Branch_c
 */
public class JifBranchExt extends JifStmtExt_c
{
    public JifBranchExt(ToJavaExt toJava) {
        super(toJava);
    }

    /** Label check the branch statement. See Figure 4.21. */
    public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
        Branch bs = (Branch) node();

        JifTypeSystem ts = lc.jifTypeSystem();
        JifContext A = lc.jifContext();
        A = (JifContext) bs.del().enterScope(A);

        Label pc = A.pc();
        Label gotoLabel = A.gotoLabel(bs.kind(), bs.label());

        if (gotoLabel == null) {
            throw new InternalCompilerError("Can't find target for " + bs.kind() + " " + bs.label());
        }

        lc.constrain(new NamedLabel("pc", 
                                    "the information that may be revealed by control reaching this program point",
                                    pc), 
                    LabelConstraint.LEQ, 
                    new NamedLabel("pc_target",
                                   "upper bound on information that should be revealed by control reaching the target program point",
                                   gotoLabel),
                   A.labelEnv(),
                   bs.position(),
                   new ConstraintMessage() {
            public String msg() {
                return "More information may be revealed by " +
                "branching to the target from this " +
                "program point than is allowed.";
            }
            public String detailMsg() { 
                return "Knowing that control flow reached this " +
                "program point may reveal information upto " + 
                namedRhs() + ". However, the target of " +
                "this break/continue should only be " +
                "revealed information less than or equal to " + 
                namedRhs() + ". Thus, more information " +
                "may be revealed by branching to the " +
                "target from this program point than is " +
                "allowed.";
            }
            public String technicalMsg() {
                return "Invalid break/continue: PC is more " +
                "restrictive than the label of the destination.";
            }                     
        }
        );

        PathMap X = ts.pathMap();
        // prevent the single path rule from being used.
        X = X.set(ts.gotoPath(bs.kind(), bs.label()), ts.topLabel());

        return updatePathMap(bs, X);
    }
}
