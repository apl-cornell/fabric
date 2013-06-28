package fabric.dissemination.pastry.messages;

import java.io.IOException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import fabric.common.ObjectGroup;
import fabric.dissemination.ObjectGlob;

/**
 * Represents a push notification that an object group has been updated.
 */
public class ObjectUpdate extends AbstractUpdate<ObjectGroup> {

  public ObjectUpdate(NodeHandle sender, Id id, String store, long onum,
      ObjectGlob update) {
    super(RawMessageType.OBJECT_UPDATE, sender, id, store, onum, update);
  }

  public ObjectUpdate(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
      throws IOException {
    super(RawMessageType.OBJECT_UPDATE, buf, endpoint, sender);
  }

  @Override
  ObjectGlob deserializeUpdate(DataInputBuffer in) throws IOException {
    return new ObjectGlob(in);
  }

}
