package fabric.store;

import fabric.common.SerializedObject;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.store.db.GroupTable;

/**
 * A partially assembled object group.
 */
public class PartialObjectGroup extends GroupTable.Entry {
  private long groupID;

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

  public PartialObjectGroup(int size, LongKeyMap<SerializedObject> objects,
      LongKeyMap<SerializedObject> frontier) {
    this.groupID = -1;
    this.size = size;
    this.objects = objects;
    this.frontier = frontier;
  }

  public void setID(long groupID) {
    this.groupID = groupID;
  }

  public long groupID() {
    return groupID;
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
  }
}
