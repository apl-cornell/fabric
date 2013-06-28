package fabric.dissemination.pastry.messages;

import java.io.IOException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;

/**
 * Identifiers for each raw message sent over the network. Pastry uses shorts as
 * message type ids.
 */
public enum RawMessageType {
  FETCH {
    @Override
    public Fetch parse(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
        throws IOException {
      return new Fetch(buf, endpoint, sender);
    }
  },
  FETCH_REPLY {
    @Override
    public Fetch.Reply parse(InputBuffer buf, Endpoint endpoint,
        NodeHandle sender) throws IOException {
      return new Fetch.Reply(buf, endpoint);
    }
  },
  REPLICATE {
    @Override
    public Replicate parse(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
        throws IOException {
      return new Replicate(buf, sender);
    }
  },
  REPLICATE_REPLY {
    @Override
    public Replicate.Reply parse(InputBuffer buf, Endpoint endpoint,
        NodeHandle sender) throws IOException {
      return new Replicate.Reply(buf);
    }
  },
  OBJECT_UPDATE {
    @Override
    public ObjectUpdate parse(InputBuffer buf, Endpoint endpoint,
        NodeHandle sender) throws IOException {
      return new ObjectUpdate(buf, endpoint, sender);
    }
  },
  UPDATE_REPLY {
    @Override
    public AbstractUpdate.Reply parse(InputBuffer buf, Endpoint endpoint,
        NodeHandle sender) throws IOException {
      return new AbstractUpdate.Reply(buf, endpoint, sender);
    }
  };

  /**
   * Deserializes a message of the appropriate type from the given InputBuffer.
   */
  public abstract AbstractRawMessage parse(InputBuffer buf, Endpoint endpoint,
      NodeHandle sender) throws IOException;
}
