package fabric.store.db;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.MutableInteger;
import fabric.common.util.Pair;

/**
 * Maps globIDs to pairs of Groups and pin counts. This class is thread-safe
 * and only keeps soft references to the group containers.
 */
public final class GroupTable {
  private final LongKeyMap<Pair<SoftRef, MutableInteger>> table;
  private final ReferenceQueue<Entry> queue;

  public static abstract class Entry {
  }

  public GroupTable() {
    this.table = new LongKeyHashMap<Pair<SoftRef, MutableInteger>>();
    this.queue = new ReferenceQueue<Entry>();

    new Collector().start();
  }

  public synchronized Entry getContainer(long globID) {
    Pair<SoftRef, MutableInteger> pair = table.get(globID);
    if (pair == null) return null;
    return pair.first.get();
  }

  public synchronized void unpin(long globID) {
    Pair<SoftRef, MutableInteger> pair = table.get(globID);
    if (pair == null) return;

    if (pair.second.value == 1) remove(globID);
    pair.second.value--;
  }

  public synchronized void put(long globID, Entry groupContainer,
      int pinCount) {
    SoftRef ref = new SoftRef(globID, groupContainer, queue);
    Pair<SoftRef, MutableInteger> entry =
        new Pair<SoftRef, MutableInteger>(ref, new MutableInteger(pinCount));
    table.put(globID, entry);
  }

  public synchronized Entry remove(long globID) {
    Pair<SoftRef, MutableInteger> entry = table.remove(globID);
    if (entry == null) return null;

    return entry.first.get();
  }

  private static class SoftRef extends SoftReference<Entry> {
    final long globId;

    public SoftRef(long globID, Entry group, ReferenceQueue<Entry> queue) {
      super(group, queue);

      this.globId = globID;
    }
  }

  private final class Collector extends Thread {
    public Collector() {
      super("Group container soft-ref collector");
      setDaemon(true);
    }

    @Override
    public void run() {
      while (true) {
        try {
          SoftRef ref = (SoftRef) queue.remove();
          remove(ref.globId);
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
