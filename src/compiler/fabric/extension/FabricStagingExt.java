package fabric.extension;

import fabil.ast.FabILNodeFactory;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

import jif.types.label.Label;

import polyglot.ast.Expr;
import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.util.Position;

/**
 * Interface supported by all delegates of fabric nodes which could be updated
 * with a staging check.
 */
public class FabricStagingExt extends Ext_c implements FabricExt {

  /**
   * Squirreled away the stage labels to check in rewritten code.
   */
  protected Label nextStage;

  /**
   * Get the ending stage label (if there is no staging needed, this is null)
   */
  public Label nextStage() {
    return nextStage;
  }

  /** Set the staging check */
  public void setStageCheck(Label nextStage) {
    this.nextStage = nextStage;
  }

  public Expr stageCheck(FabricToFabilRewriter rw, Node parent, Expr n) {
    if (nextStage != null) {
      Position posOld = parent.position();
      FabricNodeFactory fab_nf = (FabricNodeFactory) rw.jif_nf();
      FabricTypeSystem fab_ts = (FabricTypeSystem) rw.jif_ts();
      // Get the label expression
      Expr fabExpr = fab_nf.LabelExpr(posOld, nextStage).type(fab_ts.Label());
      fabExpr = rw.visitEdge(parent, fabExpr);

      // Wrap in the stage call
      Position pos = n.position();
      FabILNodeFactory fil_nf = (FabILNodeFactory) rw.java_nf();
      return fil_nf.StageCall(pos, n, fabExpr);
    }
    return n;
  }

  public Stmt stageCheck(FabricToFabilRewriter rw, Node parent, Stmt n) {
    if (nextStage != null) {
      Position posOld = parent.position();
      FabricNodeFactory fab_nf = (FabricNodeFactory) rw.jif_nf();
      FabricTypeSystem fab_ts = (FabricTypeSystem) rw.jif_ts();
      // Get the label expression
      Expr fabExpr = fab_nf.LabelExpr(posOld, nextStage).type(fab_ts.Label());
      fabExpr = rw.visitEdge(parent, fabExpr);

      // Wrap in the stage call
      Position pos = n.position();
      FabILNodeFactory fil_nf = (FabILNodeFactory) rw.java_nf();
      return fil_nf.Block(pos,
          n,
          fil_nf.Eval(pos,
            fil_nf.StageCall(pos, fil_nf.NullLit(pos), fabExpr)));
    }
    return n;
  }
}
