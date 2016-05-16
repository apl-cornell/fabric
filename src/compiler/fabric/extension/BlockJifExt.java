package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifBlockExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

/** The Fabric version of the Jif extension of the <code>Block</code> node.
 * 
 *  @see polyglot.ast.Block_c
 *  @see jif.extension.JifBlockExt
 */
public class BlockJifExt extends JifBlockExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public BlockJifExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  protected void updateContextForNextStmt(LabelChecker lc, JifContext A,
      PathMap Xprev) {
    super.updateContextForNextStmt(lc, A, Xprev);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfprev = (FabricPathMap) Xprev;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfprev.CL()));
  }

  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    // Store the starting conflictLabel
    Label confL = ((FabricContext) lc.context()).conflictLabel();
    Node checked = super.labelCheckStmt(lc);
    FabricPathMap X = (FabricPathMap) getPathMap(checked);
    // Add the starting conflictLabel back into the path map.
    return updatePathMap(checked, X.CL(lc.jifTypeSystem().meet(X.CL(), confL)));
  }

}
