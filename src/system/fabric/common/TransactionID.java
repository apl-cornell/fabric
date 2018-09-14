package fabric.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

public class TransactionID implements FastSerializable, Serializable {
  /**
   * Source for random tids.
   */
  private static final Random rand = new SecureRandom();

  public final TransactionID parent;

  /**
   * The tid for this nested transaction.
   */
  public final long tid;

  /**
   * The tid for the top-level transaction.
   */
  public final long topTid;

  /**
   * The nesting depth of this transaction. Top-level transactions have depth 0.
   */
  public final int depth;

  /* Cached computed hashcode. */
  private final int hashcode;

  public TransactionID() {
    this((TransactionID) null);
  }

  /**
   * Creates a new top-level transaction id with the given id.
   */
  public TransactionID(long tid) {
    this(null, tid);
  }

  /**
   * Creates a new transaction ID that is a child of the given tid.
   *
   * @param parent
   */
  public TransactionID(TransactionID parent) {
    this(parent, newTid());
  }

  /**
   * Deserialization constructor.
   */
  public TransactionID(DataInput in) throws IOException {
    this(in.readBoolean() ? new TransactionID(in) : null, in.readLong());
  }

  private TransactionID(TransactionID parent, long tid) {
    this.parent = parent;

    this.tid = tid;

    if (parent == null) {
      depth = 0;
      topTid = tid;
    } else {
      depth = parent.depth + 1;
      topTid = parent.topTid;
    }

    if (parent != null)
      this.hashcode = parent.hashcode * 32 + Long.hashCode(tid);
    else this.hashcode = Long.hashCode(tid);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeBoolean(parent != null);
    if (parent != null) parent.write(out);
    out.writeLong(tid);
  }

  private static long newTid() {
    synchronized (rand) {
      return rand.nextLong();
    }
  }

  /**
   * @return true iff the transaction represented by this tid is a descendant of
   *         (or is the same as) the one represented by the given tid.
   */
  public boolean isDescendantOf(TransactionID tid) {
    // Must have same top-level TIDs.
    if (topTid != tid.topTid) return false;

    // If our depth is less than the other tid's depth, we cannot be a
    // descendant.
    if (depth < tid.depth) return false;

    // Traverse up our ancestry until we are at the same depth as the given tid.
    TransactionID ancestor = this;
    while (ancestor.depth != tid.depth)
      ancestor = ancestor.parent;

    return ancestor.equals(tid);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TransactionID)) return false;
    return equals((TransactionID) o);
  }

  @Override
  public int hashCode() {
    return hashcode;
  }

  public boolean equals(TransactionID tid) {
    if (topTid != tid.topTid || depth != tid.depth) return false;

    TransactionID tid1 = this;
    TransactionID tid2 = tid;
    while (tid1 != tid2) {
      if (tid1.tid != tid2.tid) return false;
      tid1 = tid1.parent;
      tid2 = tid2.parent;
    }
    return true;
  }

  /**
   * @return the longest tid that is an ancestor of both this tid and the given
   *         tid.
   */
  public TransactionID getLowestCommonAncestor(TransactionID tid) {
    if (tid == null) return null;

    TransactionID ancestor1;
    TransactionID ancestor2;
    if (depth < tid.depth) {
      ancestor1 = this;
      ancestor2 = tid;
    } else {
      ancestor1 = tid;
      ancestor2 = this;
    }

    // Get the two ancestors to the same height.
    while (ancestor2.depth > ancestor1.depth)
      ancestor2 = ancestor2.parent;

    // Walk up until the ancestors match.
    while (ancestor1 != ancestor2 && ancestor1.tid != ancestor2.tid
        && !ancestor1.equals(ancestor2)) {
      ancestor1 = ancestor1.parent;
      ancestor2 = ancestor2.parent;
    }

    return ancestor1;
  }

  @Override
  public String toString() {
    String prefix = parent == null ? "" : (parent + ":");
    return prefix + Long.toHexString(tid);
  }
}
