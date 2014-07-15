package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/**
 * Holds a set of related, unencrypted, serialized objects.
 */
public class ObjectGroup implements FastSerializable {
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
  @Override
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
    objects = new LongKeyHashMap<>(groupSize);

    for (int i = 0; i < groupSize; i++) {
      SerializedObject obj = new SerializedObject(in);
      objects.put(obj.getOnum(), obj);
    }
  }
}
