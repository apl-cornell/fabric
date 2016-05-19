package fabric.extension;

import java.util.ArrayList;

import fabil.ast.FabILNodeFactory;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricContext;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

import jif.types.label.Label;

import polyglot.ast.Catch;
import polyglot.ast.Expr;
import polyglot.ast.Ext_c;
import polyglot.ast.Node;
import polyglot.ast.ProcedureDecl;
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
  protected Label curStage;
  protected Label nextStage;

  /**
   * Get the starting stage label (if there is no staging needed, this is null)
   */
  public Label curStage() {
    return curStage;
  }

  /**
   * Get the ending stage label (if there is no staging needed, this is null)
   */
  public Label nextStage() {
    return nextStage;
  }

  /**
   * Set the staging check.
   * Sets the fields to null if we're handed two equal labels.
   *
   * Variant which does not set the staging if the given context says that
   *    curStage ≤ nextStage (reverse is checked elsewhere).
   */
  public void setStageCheck(Label curStage, Label nextStage, FabricContext A) {
    if (curStage.hasVariableComponents() || nextStage.hasVariableComponents() ||
        !A.labelEnv().leq(curStage, nextStage)) {
      setStageCheck(curStage, nextStage);
    }
  }

  /**
   * Set the staging check.
   * Sets the fields to null if we're handed two equal labels.
   */
  public void setStageCheck(Label curStage, Label nextStage) {
    if (curStage != null)
      curStage = curStage.simplify();
    if (nextStage != null)
      nextStage = nextStage.simplify();
    if ((curStage == null && nextStage == null) || curStage.equals(nextStage)) {
      this.curStage = null;
      this.nextStage = null;
    } else {
      this.curStage = curStage;
      this.nextStage = nextStage;
    }
  }

  /**
   * Wrap an expression with staging up to the nextStage label, if it was
   * specified during label checking (otherwise, just return the expression).
   */
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

  /**
   * Follow (and wrap) a statement with staging up to the nextStage label, if it
   * was specified during label checking (otherwise, just return the statement).
   */
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
      return fil_nf.Try(pos,
          fil_nf.Block(pos, n),
          new ArrayList<Catch>(),
          fil_nf.Block(pos,
            fil_nf.Eval(pos,
              fil_nf.StageCall(pos, fil_nf.NullLit(pos), fabExpr))));
    }
    return n;
  }


  /**
   * Wrap the given procedure declaration body (if it exists) in a try-finally
   * block which ensures that there's an appropriate staging check before
   * leaving the procedure body (normally or otherwise).  If no nextStage label
   * was specified during label checking (eg. end is default "no accesses"
   * label), then return the declaration unmodified.
   */
  public ProcedureDecl stageCheck(FabricToFabilRewriter rw, Node parent, ProcedureDecl n) {
    if (n.body() != null && nextStage != null) {
      Position posOld = parent.position();
      FabricNodeFactory fab_nf = (FabricNodeFactory) rw.jif_nf();
      FabricTypeSystem fab_ts = (FabricTypeSystem) rw.jif_ts();

      // Get the label expression
      Expr fabExpr = fab_nf.LabelExpr(posOld, nextStage).type(fab_ts.Label());
      fabExpr = rw.visitEdge(parent, fabExpr);

      // Wrap with the stage call
      Position pos = n.position();
      FabILNodeFactory fil_nf = (FabILNodeFactory) rw.java_nf();
      return (ProcedureDecl) n.body(fil_nf.Block(pos,
          fil_nf.Try(pos,
            n.body(),
            new ArrayList<Catch>(),
            fil_nf.Block(pos,
              fil_nf.Eval(pos,
                fil_nf.StageCall(pos, fil_nf.NullLit(pos), fabExpr))))));
    }
    return n;
  }
}
