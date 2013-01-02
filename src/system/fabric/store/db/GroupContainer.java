package fabric.store.db;

import java.security.PrivateKey;

import fabric.common.AuthorizationUtil;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.Glob;
import fabric.lang.security.Principal;
import fabric.store.TransactionManager;
import fabric.worker.Store;

/**
 * A group container contains a group or a glob, and supports converting between
 * them. This class is thread-safe.
 */
public final class GroupContainer extends GroupTable.Entry {
  private final Store store;
  private final long labelOnum;
  private final PrivateKey signingKey;

  private ObjectGroup group;
  private Glob glob;

  /**
   * The version warranty that expires soonest for all the objects in the group.
   */
  private VersionWarranty warranty;

  /**
   * The set of onums for the objects contained in this group.
   */
  public final LongSet onums;

  public GroupContainer(Store store, PrivateKey signingKey, ObjectGroup group) {
    this.store = store;
    this.signingKey = signingKey;
    this.group = group;
    this.glob = null;
    this.warranty = null;

    this.onums = group.objects().keySet();

    Long labelOnum = null;
    for (Pair<SerializedObject, VersionWarranty> pair : group.objects()
        .values()) {
      // Set the group's label onum.
      labelOnum = pair.first.getUpdateLabelOnum();
      break;
    }

    if (labelOnum == null) {
      // Shouldn't happen.
      throw new InternalError("Empty object group");
    }

    this.labelOnum = labelOnum;
    this.warranty = group.expiry();
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

  public synchronized Glob getGlob() {
    if (glob == null) {
      glob = new Glob(store, group, signingKey);
      group = null;
    }
    return glob;
  }

  /**
   * Refreshes the warranties for the objects in the group. Should only be
   * called by the store.
   */
  public synchronized void refreshWarranties(TransactionManager tm) {
    if (warranty != null && !warranty.expired(true)) return;

    // Ensure group is decrypted.
    if (group == null) {
      group = glob.decrypt(store);
      glob = null;
    }

    group.refreshWarranties(tm);
    warranty = group.expiry();
  }

  @Override
  protected LongIterator onums() {
    return onums.iterator();
  }
}
