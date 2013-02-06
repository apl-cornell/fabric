package fabric.store.db;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.security.PrivateKey;
import java.util.Iterator;
import java.util.LinkedList;
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
 * <p>
 * Locking order: table → individual entries → individual group locks.
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
   * Maps onums to <code>Entry</code>s.
   */
  private final LongKeyMap<Entry> table;

  /**
   * Either a SoftRef or a GroupLock. A GroupLock entry indicates that the group
   * is actively being constructed; the associated GroupLock object is used to
   * prevent threads from concurrently attempting to group the same object.
   */
  private final class Entry {
    private SoftRef group;
    private GroupLock lock;

    Entry(GroupLock lock) {
      this.lock = lock;
      this.group = null;
    }

    synchronized void setGroup(AbstractGroup group) {
      if (group instanceof GroupContainer)
        setGroup((GroupContainer) group);
      else setGroup((PartialObjectGroup) group);
    }

    synchronized void setGroup(GroupContainer group) {
      // TODO remove debug checks
      if (getGroup() instanceof GroupContainer) throw new InternalError();

      this.group = new SoftRef(group);
      this.lock = null;
    }

    synchronized void setGroup(PartialObjectGroup group) {
      // TODO remove debug checks
      AbstractGroup curGroup = getGroup();
      if (curGroup instanceof GroupContainer) throw new InternalError();
      if (getLock() != getExactLock(group.lock)) throw new InternalError();

      this.group = new SoftRef(group);
      this.lock = null;
    }

    /**
     * @return the AbstractGroup associated with this entry.
     */
    synchronized AbstractGroup getGroup() {
      if (group == null) return null;
      return group.get();
    }

    /**
     * @return the GroupLock for this entry. If this entry contains a
     *          PartialObjectGroup, then its GroupLock is returned.
     */
    synchronized GroupLock getLock() {
      if (lock != null) return getExactLock(lock);

      AbstractGroup group = getGroup();
      if (group instanceof PartialObjectGroup)
        return getExactLock(((PartialObjectGroup) group).lock);

      return null;
    }

    private GroupLock getExactLock(GroupLock lock) {
      synchronized (lock) {
        if (lock.status == GroupLock.Status.DEFUNCT) {
          return lock.replacement = getExactLock(lock.replacement);
        }

        return lock;
      }
    }

    synchronized SoftRef getRef() {
      return group;
    }
  }

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
    this.table = new LongKeyHashMap<Entry>();
    this.queue = new ReferenceQueue<AbstractGroup>();

    new Collector().start();
  }

  GroupContainer getGroup(long onum) {
    // Check the cache.
    GroupLock lock = null;
    synchronized (table) {
      Entry entry = table.get(onum);
      if (entry != null) {
        synchronized (entry) {
          AbstractGroup group = entry.getGroup();
          if (group instanceof GroupContainer) return (GroupContainer) group;

          lock = entry.getLock();
        }
      }

      if (lock == null) {
        lock = new GroupLock(onum);
        table.put(onum, new Entry(lock));
      }
    }

    // Loop in case the lock gets replaced so we synchronize on the correct lock.
    OUTER: while (true) {
      synchronized (lock) {
        // Condition-variable loop.
        while (true) {
          switch (lock.status) {
          case UNCLAIMED:
            // Claim the lock.
            lock.status = GroupLock.Status.CLAIMED_FOR_FULL_GROUP;
            break OUTER;

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

          case DEFUNCT:
            lock = lock.replacement;
            continue OUTER;
          }

          throw new InternalError("Unknown lock status: " + lock.status);
        }
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
      GroupLock frontierLock =
          attemptLockForPartialGroup(frontierOnum, null, true);
      if (frontierLock == null) continue;

      // Lock obtained. Get a partial group for the frontier object.
      SerializedObject frontierObj = entry.getValue();
      PartialObjectGroup frontierGroup =
          getPartialGroup(frontierOnum, frontierLock, frontierObj);
      if (frontierGroup.size() >= MIN_GROUP_SIZE) {
        // Frontier group is large enough to hold its own. Add it to the cache.
        cacheGroup(frontierGroup.objects, frontierGroup);

        // Unlock the frontier group.
        synchronized (frontierLock) {
          frontierLock.status = GroupLock.Status.UNCLAIMED;
          frontierLock.notifyAll();
        }

        continue;
      }

      // Group on the frontier isn't big enough. Coalesce it with the group
      // we're creating.
      synchronized (table) {
        partialGroup.mergeFrom(frontierGroup);

        cacheGroup(frontierGroup.objects, partialGroup);
      }
    }

    Store store = Worker.getWorker().getStore(database.getName());
    GroupContainer result =
        new GroupContainer(store, signingKey, new ObjectGroup(
            partialGroup.objects));

    // Add the result to the cache: bind all non-surrogate onums to the result.
    cacheGroup(partialGroup.objects, result);

    // Unlock and notify any waiting threads.
    synchronized (lock) {
      lock.group = result;
      lock.status = GroupLock.Status.READY;
      lock.notifyAll();
    }

    return result;
  }

  AbstractGroup removeGroup(long onum) {
    // If there is a thread working on a group for this object, let it finish.
    OUTER: while (true) {
      GroupLock lock = null;
      synchronized (table) {
        Entry entry = table.get(onum);
        if (entry != null) lock = entry.getLock();
      }

      if (lock == null) lock = new GroupLock(onum);

      // Loop in case the lock gets replaced so we synchronize on the correct
      // lock.
      while (true) {
        synchronized (lock) {
          switch (lock.status) {
          case UNCLAIMED:
          case READY:
            synchronized (table) {
              Entry entry = table.get(onum);
              if (entry == null) return null;

              AbstractGroup result = entry.getGroup();
              if (result == null) return null;

              // Remove the group from the table.
              for (LongIterator it = result.onums().iterator(); it.hasNext();) {
                long memberOnum = it.next();
                Entry memberEntry = table.get(memberOnum);
                if (memberEntry == null || memberEntry.getGroup() != result)
                  continue;
                table.remove(memberOnum);
              }

              return result;
            }

          case CLAIMED_FOR_PARTIAL_GROUP:
          case CLAIMED_FOR_FULL_GROUP:
            // Wait for other thread to finish.
            try {
              lock.wait();
            } catch (InterruptedException e) {
            }
            continue OUTER;

          case DEFUNCT:
            lock = lock.replacement;
            continue;
          }

          throw new InternalError("Unknown lock status: " + lock.status);
        }
      }
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

        table.get(entry.getKey()).setGroup(group);
      }
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
      Entry entry = table.get(onum);
      if (entry != null) {
        AbstractGroup group = entry.getGroup();
        if (group instanceof PartialObjectGroup) {
          PartialObjectGroup result = (PartialObjectGroup) group;
          // TODO remove debug check
          if (entry.getLock() != lock)
            throw new InternalError("Broken invariant");
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
      if (attemptLockForPartialGroup(curOnum, lock, false) == null) continue;

      // Add object to partial group.
      group.put(curOnum, curObject);
      groupSize++;

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

    return new PartialObjectGroup(groupSize, group, frontier, lock);
  }

  /**
   * Attempts to lock an onum for creating a partial group.
   *
   * @param groupLock
   *         The lock to use. If non-null and the locking operation is
   *         successful, the given lock will replace any existing lock.
   * @param ignorePartialGroups
   *         If false, an attempt will only be made if the onum is not already
   *         in a group (partial or otherwise). If true, an attempt will be made
   *         even if the onum is already in a partial group.
   *          
   * @return the group's lock, if successful; otherwise, null.
   */
  private GroupLock attemptLockForPartialGroup(long onum, GroupLock groupLock,
      boolean ignorePartialGroups) {
    GroupLock curLock = null;

    synchronized (table) {
      // First look for an existing table entry.
      Entry entry = table.get(onum);
      if (entry != null) {
        synchronized (entry) {
          AbstractGroup group = entry.getGroup();
          if (!ignorePartialGroups && group != null
              || group instanceof GroupContainer) {
            // Group already exists.
            return null;
          }

          curLock = entry.getLock();
        }
      }

      if (curLock == null) {
        // No existing entry (or existing entry is about to be GCed) so create a
        // new one.
        if (groupLock == null) {
          // Create a new groupLock.
          curLock = new GroupLock(onum);
          table.put(onum, new Entry(curLock));
        } else {
          // Bind the onum to the groupLock.
          table.put(onum, new Entry(groupLock));
          return groupLock;
        }
      }

      while (true) {
        if (curLock == groupLock) return groupLock;

        synchronized (curLock) {
          if (curLock.status == GroupLock.Status.DEFUNCT) {
            curLock = curLock.replacement;
            continue;
          }

          if (curLock.status != GroupLock.Status.UNCLAIMED) {
            return null;
          }

          if (groupLock == null) {
            // Use the existing lock.
            curLock.status = GroupLock.Status.CLAIMED_FOR_PARTIAL_GROUP;
            return curLock;
          }

          // Mark the curLock as defunct and replace it with the groupLock.
          curLock.status = GroupLock.Status.DEFUNCT;
          curLock.replacement = groupLock;
          curLock.notifyAll();

          return groupLock;
        }
      }
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
      READY,
      /**
       * Indicates that the lock has been replaced with another one.
       */
      DEFUNCT
    }

    Status status;
    GroupContainer group;
    final LongSet onums;

    /**
     * If the status is DEFUNCT, then this is the replacement GroupLock.
     */
    GroupLock replacement;

    GroupLock(long onum) {
      this.status = Status.UNCLAIMED;
      this.group = null;
      this.onums = new LongHashSet();
      this.onums.add(onum);
      this.replacement = null;
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
              Entry entry = table.get(onum);
              if (entry != null) {
                synchronized (entry) {
                  if (entry.getRef() == ref) {
                    table.remove(onum);
                  }
                }
              }
            }
          }
        } catch (InterruptedException e) {
        }
      }
    }
  }
}
