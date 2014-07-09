/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.dissemination.pastry.messages;

import java.io.IOException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import fabric.common.exceptions.InternalError;

/**
 * Identifiers for each raw message sent over the network. Pastry uses shorts as
 * message type ids.
 */
public enum RawMessageType {
  /**
   * This placeholder type occupies message type 0, which is reserved by Pastry
   * for "Java Serialized Messages".
   */
  _RESERVED_ {
    @Override
    public AbstractRawMessage parse(InputBuffer buf, Endpoint endpoint,
        NodeHandle sender) throws IOException {
      throw new InternalError("Shouldn't reach here.");
    }
  },
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
