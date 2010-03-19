package fabric.core.db;

import java.security.PrivateKey;

import fabric.client.Core;
import fabric.common.AuthorizationUtil;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongSet;
import fabric.dissemination.Glob;
import fabric.lang.NodePrincipal;

/**
 * Abstracts groups and globs.  This class is thread-safe.
 */
public final class GroupContainer {
  private final Core core;
  private final long labelOnum;
  private final PrivateKey signingKey;

  private ObjectGroup group;
  private Glob glob;
  
  /**
   * The set of onums for the objects contained in this group.
   */
  public final LongSet onums;

  public GroupContainer(Core core, PrivateKey signingKey, ObjectGroup group) {
    this.core = core;
    this.signingKey = signingKey;
    this.group = group;
    this.glob = null;
    
    this.onums = group.objects().keySet();

    for (SerializedObject obj : group.objects().values()) {
      this.labelOnum = obj.getLabelOnum();
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
  public ObjectGroup getGroup(NodePrincipal principal) {
    if (!AuthorizationUtil.isReadPermitted(principal, core, labelOnum))
      return null;

    ObjectGroup group;
    Glob glob;
    synchronized (this) {
      group = this.group;
      glob = this.glob;
    }

    if (group != null) return group;
    return glob.decrypt(core);
  }

  public Glob getGlob() {
    if (glob != null) return glob;
    synchronized (this) {
      if (glob == null) {
        glob = new Glob(core, group, signingKey);
        group = null;
      }
      return glob;
    }
  }
}
