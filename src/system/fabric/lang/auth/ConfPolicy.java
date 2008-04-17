package fabric.lang.auth;

import fabric.lang.Object;

public interface ConfPolicy extends Policy, Object {

  // This is commented out until we have fabric.util.Set written in Java.
  // /**
  // * Returns the join/meet of this policy and p. Upon return, all delegations
  // * (i.e., DelegationPairs) upon which the join/meet result depends will be
  // * added to <code>dependencies</code>.
  // */
  // ConfPolicy join(ConfPolicy p, Set dependencies);
  //
  // ConfPolicy meet(ConfPolicy p, Set dependencies);

  /**
   * @return the join of this policy and p.
   */
  ConfPolicy join(ConfPolicy p);

  /**
   * @return the meet of this policy and p.
   */
  ConfPolicy meet(ConfPolicy p);

  public static class $Proxy extends Object.$Proxy implements ConfPolicy {
    public $Proxy(fabric.client.Core core, long onum) {
      super(core, onum);
    }

    public ConfPolicy join(ConfPolicy p) {
      return ((ConfPolicy) fetch()).join(p);
    }

    public ConfPolicy meet(ConfPolicy p) {
      return ((ConfPolicy) fetch()).meet(p);
    }
  }
}
