package fabric.client.transaction;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import fabric.common.FastSerializable;

public class TransactionID implements FastSerializable {
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

  public TransactionID() {
    this((TransactionID) null);
  }

  TransactionID(TransactionID parent) {
    this(parent, newTid());
  }

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
  }

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
}
