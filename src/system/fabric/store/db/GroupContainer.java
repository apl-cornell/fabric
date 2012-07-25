package fabric.store.db;

import java.security.PrivateKey;

import fabric.common.AuthorizationUtil;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongSet;
import fabric.dissemination.Glob;
import fabric.lang.security.Principal;
import fabric.worker.Store;

/**
 * A group container contains a group or a glob, and supports converting between
 * them. This class is thread-safe.
 */
public final class GroupContainer {
  private final Store store;
  private final long labelOnum;
  private final PrivateKey signingKey;

  private ObjectGroup group;
  private Glob glob;

  /**
   * The set of onums for the objects contained in this group.
   */
  public final LongSet onums;

  public GroupContainer(Store store, PrivateKey signingKey, ObjectGroup group) {
    this.store = store;
    this.signingKey = signingKey;
    this.group = group;
    this.glob = null;

    this.onums = group.objects().keySet();

    for (SerializedObject obj : group.objects().values()) {
      this.labelOnum = obj.getUpdateLabelOnum();
      return;
    }

    // Shouldn't happen.
    throw new InternalError("Empty object group");
  }

  /**
   * @param principal
   *          The principal accessing the group.
   * @return null if the given principal is not allowed to read the group.
   */
  public ObjectGroup getGroup(Principal principal) {
    if (!AuthorizationUtil.isReadPermitted(principal, store, labelOnum))
      return null;

    ObjectGroup group;
    Glob glob;
    synchronized (this) {
      group = this.group;
      glob = this.glob;
    }

    if (group != null) return group;
    return glob.decrypt(store);
  }

  public Glob getGlob() {
    if (glob != null) return glob;
    synchronized (this) {
      if (glob == null) {
        glob = new Glob(store, group, signingKey);
        group = null;
      }
      return glob;
    }
  }
}
