package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import fabric.common.VersionWarranty.Binding;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

/**
 * Holds a set of related, unencrypted warranty updates.
 */
public class WarrantyRefreshGroup implements Iterable<Binding>,
    FastSerializable {
  private final LongKeyMap<Binding> warranties;

  /**
   * Constructs an empty group.
   */
  public WarrantyRefreshGroup() {
    this.warranties = new LongKeyHashMap<>();
  }

  /**
   * Constructs a group, initialized with the given warranties.
   */
  public WarrantyRefreshGroup(LongKeyMap<Binding> warranties) {
    this.warranties = warranties;
  }

  /**
   * Adds a new warranty to the group.
   */
  public void add(Binding warranty) {
    warranties.put(warranty.onum, warranty);
  }

  @Override
  public Iterator<Binding> iterator() {
    return warranties.values().iterator();
  }

  /**
   * Serializes the group onto the given output stream.
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(warranties.size());
    for (Binding binding : warranties.values()) {
      out.writeLong(binding.expiry());
      out.writeLong(binding.onum);
      out.writeInt(binding.versionNumber);
    }
  }

  /**
   * Deserialization constructor.
   */
  public WarrantyRefreshGroup(DataInput in) throws IOException {
    int size = in.readInt();
    warranties = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      long expiry = in.readLong();
      long onum = in.readLong();
      int version = in.readInt();
      warranties.put(onum, new VersionWarranty(expiry).new Binding(onum,
          version));
    }
  }

  /**
   * @param onum
   */
  public Binding get(long onum) {
    return warranties.get(onum);
  }
}
