package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifSwitchExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

public class FabricSwitchExt extends JifSwitchExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricSwitchExt(ToJavaExt toJava) {
    super(toJava);
  }
  
  /**
   * Modified to reset the Conflict PC after each branch.
   */
  @Override
  protected void updateContextForNextCase(LabelChecker lc, JifContext A,
      PathMap Xprev) {
    super.updateContextForNextCase(lc, A, Xprev);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfprev = (FabricPathMap) Xprev;
    Af.setConflictLabel(Xfprev.CL());
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    // TODO Auto-generated method stub
    // TODO Handle branching for conflict labels.
    return super.labelCheckStmt(lc);
  }
}
