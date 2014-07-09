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
