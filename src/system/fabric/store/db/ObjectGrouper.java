package fabric.store.db;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.security.PrivateKey;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import fabric.common.Logging;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * Constructs and caches object groups. This class is thread-safe.
 * <p>
 * Locking order:
 * <ol>
 * <li>individual entries (when modifying anything in the entry)</li>
 * <li>individual group locks (when modifying anything about the lock)</li>
 * <li>Entry.groupRefMutex (when modifying the group ref)</li>
 * </ol>
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
  private final ConcurrentLongKeyMap<Entry> table;

  private final class Entry {
    /**
     * Refers to either a partial group (PartialObjectGroup) or a full group
     * (GroupContainer).
     */
    private SoftRef group;

    /**
     * Used to coordinate group creation. Prevents the associated group from
     * being simultaneously created by multiple threads. This is null once the
     * full group is created.
     */
    private GroupLock lock;

    /**
     * Mutex object for accessing group field.
     */
    private final Object groupRefMutex;

    Entry(GroupLock lock) {
      this.lock = lock;
      this.group = null;
      this.groupRefMutex = new Object();
    }

    synchronized void setRef(SoftRef ref) {
      synchronized (groupRefMutex) {
        // TODO remove debug checks
        {
          AbstractGroup curGroup = getGroup();
          if (curGroup instanceof GroupContainer) {
            throw new InternalError();
          }

          if (curGroup instanceof PartialObjectGroup) {
            AbstractGroup refGroup = ref.get();
            if (refGroup instanceof PartialObjectGroup && refGroup != curGroup)
              throw new InternalError();
          }
        }
        this.group = ref;
      }

      AbstractGroup group = getGroup();
      if (group instanceof GroupContainer) {
        this.lock = null;
      }
      // TODO remove debug checks
      else if (getLock() != getExactLock(((PartialObjectGroup) group).lock)) {
        throw new InternalError();
      }
    }

    /**
     * @return the AbstractGroup associated with this entry.
     */
    AbstractGroup getGroup() {
      synchronized (groupRefMutex) {
        if (group == null) return null;
        return group.get();
      }
    }

    /**
     * @return the GroupLock for this entry. If this entry contains a
     *          PartialObjectGroup, then its GroupLock is returned.
     */
    synchronized GroupLock getLock() {
      return lock == null ? null : (lock = getExactLock(lock));
    }

    private GroupLock getExactLock(GroupLock lock) {
      synchronized (lock) {
        if (lock.status == GroupLock.Status.REPLACED) {
          return lock.replacement = getExactLock(lock.replacement);
        }

        return lock;
      }
    }

    SoftRef getRef() {
      synchronized (groupRefMutex) {
        return group;
      }
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
    this.table = new ConcurrentLongKeyHashMap<>();
    this.queue = new ReferenceQueue<>();

    new Collector().start();
  }

  GroupContainer getGroup(long headOnum) {
    GroupLock lock;
    PartialObjectGroup partialGroup = null;

    // Loop in case the lock we get becomes DEAD.
    OUTERMOST: while (true) {
      Entry entry;
      // Loop until we've established a proper entry for the onum.
      while (true) {
        Entry newEntry = new Entry(new GroupLock(headOnum));
        entry = table.putIfAbsent(headOnum, newEntry);
        if (entry == null) entry = newEntry;

        AbstractGroup group;
        synchronized (entry) {
          group = entry.getGroup();
          lock = entry.getLock();
        }

        if (group instanceof GroupContainer) {
          return (GroupContainer) group;
        }

        if (lock == null) {
          // Entry had a full group that has been GCed. Remove the entry and start
          // over.
          table.remove(headOnum, entry);
          continue;
        }

        // Have a proper entry now.
        break;
      }

      // If the lock has been claimed, wait for the other thread to finish and try
      // again. Otherwise, claim the lock.

      // Loop in case the lock gets replaced so we synchronize on the correct lock.
      SYNC_LOCK: while (true) {
        synchronized (lock) {
          // Condition-variable loop.
          CLAIM_WAIT: while (true) {
            switch (lock.status) {
            case FRESH:
              // Claim the lock.
              lock.status = GroupLock.Status.CLAIMED_FOR_FULL_GROUP;
              break OUTERMOST;

            case CLAIMED_FOR_PARTIAL_GROUP:
            case CLAIMED_FOR_FULL_GROUP:
              // Wait for a signal.
              try {
                lock.wait();
              } catch (InterruptedException e) {
                Logging.logIgnoredInterruptedException(e);
              }
              continue CLAIM_WAIT;

            case READY:
              return lock.group;

            case REPLACED:
              lock = lock.replacement;
              continue SYNC_LOCK;

            case PARTIALLY_GROUPED:
              // Retrieve the partial group.
              partialGroup = (PartialObjectGroup) entry.getGroup();
              if (partialGroup != null) {
                // Claim the lock.
                lock.status = GroupLock.Status.CLAIMED_FOR_FULL_GROUP;
                break OUTERMOST;
              }

              // Partial group has been GCed. Treat the lock status as being
              // DEAD.

              // $FALL-THROUGH$
            case DEAD:
              table.remove(headOnum, entry);
              continue OUTERMOST;
            }

            throw new InternalError("Unknown lock status: " + lock.status);
          }
        }
      }
    }

    // We are responsible for creating the group. Get a partial group for the
    // onum.
    if (partialGroup == null)
      partialGroup = getPartialGroup(headOnum, lock, null);
    if (partialGroup == null) {
      // No such group. Unlock and notify any waiting threads.
      synchronized (lock) {
        lock.group = null;
        lock.status = GroupLock.Status.READY;
        lock.notifyAll();
      }
      return null;
    }

    // Add any small groups on the partial group's frontier.
    for (LongKeyMap.Entry<SerializedObject> entry : partialGroup.frontier
        .entrySet()) {
      long frontierOnum = entry.getKey();
      SerializedObject frontierObj = entry.getValue();

      // Always add surrogate objects.
      if (frontierObj.isSurrogate()) {
        partialGroup.addSurrogate(frontierOnum, frontierObj);
        continue;
      }

      // Make sure the frontier object isn't already in a (non-partial) group
      // and attempt to obtain a lock on the frontier object.
      Pair<GroupLock, PartialObjectGroup> lockResult =
          attemptLockForPartialGroup(frontierOnum, null, true);
      if (lockResult == null) continue;

      // Lock obtained. Get a partial group for the frontier object.
      GroupLock frontierLock = lockResult.first;
      PartialObjectGroup frontierGroup = lockResult.second;
      if (frontierGroup == null) {
        frontierGroup =
            getPartialGroup(frontierOnum, frontierLock, frontierObj);
      }
      if (frontierGroup.size() >= MIN_GROUP_SIZE) {
        // Frontier group is large enough to hold its own. Add it to the cache.
        cacheFrontierGroup(frontierGroup.objects, frontierGroup);

        // Unlock the frontier group.
        synchronized (frontierLock) {
          frontierLock.status = GroupLock.Status.PARTIALLY_GROUPED;
          frontierLock.notifyAll();
        }

        continue;
      }

      // Group on the frontier isn't big enough. Coalesce it with the group
      // we're creating.
      partialGroup.mergeFrom(frontierGroup);
      cacheGroup(headOnum, frontierGroup.objects, partialGroup);
    }

    Store store = Worker.getWorker().getStore(database.getName());
    GroupContainer result =
        new GroupContainer(store, signingKey, new ObjectGroup(
            partialGroup.objects));

    // Add the result to the cache: bind all non-surrogate onums to the result.
    cacheGroup(headOnum, partialGroup.objects, result);

    // Unlock and notify any waiting threads.
    synchronized (lock) {
      lock.group = result;
      lock.status = GroupLock.Status.READY;
      lock.notifyAll();
    }

    return result;
  }

  /**
   * @return the set of onums in the group that was removed. If no group was
   *          removed, then null is returned.
   */
  LongSet removeGroup(long onum) {
    AbstractGroup group;

    // If there is a thread working on a group for this object, let it finish
    // before removing the group. (This can be made more efficient with a
    // fancy mechanism to signal that the thread should redo its group.)
    OUTER: while (true) {
      GroupLock lock = null;
      {
        Entry entry = table.get(onum);
        if (entry == null) return null;

        lock = entry.getLock();
      }

      // Loop in case the lock gets replaced so we synchronize on the correct
      // lock.
      while (true) {
        // First, handle case where group was already constructed.
        if (lock == null) {
          // Check the table again in case the entry got GCed or replaced while
          // we weren't looking.
          Entry entry = table.get(onum);
          if (entry == null) return null;

          group = entry.getGroup();
          if (group == null) return null;

          lock = entry.getLock();
          if (lock != null) continue OUTER;
          break OUTER;
        }

        synchronized (lock) {
          switch (lock.status) {
          case FRESH:
            return null;

          case PARTIALLY_GROUPED:
          case READY:
            // Check the table again in case the entry got GCed or replaced
            // while we weren't looking.
            Entry entry = table.get(onum);
            if (entry == null) return null;

            group = entry.getGroup();
            if (group == null) return null;

            lock.status = GroupLock.Status.DEAD;
            break OUTER;

          case CLAIMED_FOR_PARTIAL_GROUP:
          case CLAIMED_FOR_FULL_GROUP:
            // Wait for other thread to finish, and then start over.
            try {
              lock.wait();
            } catch (InterruptedException e) {
              Logging.logIgnoredInterruptedException(e);
            }
            continue OUTER;

          case REPLACED:
            lock = lock.replacement;
            continue;

          case DEAD:
            return null;
          }

          throw new InternalError("Unknown lock status: " + lock.status);
        }
      }
    }

    // Remove the group from the table.
    for (LongIterator it = group.onums().iterator(); it.hasNext();) {
      long memberOnum = it.next();
      Entry memberEntry = table.get(memberOnum);
      if (memberEntry == null || memberEntry.getGroup() != group) continue;
      table.remove(memberOnum, memberEntry);
    }

    return group.onums();
  }

  /**
   * Adds the given abstract group to the cache.
   */
  private void cacheGroup(long headOnum,
      LongKeyMap<SerializedObject> groupObjects, AbstractGroup group) {
    SoftRef ref = new SoftRef(group);
    for (LongKeyMap.Entry<SerializedObject> entry : groupObjects.entrySet()) {
      SerializedObject obj = entry.getValue();
      if (obj.getOnum() != headOnum && obj.isSurrogate()) continue;

      table.get(entry.getKey()).setRef(ref);
    }
  }

  /**
   * Adds the given frontier group to the cache.
   */
  private void cacheFrontierGroup(LongKeyMap<SerializedObject> groupObjects,
      PartialObjectGroup group) {
    SoftRef ref = new SoftRef(group);
    for (LongKeyMap.Entry<SerializedObject> entry : groupObjects.entrySet()) {
      SerializedObject obj = entry.getValue();
      if (obj.isSurrogate()) continue;

      table.get(entry.getKey()).setRef(ref);
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
   * Assumes the current thread has the lock for the given onum and that all
   * previously made groups have been GCed. The caller is responsible for
   * releasing the lock.
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
    // TODO remove debug check
    Entry entry = table.get(onum);
    if (entry != null && entry.getRef() != null) {
      throw new InternalError("Broken invariant");
    }

    if (obj == null) obj = database.read(onum);
    if (obj == null) return null;

    long headLabelOnum = obj.getUpdateLabelOnum();

    LongKeyMap<SerializedObject> group = new LongKeyHashMap<>(MIN_GROUP_SIZE);
    LongKeyMap<SerializedObject> frontier = new LongKeyHashMap<>();

    // Number of non-surrogate objects in the group.
    int groupSize = 0;

    // Do a breadth-first traversal and add objects to the group.
    Queue<SerializedObject> toVisit = new LinkedList<>();
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

        // Ensure that the related object has the same label as the head object.
        // We could be smarter here, but to avoid calling into the worker, let's
        // hope pointer equality is sufficient.
        long relatedLabelOnum = related.getUpdateLabelOnum();
        if (headLabelOnum == relatedLabelOnum) {
          toVisit.add(related);
        }
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
   *         in a group (partial or otherwise). This mode is used to lock an
   *         onum so it can be added to a partial group that is being
   *         constructed. Otherwise, if ignorePartialGroups is true, an attempt
   *         will be made even if the onum is already in a partial group.
   *
   * @return if successful, the group's lock and the existing partial group, if
   *          one was already made. Null is returned if the locking operation
   *          was unsuccessful.
   */
  private Pair<GroupLock, PartialObjectGroup> attemptLockForPartialGroup(
      long onum, final GroupLock groupLock, boolean ignorePartialGroups) {
    // Loop in case the lock we get becomes DEAD.
    OUTERMOST: while (true) {
      GroupLock curLock;
      Entry entry;
      // Loop until we've established a proper entry for the onum.
      while (true) {
        // First, ensure that there is a table entry for the given onum.
        GroupLock newEntryGroupLock =
            groupLock == null ? new GroupLock(onum) : groupLock;
        Entry newEntry = new Entry(newEntryGroupLock);
        entry = table.putIfAbsent(onum, newEntry);
        if (entry == null) entry = newEntry;

        AbstractGroup group;
        synchronized (entry) {
          group = entry.getGroup();
          curLock = entry.getLock();
        }

        if (!ignorePartialGroups && group != null
            || group instanceof GroupContainer) {
          // Group already exists.
          return null;
        }

        if (curLock == null) {
          // Entry had a full group that has been GCed. Remove the entry and start
          // over.
          table.remove(onum, entry);
          continue;
        }

        // Have a proper entry now.
        break;
      }

      while (true) {
        if (curLock == groupLock) {
          return new Pair<>(groupLock, (PartialObjectGroup) entry.getGroup());
        }

        PartialObjectGroup existingGroup = null;
        synchronized (curLock) {
          switch (curLock.status) {
          case FRESH:
            break;

          case REPLACED:
            curLock = curLock.replacement;
            continue;

          case CLAIMED_FOR_PARTIAL_GROUP:
          case CLAIMED_FOR_FULL_GROUP:
          case READY:
            return null;

          case PARTIALLY_GROUPED:
            existingGroup = (PartialObjectGroup) entry.getGroup();
            if (existingGroup != null) {
              if (!ignorePartialGroups) {
                // Group already exists.
                return null;
              }

              break;
            }

            // Partial group has been GCed. Treat the lock status as being DEAD.

            // $FALL-THROUGH$
          case DEAD:
            table.remove(onum, entry);
            continue OUTERMOST;
          }

          if (groupLock == null) {
            // Use the existing lock.
            curLock.status = GroupLock.Status.CLAIMED_FOR_PARTIAL_GROUP;
            return new Pair<>(curLock, existingGroup);
          }

          // Replace curLock with the groupLock.
          curLock.status = GroupLock.Status.REPLACED;
          curLock.replacement = groupLock;
          curLock.notifyAll();

          return new Pair<>(groupLock, existingGroup);
        }
      }
    }
  }

  static class GroupLock {
    static enum Status {
      /**
       * Indicates that the lock is currently unclaimed, and has never been
       * claimed.
       */
      FRESH,
      /**
       * Indicates that the lock is currently claimed by a thread that is
       * creating a partial group
       */
      CLAIMED_FOR_PARTIAL_GROUP,
      /**
       * Indicates that the lock is currently unclaimed, but was once claimed
       * by a thread for a partial group. The partial group may have since been
       * GCed.
       */
      PARTIALLY_GROUPED,
      /**
       * Indicates that the lock is currently claimed by a thread that is
       * creating a full group.
       */
      CLAIMED_FOR_FULL_GROUP,
      /**
       * Indicates that a full group has been created and is referenced by the
       * lock's <code>group</code> field.
       */
      READY,
      /**
       * Indicates that the lock has been replaced with another one.
       */
      REPLACED,
      /**
       * Indicates that the lock is no longer in use and its associated entries
       * should be replaced.
       */
      DEAD
    }

    Status status;
    GroupContainer group;

    /**
     * If the status is REPLACED, then this is the replacement GroupLock.
     */
    GroupLock replacement;

    GroupLock(long onum) {
      this.status = Status.FRESH;
      this.group = null;
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
          for (LongIterator it = ref.onums.iterator(); it.hasNext();) {
            long onum = it.next();
            Entry entry = table.get(onum);
            if (entry != null) {
              synchronized (entry) {
                if (entry.getRef() == ref) {
                  GroupLock lock = entry.getLock();
                  if (lock != null) {
                    // Loop in case the lock gets replaced so we synchronize on
                    // the correct lock.
                    INNER: while (true) {
                      synchronized (lock) {
                        switch (lock.status) {
                        case FRESH:
                          throw new InternalError(
                              "Shouldn't get here. Broken invariant?");

                        case CLAIMED_FOR_PARTIAL_GROUP:
                        case CLAIMED_FOR_FULL_GROUP:
                          // A thread is actively working on the group and
                          // should have an active reference to the group.
                          throw new InternalError(
                              "Shouldn't get here. Broken invariant?");

                        case PARTIALLY_GROUPED:
                          lock.status = GroupLock.Status.DEAD;
                          break INNER;

                        case READY:
                          // Shouldn't get here. The soft ref that was snapped
                          // should have been pinned by lock.group.
                          throw new InternalError(
                              "Shouldn't get here. Broken invariant?");

                        case REPLACED:
                          lock = lock.replacement;
                          continue;

                        case DEAD:
                          break INNER;
                        }

                        throw new InternalError("Unknown lock status: "
                            + lock.status);
                      }
                    }
                  }

                  table.remove(onum, entry);
                }
              }
            }
          }
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
      }
    }
  }
}
