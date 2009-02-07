package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/**
 * Holds an unencrypted serialized object and a set of related, unencrypted,
 * serialized objects.
 */
public class ObjectGroup {
  private final SerializedObject obj;
  private final LongKeyMap<SerializedObject> related;

  public ObjectGroup(SerializedObject obj, LongKeyMap<SerializedObject> related) {
    this.obj = obj;
    this.related = related;
  }
  
  /**
   * The head object for this group.
   */
  public SerializedObject obj() {
    return obj;
  }
  
  /**
   * The related objects as a map from their onums to the objects.
   */
  public LongKeyMap<SerializedObject> related() {
    return related;
  }
  
  /**
   * Serializes the group onto the given output stream.
   */
  public void write(DataOutput out) throws IOException {
    obj.write(out);
    out.writeInt(related.size());
    
    for (LongKeyMap.Entry<SerializedObject> entry : related.entrySet()) {
      out.writeLong(entry.getKey());
      entry.getValue().write(out);
    }
  }
  
  /**
   * Deserialization constructor.
   */
  public ObjectGroup(DataInput in) throws IOException {
    obj = new SerializedObject(in);
    
    int groupSize = in.readInt();
    related = new LongKeyHashMap<SerializedObject>(groupSize);
    
    for (int i = 0; i < groupSize; i++) {
      related.put(in.readLong(), new SerializedObject(in));
    }
  }
}
