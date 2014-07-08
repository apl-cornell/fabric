/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/**
 * Holds a set of related, unencrypted, serialized objects.
 */
public class ObjectGroup {
  private final LongKeyMap<SerializedObject> objects;

  public ObjectGroup(LongKeyMap<SerializedObject> objects) {
    this.objects = objects;
  }

  /**
   * The objects as a map from their onums to the objects.
   */
  public LongKeyMap<SerializedObject> objects() {
    return objects;
  }

  /**
   * Serializes the group onto the given output stream.
   */
  public void write(DataOutput out) throws IOException {
    out.writeInt(objects.size());
    for (SerializedObject obj : objects.values()) {
      obj.write(out);
    }
  }

  /**
   * Deserialization constructor.
   */
  public ObjectGroup(DataInput in) throws IOException {
    int groupSize = in.readInt();
    objects = new LongKeyHashMap<SerializedObject>(groupSize);

    for (int i = 0; i < groupSize; i++) {
      SerializedObject obj = new SerializedObject(in);
      objects.put(obj.getOnum(), obj);
    }
  }
}
