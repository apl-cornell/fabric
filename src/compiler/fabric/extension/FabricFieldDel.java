package fabric.extension;

import jif.extension.JifFieldDel;

import polyglot.ast.Expr;

public class FabricFieldDel extends JifFieldDel {
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
