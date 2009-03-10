package fabric.core.store;

import java.security.PrivateKey;

import fabric.client.Core;
import fabric.client.debug.Timing;
import fabric.common.AuthorizationUtil;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.InternalError;
import fabric.dissemination.Glob;
import fabric.lang.NodePrincipal;

/**
 * Abstracts groups and globs.
 */
public final class GroupContainer {
  private final Core core;
  private final long labelOnum;

  private ObjectGroup group;
  private Glob glob;

  public GroupContainer(Core core, ObjectGroup group) {
    this.core = core;
    this.group = group;
    this.glob = null;

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
    try {
      Timing.READ_CHECK.begin();

      if (!AuthorizationUtil.isReadPermitted(principal, core, labelOnum))
        return null;
    } finally {
      Timing.READ_CHECK.end();
    }

    ObjectGroup group;
    Glob glob;
    synchronized (this) {
      group = this.group;
      glob = this.glob;
    }

    if (group != null) return group;
    return glob.decrypt(core);
  }

  public Glob getGlob(PrivateKey key) {
    if (glob != null) return glob;
    synchronized (this) {
      if (glob == null) {
        glob = new Glob(core, group, key);
        group = null;
      }
      return glob;
    }
  }
}
