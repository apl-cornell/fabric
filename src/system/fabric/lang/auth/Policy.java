package fabric.lang.auth;

import fabric.client.Core;
import fabric.lang.Object;

/**
 * A Policy is a component of a label, and is either an integrity policy or a
 * confidentiality policy.
 */
public interface Policy extends Object {
  // This is commented out until we have fabric.util.Set written in Java.
  // /**
  // * Determines whether this policy relabels to policy p. If this method
  // * returns true, then all delegations on which this result depends (i.e.,
  // * DelegationPairs) are added to <code>dependencies</code>. If this method
  // * returns false, then <code>dependencies</code> remains unaltered.
  // */
  // boolean relabelsTo(Policy p, Set dependencies);

  public static class $Proxy extends Object.$Proxy implements Policy {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }
  }
}
