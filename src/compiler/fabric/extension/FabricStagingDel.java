package fabric.extension;

import jif.types.label.Label;

/**
 * Interface supported by all delegates of fabric nodes which could be updated
 * with a staging check.
 */
//TODO: Start stage isn't really necessary now.
public interface FabricStagingDel {

  /**
   * Get the starting stage label (if there is no staging needed, this is null)
   */
  public Label startStage();

  /**
   * Get the ending stage label (if there is no staging needed, this is null)
   */
  public Label endStage();

  /** Set the staging check */
  public void setStageCheck(Label startStage, Label endStage);

}
