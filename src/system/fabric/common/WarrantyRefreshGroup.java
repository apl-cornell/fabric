package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fabric.common.VersionWarranty.Binding;

/**
 * Holds a set of related, unencrypted warranty updates.
 */
public class WarrantyRefreshGroup implements Iterable<Binding>,
    FastSerializable {
  private final List<Binding> warranties;

  /**
   * Constructs an empty group.
   */
  public WarrantyRefreshGroup() {
    this.warranties = new ArrayList<>();
  }

  /**
   * Constructs a group, initialized with the given warranties.
   */
  public WarrantyRefreshGroup(List<Binding> warranties) {
    this.warranties = warranties;
  }

  /**
   * Adds a new warranty to the group.
   */
  public void add(Binding warranty) {
    warranties.add(warranty);
  }

  @Override
  public Iterator<Binding> iterator() {
    return warranties.iterator();
  }

  /**
   * Serializes the group onto the given output stream.
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(warranties.size());
    for (Binding binding : warranties) {
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
    warranties = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      long expiry = in.readLong();
      long onum = in.readLong();
      int version = in.readInt();
      warranties.add(new VersionWarranty(expiry).new Binding(onum, version));
    }
  }
}
