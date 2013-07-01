package fabric.store.db;

import java.security.PrivateKey;

import fabric.common.AuthorizationUtil;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.VersionWarranty.Binding;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.InternalError;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongKeyMap.Entry;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.security.Principal;
import fabric.store.TransactionManager;
import fabric.worker.Store;

/**
 * A group container contains a group or a glob, and supports converting between
 * them. This class is thread-safe.
 */
public final class GroupContainer extends ObjectGrouper.AbstractGroup {
  private final Store store;
  private final long labelOnum;
  private final PrivateKey signingKey;

  // Invariant: exactly one of group and glob is null. We start with a group,
  // and when it's encrypted into a glob, we throw away the group. If we ever
  // need the group again, we simply decrypt the glob. (This is a time/space
  // trade-off, and we're erring on the side of space.)
  private ObjectGroup group;
  private ObjectGlob glob;

  /**
   * Warranties on the group's members, keyed by onum.
   */
  private ConcurrentLongKeyMap<Binding> warranties;

  /**
   * The set of onums for the objects contained in this group, mapped to the
   * objects' version numbers.
   */
  public final LongKeyMap<Integer> onumsToVersions;

  public GroupContainer(Store store, PrivateKey signingKey, ObjectGroup group) {
    this.store = store;
    this.signingKey = signingKey;
    this.group = group;
    this.glob = null;

    this.onumsToVersions = new LongKeyHashMap<>();
    for (Entry<SerializedObject> entry : group.objects().entrySet()) {
      onumsToVersions.put(entry.getKey(), entry.getValue().getVersion());
    }

    Long labelOnum = null;
    for (SerializedObject obj : group.objects().values()) {
      // Set the group's label onum.
      labelOnum = obj.getUpdateLabelOnum();
      break;
    }

    if (labelOnum == null) {
      // Shouldn't happen.
      throw new InternalError("Empty object group");
    }

    this.labelOnum = labelOnum;
    this.warranties = new ConcurrentLongKeyHashMap<>();
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
    ObjectGlob glob;
    synchronized (this) {
      group = this.group;
      glob = this.glob;
    }

    if (group == null) group = glob.decrypt();
    return group;
  }

  /**
   * @param principal
   *          The principal accessing the group.
   * @return null if the given principal is not allowed to read the group.
   */
  public Pair<ObjectGroup, WarrantyGroup> getGroups(Principal principal) {
    ObjectGroup objectGroup = getGroup(principal);
    if (objectGroup == null) return null;

    return new Pair<>(objectGroup, getWarranties());
  }

  public synchronized ObjectGlob getGlob() {
    if (glob == null) {
      glob = new ObjectGlob(store, group, signingKey);
      group = null;
    }
    return glob;
  }

  public Pair<ObjectGlob, WarrantyGlob> getGlobs() {
    WarrantyGroup warrantyGroup = getWarranties();
    WarrantyGlob warrantyGlob =
        warrantyGroup == null ? null : new WarrantyGlob(store, signingKey,
            warrantyGroup);

    return new Pair<>(getGlob(), warrantyGlob);
  }

  /**
   * Refreshes the warranties for the objects in the group. This is done by
   * creating new warranties for any objects whose warranties have expired.
   * Should only be called by the store.
   */
  public synchronized void refreshWarranties(TransactionManager tm) {
    if (!shortestWarranty().expired(true)) return;

    // Ensure group is decrypted.
    if (group == null) {
      group = glob.decrypt();
      glob = null;
    }

    // This call will eventually call into the SubscriptionManager, which will
    // then update the state in this GroupContainer with the new warranties.
    // This is kinda gross, but we do it this way, because this isn't the only
    // path through which warranties are refreshed.
    tm.refreshWarranties(onumsToVersions);
  }

  @Override
  protected LongSet onums() {
    return onumsToVersions.keySet();
  }

  /**
   * Adds the given warranty refreshes to the group.
   * 
   * Used on the store. The corresponding call for the worker's cache is
   * ObjectGroup.incorporate(). These are separate because the group on the
   * store might have been encrypted already, so we use the container to collect
   * up all relevant warranty refreshes. 
   */
  public void addRefreshedWarranties(LongKeyMap<Binding> updatesByOnum) {
    for (LongIterator it = onumsToVersions.keySet().iterator(); it.hasNext();) {
      long onum = it.next();
      Binding binding = updatesByOnum.get(onum);
      if (binding == null) continue;

      // Update refreshedWarranties.
      while (true) {
        Binding existingBinding = warranties.putIfAbsent(onum, binding);
        if (existingBinding == null) break;

        // Check if we should replace the existing binding.
        if (existingBinding.versionNumber > binding.versionNumber) {
          // This shouldn't really happen, but just in case...
          break;
        }

        if (!binding.warranty().expiresAfter(existingBinding.warranty()))
          break;

        // Need to replace.
        if (warranties.replace(onum, existingBinding, binding)) {
          // Success!
          break;
        }

        // Existing binding was replaced while we weren't looking loop to try
        // again.
      }
    }
  }

  public WarrantyGroup getWarranties() {
    LongKeyMap<Binding> warranties = new LongKeyHashMap<>(this.warranties);
    if (warranties.isEmpty()) return null;
    return new WarrantyGroup(warranties);
  }

  private VersionWarranty shortestWarranty() {
    VersionWarranty result = VersionWarranty.EXPIRED_WARRANTY;
    for (Binding binding : warranties.values()) {
      VersionWarranty curWarranty = binding.warranty();
      if (curWarranty.expiresAfter(result)) result = curWarranty;
    }

    return result;
  }
}
