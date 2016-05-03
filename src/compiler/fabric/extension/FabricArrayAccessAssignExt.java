package fabric.extension;

import jif.extension.JifArrayAccessAssignExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

/**
 * Special handling for access and conflict labels.
 */
public class FabricArrayAccessAssignExt extends JifArrayAccessAssignExt {
    private static final long serialVersionUID = SerialVersionUID.generate();

    public FabricArrayAccessAssignExt(ToJavaExt toJava) {
        super(toJava);
    }

    /**
     * Additional handling for conflict label checks.
     */
    @Override
    public Node labelCheckLHS(LabelChecker lc) throws SemanticException {
      ArrayAccessAssign acc = (ArrayAccessAssign) super.labelCheckLHS(lc);
      return acc.left(FabricArrayAccessExt.conflictLabelCheck(acc.left(), lc, true));
    }
}