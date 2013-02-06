package fabric.store.db;

import static fabric.store.db.ObjectGrouper.GroupLock.Status.DEFUNCT;
import fabric.common.SerializedObject;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.store.db.ObjectGrouper.GroupLock;

/**
 * A partially assembled object group.
 */
public class PartialObjectGroup extends ObjectGrouper.AbstractGroup {
  /**
   * The number of non-surrogate objects in the group.
   */
  private int size;

  /**
   * The set of objects in the partially assembled group.
   */
  public final LongKeyMap<SerializedObject> objects;

  /**
   * The set of objects in the partially assembled group's frontier.
   */
  public final LongKeyMap<SerializedObject> frontier;

  /**
   * The GroupLock for this group. This is non-null exactly while the group is
   * constructed.
   */
  GroupLock lock;

  /**
   * @param size the number of non-surrogate objects in the group.
   */
  public PartialObjectGroup(int size, LongKeyMap<SerializedObject> objects,
      LongKeyMap<SerializedObject> frontier, GroupLock lock) {
    this.size = size;
    this.objects = objects;
    this.frontier = frontier;
    this.lock = lock;
  }

  /**
   * @return the number of non-surrogate objects in the partial group.
   */
  public int size() {
    return size;
  }

  @Override
  protected LongSet onums() {
    return objects.keySet();
  }

  public void mergeFrom(PartialObjectGroup from) {
    size += from.size;
    objects.putAll(from.objects);
    frontier.putAll(from.frontier);

    synchronized (from.lock) {
      from.lock.status = DEFUNCT;
      from.lock.replacement = this.lock;
      from.lock.notifyAll();
    }

    from.lock = lock;
  }
}
