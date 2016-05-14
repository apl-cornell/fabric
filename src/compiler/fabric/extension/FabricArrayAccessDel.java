package fabric.extension;

import jif.extension.JifArrayAccessDel;

import polyglot.ast.Expr;

public class FabricArrayAccessDel extends JifArrayAccessDel {
  /**
   * Squirreled away the staging check expression to be produced when rewriting
   * to FabIL.
   */
  protected Expr stageCheck;

  public Expr stageCheck() {
    return stageCheck;
  }

  public void setStageCheck(Expr stageCheck) {
    this.stageCheck = stageCheck;
  }
}
