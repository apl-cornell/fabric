package fabric.extension;

import jif.extension.JifConstructorCallDel;

import polyglot.ast.Expr;

public class FabricConstructorCallDel extends JifConstructorCallDel implements FabricStagingDel {

  /**
   * Squirreled away the staging check expression to be produced when rewriting
   * to FabIL.
   */
  protected Expr stageCheck;

  @Override
  public Expr stageCheck() {
    return stageCheck;
  }

  @Override
  public void setStageCheck(Expr stageCheck) {
    this.stageCheck = stageCheck;
  }

}
