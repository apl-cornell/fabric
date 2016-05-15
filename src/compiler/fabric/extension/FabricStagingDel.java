package fabric.extension;

import polyglot.ast.Expr;

/**
 * Interface supported by all delegates of fabric nodes which could be updated
 * with a staging check.
 */
public interface FabricStagingDel {

  /** Get the staging check, if there is one (otherwise, null) */
  public Expr stageCheck();

  /** Set the staging check */
  public void setStageCheck(Expr stageCheck);

}
