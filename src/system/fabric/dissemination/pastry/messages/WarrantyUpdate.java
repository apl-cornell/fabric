package fabric.dissemination.pastry.messages;

import java.io.IOException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import fabric.common.WarrantyGroup;
import fabric.dissemination.WarrantyGlob;

/**
 * Represents a push notification that an object group has been updated.
 */
public class WarrantyUpdate extends AbstractUpdate<WarrantyGroup> {

  public WarrantyUpdate(NodeHandle sender, Id id, String store, long onum,
      WarrantyGlob update) {
    super(RawMessageType.WARRANTY_UPDATE, sender, id, store, onum, update);
  }

  public WarrantyUpdate(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
      throws IOException {
    super(RawMessageType.WARRANTY_UPDATE, buf, endpoint, sender);
  }

  @Override
  WarrantyGlob deserializeUpdate(DataInputBuffer in) throws IOException {
    return new WarrantyGlob(in);
  }

}
