package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;

import jif.extension.JifIfExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.If;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.SerialVersionUID;

/** The Fabric version of the Jif extension of the <code>If</code> node.
 * 
 *  @see polyglot.ast.Block_c
 *  @see jif.extension.JifIfExt
 */
public class FabricIfExt extends JifIfExt {
  private static final long serialVersionUID = SerialVersionUID.generate();

  public FabricIfExt(ToJavaExt toJava) {
      super(toJava);
  }

  /**
   * Extended to add any necessary staging at the end of each branch to
   * ensure that we're in a consistent stage after the if statement.
   */
  @Override
  public Node labelCheckStmt(LabelChecker lc) throws SemanticException {
    //TODO
    If iff = (If) super.labelCheckStmt(lc);
    if (iff.alternative() != null) {
      // Get the conflict label at the end of each alternative
      Label tCL = ((FabricPathMap) getPathMap(iff.consequent())).CL();
      Label fCL = ((FabricPathMap) getPathMap(iff.alternative())).CL();
      boolean tToF = lc.context().labelEnv().leq(tCL, fCL);
      boolean fToT = lc.context().labelEnv().leq(tCL, fCL);
      if (!tToF) {
        // Consequent isn't statically ending at a stage leq than the
        // alternative, so we need to add a final staging check.
        // TODO
      }
      if (!fToT) {
        // Alternative isn't statically ending at a stage leq than the
        // consequent, so we need to add a final staging check.
        // TODO
      }
    }
    return iff;
  }

  @Override
  protected void updateContextForConsequent(LabelChecker lc, JifContext A,
      PathMap Xcond) {
    super.updateContextForConsequent(lc, A, Xcond);
    FabricContext Af = (FabricContext) A;
    FabricPathMap Xfcond = (FabricPathMap) Xcond;
    Af.setConflictLabel(lc.jifTypeSystem().meet(Af.conflictLabel(), Xfcond.CL()));
  }
}
