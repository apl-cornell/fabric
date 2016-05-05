package fabric.extension;

import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;

import jif.extension.JifArrayAccessAssignExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Expr;
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
      // Jif checks
      ArrayAccessAssign acc = (ArrayAccessAssign) super.labelCheckLHS(lc);

      // Access label checks
      Expr array = acc.left().array();
      FabricReferenceType type = (FabricReferenceType)
        lc.typeSystem().unlabel(array.type());
      DereferenceHelper.checkAccess(array,  type, lc, node().position());

      // Confict checks
      acc = (ArrayAccessAssign) acc.left(FabricArrayAccessExt.conflictLabelCheck(acc.left(), lc, true));

      // Update the pathmap
      FabricPathMap Xfe = (FabricPathMap) getPathMap(acc.left());
      FabricPathMap X = (FabricPathMap) getPathMap(acc);
      return updatePathMap(acc, X.CL(Xfe.CL()));
    }
}
