package fabric.dissemination.pastry.messages;

import fabric.dissemination.pastry.Disseminator;

/**
 * Common interface for Messages in the Pastry dissemination layer.
 */
public interface Message extends rice.p2p.commonapi.Message {
  /**
   * Handles this message by calling into the given disseminator.
   */
  void dispatch(Disseminator disseminator);
}
