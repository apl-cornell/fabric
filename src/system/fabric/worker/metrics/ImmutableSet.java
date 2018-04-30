package fabric.worker.metrics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Iterator;

import fabric.common.FastSerializable;
import fabric.common.util.Oid;
import fabric.common.util.OidHashSet;

/**
 * Utility class to easily express an immutable vector of items.
 */
public class ImmutableSet implements FastSerializable, Serializable,
    Iterable<fabric.lang.Object._Proxy> {

  private OidHashSet items;

  /** Provided since you can't mark constructors as native for the fabil
   * signatures
   */
  public static ImmutableSet createSet(fabric.lang.Object[] items) {
    return new ImmutableSet(items);
  }

  public ImmutableSet(fabric.lang.Object[] items) {
    this.items = new OidHashSet();
    for (fabric.lang.Object item : items) {
      this.items.add(item);
    }
  }

  public ImmutableSet(DataInput in) throws IOException {
    this.items = new OidHashSet(in);
  }

  private ImmutableSet(OidHashSet items) {
    this.items = new OidHashSet(items);
  }

  /** @return the length. */
  public int size() {
    return items.size();
  }

  /** @return a new set with the given item added. */
  public ImmutableSet add(fabric.lang.Object obs) {
    ImmutableSet updated = new ImmutableSet(items);
    updated.items.add(obs);
    return updated;
  }

  /** @return a new set with the given item removed. */
  public ImmutableSet remove(fabric.lang.Object obs) {
    ImmutableSet updated = new ImmutableSet(items);
    updated.items.remove(obs);
    return updated;
  }

  /** @return true iff the given observer is in the set */
  public boolean contains(fabric.lang.Object obs) {
    return items.contains(obs);
  }

  /** @return true iff the set is empty */
  public boolean isEmpty() {
    return items.isEmpty();
  }

  @Override
  public Iterator<fabric.lang.Object._Proxy> iterator() {
    return new Iterator<fabric.lang.Object._Proxy>() {
      Iterator<Oid> oidIter = items.iterator();

      @Override
      public boolean hasNext() {
        return oidIter.hasNext();
      }

      @Override
      public fabric.lang.Object._Proxy next() {
        Oid oid = oidIter.next();
        return new fabric.lang.Object._Proxy(oid.store, oid.onum);
      }
    };
  }

  private static final ImmutableSet EMPTY =
      new ImmutableSet(new fabric.lang.Object[0]);

  /** @return a value to use for an empty vector */
  public static ImmutableSet emptySet() {
    return EMPTY;
  }

  /* Serializable definitions, need to special case fabric references. */

  private void writeObject(ObjectOutputStream out) throws IOException {
    write(out);
  }

  private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException {
    this.items = new OidHashSet(in);
  }

  private void readObjectNoData() throws ObjectStreamException {
    items = new OidHashSet();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof ImmutableSet)
        && this.items.equals(((ImmutableSet) obj).items);
  }

  @Override
  public int hashCode() {
    return this.items.hashCode();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    items.write(out);
  }
}
