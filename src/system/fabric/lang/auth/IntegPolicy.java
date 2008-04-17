package fabric.lang.auth;

import fabric.lang.Object;

public interface IntegPolicy extends Policy, Object {
  
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
  IntegPolicy join(IntegPolicy p);

  /**
   * @return the meet of this policy and p.
   */
  IntegPolicy meet(IntegPolicy p);

  public static class $Proxy extends fabric.lang.Object.$Proxy implements
      IntegPolicy {

    public IntegPolicy join(IntegPolicy p) {
      return ((IntegPolicy) fetch()).join(p);
    }

    public IntegPolicy meet(IntegPolicy p) {
      return ((IntegPolicy) fetch()).meet(p);
    }

    public $Proxy(fabric.client.Core core, long onum) {
      super(core, onum);
    }
  }
}
