package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongKeyMap.Entry;
import fabric.common.util.Pair;
import fabric.store.TransactionManager;
import fabric.store.db.ObjectDB.ReadMode;

/**
 * Holds a set of related, unencrypted, serialized objects.
 */
public class ObjectGroup {
  private final LongKeyMap<Pair<SerializedObject, VersionWarranty>> objects;

  /**
   * The version warranty that expires soonest for all the objects in the group.
   */
  private VersionWarranty expiry;

  public ObjectGroup(LongKeyMap<SerializedObject> objects) {
    this.objects =
        new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>(
            objects.size());
    this.expiry = null;

    for (Entry<SerializedObject> entry : objects.entrySet()) {
      this.objects.put(entry.getKey(),
          new Pair<SerializedObject, VersionWarranty>(entry.getValue(), null));
    }
  }

  /**
   * The objects as a map from their onums to the objects and their warranties.
   */
  public LongKeyMap<Pair<SerializedObject, VersionWarranty>> objects() {
    return objects;
  }

  /**
   * The version warranty that expires soonest for all objects in the group.
   */
  public VersionWarranty expiry() {
    return expiry;
  }

  /**
   * Serializes the group onto the given output stream.
   */
  public void write(DataOutput out) throws IOException {
    out.writeInt(objects.size());
    for (Pair<SerializedObject, VersionWarranty> obj : objects.values()) {
      obj.first.write(out);
      out.writeLong(obj.second.expiry());
    }
  }

  /**
   * Deserialization constructor.
   */
  public ObjectGroup(DataInput in) throws IOException {
    int groupSize = in.readInt();
    objects =
        new LongKeyHashMap<Pair<SerializedObject, VersionWarranty>>(groupSize);

    VersionWarranty expiry = null;
    for (int i = 0; i < groupSize; i++) {
      SerializedObject obj = new SerializedObject(in);
      VersionWarranty warranty = new VersionWarranty(in.readLong());
      objects.put(obj.getOnum(), new Pair<SerializedObject, VersionWarranty>(
          obj, warranty));
      if (expiry == null || expiry.expiresAfter(warranty)) expiry = warranty;
    }

    this.expiry = expiry;
  }

  public void refreshWarranties(TransactionManager tm) {
    if (expiry != null && !expiry.expired()) return;

    expiry = null;
    for (Entry<Pair<SerializedObject, VersionWarranty>> entry : objects
        .entrySet()) {
      Pair<SerializedObject, VersionWarranty> value = entry.getValue();
      VersionWarranty warranty =
          value.second =
              tm.getWarranty(entry.getKey(), ReadMode.REFRESH_WARRANTY);

      if (expiry == null || warranty != null && expiry.expiresAfter(warranty))
        expiry = warranty;
    }
  }
}
