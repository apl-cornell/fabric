package fabric.core.store;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.MutableInteger;
import fabric.common.util.Pair;

/**
 * Maps globIDs to pairs of GroupContainers and pin counts. This class is
 * thread-safe and only keeps soft references to the group containers.
 */
final class GroupContainerTable {
  private final LongKeyMap<Pair<SoftRef, MutableInteger>> table;
  private final ReferenceQueue<GroupContainer> queue;

  public GroupContainerTable() {
    this.table = new LongKeyHashMap<Pair<SoftRef, MutableInteger>>();
    this.queue = new ReferenceQueue<GroupContainer>();

    new Collector().start();
  }

  public synchronized GroupContainer getContainer(long globID) {
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

  public synchronized void put(long globID, GroupContainer groupContainer,
      int pinCount) {
    SoftRef ref = new SoftRef(globID, groupContainer, queue);
    Pair<SoftRef, MutableInteger> entry =
        new Pair<SoftRef, MutableInteger>(ref, new MutableInteger(pinCount));
    table.put(globID, entry);
  }

  public synchronized void remove(long globID) {
    table.remove(globID);
  }

  private static class SoftRef extends SoftReference<GroupContainer> {
    final long globId;

    /**
     * @param store
     *          The object store that is storing the objects in the given group.
     */
    public SoftRef(long globID, GroupContainer group,
        ReferenceQueue<GroupContainer> queue) {
      super(group, queue);

      this.globId = globID;
    }
  }

  private final class Collector extends Thread {
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
