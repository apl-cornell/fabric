package fabric.store.db;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.security.PrivateKey;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.MutableInteger;
import fabric.common.util.Pair;
import fabric.store.PartialObjectGroup;
import fabric.worker.Store;

/**
 * Maps globIDs to pairs of Groups and pin counts. This class is thread-safe
 * and only keeps soft references to the group containers.
 */
public final class GroupTable {

  /**
   * Maps object numbers to globIDs. The group container with ID
   * globIDByOnum(onum) holds a copy of object onum. (globIDs really ought to be
   * called group-container IDs, but we're sticking with globID for historical
   * reasons and because it's shorter.)
   */
  private final LongKeyMap<Long> globIDByOnum;

  /**
   * Maps globIDs to entries (either GroupContainers or PartialObjectGroups) and
   * the number of times the entry is referenced in globIDByOnum.
   */
  private final LongKeyMap<Pair<SoftRef, MutableInteger>> table;
  private final ReferenceQueue<Entry> queue;

  public static abstract class Entry {
    protected abstract LongSet onums();
  }

  public GroupTable() {
    this.globIDByOnum = new LongKeyHashMap<Long>();
    this.table = new LongKeyHashMap<Pair<SoftRef, MutableInteger>>();
    this.queue = new ReferenceQueue<Entry>();

    new Collector().start();
  }

  public synchronized Long getGlobIDByOnum(long onum) {
    return globIDByOnum.get(onum);
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

  public synchronized void put(PartialObjectGroup partialGroup) {
    long groupID = partialGroup.groupID();

    // Establish groupID bindings for all non-surrogate onums in the group.
    for (LongKeyMap.Entry<SerializedObject> entry : partialGroup.objects
        .entrySet()) {
      SerializedObject obj = entry.getValue();
      if (obj.isSurrogate()) continue;

      // Establish groupID binding for the non-surrogate object.
      long onum = entry.getKey();
      Long oldGroupID = globIDByOnum.put(onum, groupID);
      if (oldGroupID != null) unpin(oldGroupID);
    }

    if (partialGroup.size() > 0) {
      // Insert into the table.
      put(groupID, partialGroup, partialGroup.size());
    }
  }

  private synchronized void put(long globID, Entry groupContainer, int pinCount) {
    SoftRef ref = new SoftRef(globID, groupContainer, queue);
    Pair<SoftRef, MutableInteger> entry =
        new Pair<SoftRef, MutableInteger>(ref, new MutableInteger(pinCount));
    table.put(globID, entry);
  }

  public synchronized void coalescePartialGroups(PartialObjectGroup from,
      PartialObjectGroup to) {
    long fromID = from.groupID();
    remove(fromID);

    to.mergeFrom(from);
    put(to);
  }

  public GroupContainer promotePartialGroup(Store store, PrivateKey signingKey,
      PartialObjectGroup partialGroup) {
    ObjectGroup group = new ObjectGroup(partialGroup.objects);
    GroupContainer result = new GroupContainer(store, signingKey, group);
    if (partialGroup.size() > 0) {
      put(partialGroup.groupID(), result, partialGroup.size());
    }

    return result;
  }

  public synchronized Entry remove(long globID) {
    Pair<SoftRef, MutableInteger> entry = table.remove(globID);
    if (entry == null) return null;

    // Clean out entries in globIDByOnum that refer to the entry we're removing.
    for (LongIterator it = entry.first.onums.iterator(); it.hasNext();) {
      globIDByOnum.remove(it.next());
    }

    return entry.first.get();
  }

  private static class SoftRef extends SoftReference<Entry> {
    final long globId;

    /**
     * The set of onums associated with this entry.
     */
    final LongSet onums;

    public SoftRef(long globID, Entry group, ReferenceQueue<Entry> queue) {
      super(group, queue);

      this.globId = globID;
      this.onums = group.onums();
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
