package fabric.extension;

import jif.ast.JifExt;
import jif.types.label.Label;

import polyglot.ast.Ext_c;

/**
 * Interface supported by all delegates of fabric nodes which could be updated
 * with a staging check.
 */
//TODO: Start stage isn't really necessary now.
public class FabricStagingExt extends Ext_c implements FabricExt {

  /**
   * Squirreled away the stage labels to check in rewritten code.
   */
  protected Label startStage;
  protected Label endStage;

  /**
   * Get the starting stage label (if there is no staging needed, this is null)
   */
  public Label startStage() {
    return startStage;
  }

  /**
   * Get the ending stage label (if there is no staging needed, this is null)
   */
  public Label endStage() {
    return endStage;
  }

  /** Set the staging check */
  public void setStageCheck(Label startStage, Label endStage) {
    this.startStage = startStage;
    this.endStage = endStage;
  }
}
