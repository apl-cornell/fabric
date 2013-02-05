package fabric.store.db;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Constructs and caches object groups. This class is thread-safe.
 */
public final class ObjectGrouper {
  private static final int MIN_GROUP_SIZE = 75;

  /**
   * The database containing the objects to be grouped.
   */
  private final ObjectDB database;

  /**
   * The key to use when signing objects.
   */
  private final PrivateKey signingKey;

  /**
   * Maps onums to SoftRefs.
   */
  private final LongKeyMap<SoftRef> table;

  /**
   * The set of group locks. Used to prevent threads from concurrently
   * attempting to group the same object.
   */
  private final LongKeyMap<GroupLock> groupLocks;

  private final ReferenceQueue<AbstractGroup> queue;

  /**
   * Either a GroupContainer, a PartialObjectGroup, or a Placeholder.
   */
  public static abstract class AbstractGroup {
    protected abstract LongSet onums();
  }

  public ObjectGrouper(ObjectDB database, PrivateKey signingKey) {
    this.database = database;
    this.signingKey = signingKey;
    this.table = new LongKeyHashMap<SoftRef>();
    this.groupLocks = new LongKeyHashMap<GroupLock>();
    this.queue = new ReferenceQueue<AbstractGroup>();

    new Collector().start();
  }

  GroupContainer getGroup(long onum) {
    // Check the cache.
    GroupLock lock;
    synchronized (groupLocks) {
      synchronized (table) {
        SoftRef ref = table.get(onum);
        if (ref != null) {
          AbstractGroup group = ref.get();
          if (group instanceof GroupContainer) return (GroupContainer) group;
        }
      }

      // Need to construct group.
      lock = getGroupLock(onum);

      // Set the lock's needFullGroup flag.
      synchronized (lock) {
        if (lock.status == GroupLock.Status.CLAIMED_FOR_PARTIAL_GROUP) {
          lock.needFullGroup = true;
        }
      }
    }

    synchronized (lock) {
      while (true) {
        switch (lock.status) {
        case UNCLAIMED:
          // Claim the lock.
          lock.status = GroupLock.Status.CLAIMED_FOR_FULL_GROUP;
          break;

        case CLAIMED_FOR_PARTIAL_GROUP:
        case CLAIMED_FOR_FULL_GROUP:
          // Wait for a signal.
          try {
            lock.wait();
          } catch (InterruptedException e) {
          }
          continue;

        case READY:
          return lock.group;
        }

        break;
      }
    }

    // We are responsible for creating the group. Get a partial group for the
    // onum.
    PartialObjectGroup partialGroup = getPartialGroup(onum, lock, null);
    if (partialGroup == null) return null;

    // Add any small groups on the partial group's frontier.
    for (LongKeyMap.Entry<SerializedObject> entry : partialGroup.frontier
        .entrySet()) {
      long frontierOnum = entry.getKey();

      // Make sure the frontier object isn't already in a (non-partial) group
      // and attempt to obtain a lock on the frontier object.
      GroupLock frontierLock = attemptLockForPartialGroup(frontierOnum, true);
      if (frontierLock == null) continue;

      // Lock obtained. Get a partial group for the frontier object.
      SerializedObject frontierObj = entry.getValue();
      PartialObjectGroup frontierGroup =
          getPartialGroup(frontierOnum, frontierLock, frontierObj);
      if (frontierGroup.size() >= MIN_GROUP_SIZE) {
        // Frontier group is large enough to hold its own. Add it to the cache.
        cacheGroup(frontierGroup.objects, frontierGroup);

        // Unlock the locks it holds.
        synchronized (groupLocks) {
          for (GroupLock groupLock : frontierGroup.locks) {
            synchronized (groupLock) {
              groupLock.status = GroupLock.Status.UNCLAIMED;
              groupLock.notifyAll();

              if (!groupLock.needFullGroup) groupLocks.remove(groupLock.onum);
            }
          }
        }

        continue;
      }

      // Group on the frontier isn't big enough. Coalesce it with the group
      // we're creating.
      partialGroup.mergeFrom(frontierGroup);
    }

    Store store = Worker.getWorker().getStore(database.getName());
    GroupContainer result =
        new GroupContainer(store, signingKey, new ObjectGroup(
            partialGroup.objects));

    // Add the result to the cache: bind all non-surrogate onums to the result.
    cacheGroup(partialGroup.objects, result);

    // Unlock and notify any waiting threads.
    for (GroupLock groupLock : partialGroup.locks) {
      synchronized (groupLock) {
        groupLock.group = result;
        groupLock.status = GroupLock.Status.READY;
        groupLock.notifyAll();
      }

      // Remove the lock from groupLocks.
      synchronized (groupLocks) {
        groupLocks.remove(groupLock.onum);
      }
    }

    return result;
  }

  AbstractGroup removeGroup(long onum) {
    // If there is a thread working on a group for this object, let it finish.
    while (true) {
      GroupLock lock;
      synchronized (groupLocks) {
        lock = groupLocks.get(onum);
      }

      if (lock == null) break;

      synchronized (lock) {
        switch (lock.status) {
        case UNCLAIMED:
        case READY:
          break;

        case CLAIMED_FOR_PARTIAL_GROUP:
        case CLAIMED_FOR_FULL_GROUP:
          // Wait for other thread to finish.
          try {
            lock.wait();
          } catch (InterruptedException e) {
          }
          continue;
        }
      }

      break;
    }

    synchronized (table) {
      SoftRef ref = table.get(onum);
      if (ref == null) return null;

      AbstractGroup result = ref.get();
      if (result == null) return null;

      // Remove the group from the table.
      for (LongIterator it = result.onums().iterator(); it.hasNext();) {
        long memberOnum = it.next();
        SoftRef memberRef = table.get(memberOnum);
        if (memberRef == null || memberRef.get() != result) continue;
        table.remove(memberOnum);
      }

      return result;
    }
  }

  /**
   * Adds the given abstract group to the cache.
   */
  private void cacheGroup(LongKeyMap<SerializedObject> groupObjects,
      AbstractGroup group) {
    synchronized (table) {
      for (LongKeyMap.Entry<SerializedObject> entry : groupObjects.entrySet()) {
        SerializedObject obj = entry.getValue();
        if (obj.isSurrogate()) continue;

        table.put(entry.getKey(), new SoftRef(group));
      }
    }
  }

  /**
   * Returns the GroupLock for the given onum.
   */
  private GroupLock getGroupLock(long onum) {
    synchronized (groupLocks) {
      GroupLock lock = groupLocks.get(onum);
      if (lock == null) {
        lock = new GroupLock(onum);
        groupLocks.put(onum, lock);
      }
      return lock;
    }
  }

  /**
   * <p>
   * Returns a partial group of objects. A partial group is a group containing
   * at most MIN_GROUP_SIZE objects, but may need to be grown because the object
   * sub-graphs on the group's frontier may not be large enough to fill an
   * entire group.
   * </p>
   * <p>
   * Assumes the current thread has the lock for the given onum. The caller is
   * responsible for releasing the lock.
   * </p>
   * 
   * @param lock
   *          The lock held for the given onum.
   * @param obj
   *          The object corresponding to the given onum. If null, the object
   *          will be read from the database.
   */
  private PartialObjectGroup getPartialGroup(long onum, GroupLock lock,
      SerializedObject obj) {
    // Check the cache.
    synchronized (table) {
      SoftRef ref = table.get(onum);
      if (ref != null) {
        AbstractGroup group = ref.get();
        if (group instanceof PartialObjectGroup) {
          PartialObjectGroup result = (PartialObjectGroup) group;
          result.locks.add(lock);
          return result;
        }
      }
    }

    if (obj == null) obj = database.read(onum);
    if (obj == null) return null;

    long headLabelOnum = obj.getUpdateLabelOnum();

    LongKeyMap<SerializedObject> group =
        new LongKeyHashMap<SerializedObject>(MIN_GROUP_SIZE);
    LongKeyMap<SerializedObject> frontier =
        new LongKeyHashMap<SerializedObject>();
    List<GroupLock> groupLocks = new ArrayList<GroupLock>();

    // Number of non-surrogate objects in the group.
    int groupSize = 0;

    // Do a breadth-first traversal and add objects to the group.
    Queue<SerializedObject> toVisit = new LinkedList<SerializedObject>();
    toVisit.add(obj);

    LongSet seen = new LongHashSet();
    seen.add(onum);

    while (!toVisit.isEmpty()) {
      SerializedObject curObject = toVisit.remove();
      long curOnum = curObject.getOnum();

      // Always add surrogates.
      if (curObject.isSurrogate()) {
        group.put(curOnum, curObject);
        continue;
      }

      if (groupSize >= MIN_GROUP_SIZE) {
        // Partial group is full. Add the object to the partial group's
        // frontier.
        frontier.put(curOnum, curObject);
        continue;
      }

      // Attempt to claim a lock on curOnum.
      GroupLock curLock =
          curOnum == onum ? lock : attemptLockForPartialGroup(curOnum, false);
      if (curLock == null) continue;

      // Add object to partial group.
      group.put(curOnum, curObject);
      groupSize++;
      groupLocks.add(curLock);

      // Visit links outgoing from the object.
      for (Iterator<Long> it = curObject.getIntraStoreRefIterator(); it
          .hasNext();) {
        long relatedOnum = it.next();
        if (seen.contains(relatedOnum)) continue;
        seen.add(relatedOnum);

        SerializedObject related = database.read(relatedOnum);
        if (related == null) continue;

        // Ensure that the related object's label is the same as the head
        // object's label. We could be smarter here, but to avoid calling into
        // the worker, let's hope pointer equality is sufficient.
        long relatedLabelOnum = related.getUpdateLabelOnum();
        if (headLabelOnum != relatedLabelOnum) continue;

        toVisit.add(related);
      }
    }

    return new PartialObjectGroup(groupSize, group, frontier, groupLocks);
  }

  /**
   * Attempts to lock an onum for creating a partial group.
   *
   * @param ignorePartialGroups
   *          If false, an attempt will only be made if the onum is not already
   *          in a group (partial or otherwise). If true, an attempt will be
   *          made even if the onum is already in a partial group.
   *          
   * @return the GroupLock if successful, otherwise null.
   */
  private GroupLock attemptLockForPartialGroup(long onum,
      boolean ignorePartialGroups) {
    GroupLock lock;

    synchronized (groupLocks) {
      // First look for an existing group lock.
      lock = groupLocks.get(onum);
      if (lock == null) {
        // No existing group lock. Check to see if there's a cached group.
        synchronized (table) {
          SoftRef ref = table.get(onum);
          if (ref != null) {
            AbstractGroup group = ref.get();
            if (!ignorePartialGroups && group != null
                || group instanceof GroupContainer) return null;
          }
        }

        // No cached group. Create the lock.
        lock = getGroupLock(onum);
      }
    }

    synchronized (lock) {
      if (lock.status == GroupLock.Status.UNCLAIMED) {
        lock.status = GroupLock.Status.CLAIMED_FOR_PARTIAL_GROUP;
        return lock;
      }

      return null;
    }
  }

  static class GroupLock {
    static enum Status {
      UNCLAIMED,
      /**
       * Indicates that the lock has been claimed by a thread that is creating a
       * full group.
       */
      CLAIMED_FOR_FULL_GROUP,
      /**
       * Indicates that the lock has been claimed by a thread that is creating a
       * partial group
       */
      CLAIMED_FOR_PARTIAL_GROUP,
      /**
       * Indicates that a full group has been created and is referenced by the
       * lock's <code>group</code> field.
       */
      READY
    }

    Status status;
    GroupContainer group;
    final long onum;

    /**
     * If the lock has been claimed by a thread that is creating a partial
     * group, this flag is a signal that another thread is waiting to create a
     * full group.
     */
    boolean needFullGroup;

    GroupLock(long onum) {
      this.status = Status.UNCLAIMED;
      this.group = null;
      this.needFullGroup = false;
      this.onum = onum;
    }
  }

  private class SoftRef extends SoftReference<AbstractGroup> {
    /**
     * The set of onums associated with this group.
     */
    final LongSet onums;

    public SoftRef(AbstractGroup group) {
      super(group, queue);
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
          synchronized (table) {
            for (LongIterator it = ref.onums.iterator(); it.hasNext();) {
              long onum = it.next();
              SoftRef entry = table.get(onum);
              if (entry == ref) {
                table.remove(onum);
              }
            }
          }
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
