package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.lang.Object;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface IntegPolicy
  extends fabric.lang.security.Policy, fabric.lang.Object {
    /**
   * Return the join of this policy and p. The set s contains all delegations
   * (i.e., DelegationPairs) that this join result depends upon.
   */
    fabric.lang.security.IntegPolicy join(
      fabric.worker.Store store, fabric.lang.security.IntegPolicy p, java.util.Set dependencies);
    
    /**
   * Return the meet of this policy and p. The set s contains all delegations
   * (i.e., DelegationPairs) that this meet result depends upon.
   */
    fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
                                          fabric.lang.security.IntegPolicy p,
                                          java.util.Set dependencies);
    
    fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
                                          fabric.lang.security.IntegPolicy p);
    
    fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
                                          fabric.lang.security.IntegPolicy p);
    
    fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
                                          fabric.lang.security.IntegPolicy p,
                                          boolean simplify);
    
    fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
                                          fabric.lang.security.IntegPolicy p,
                                          boolean simplify);
    
    fabric.lang.security.IntegPolicy join(fabric.worker.Store store,
                                          fabric.lang.security.IntegPolicy p,
                                          java.util.Set dependencies,
                                          boolean simplify);
    
    fabric.lang.security.IntegPolicy meet(fabric.worker.Store store,
                                          fabric.lang.security.IntegPolicy p,
                                          java.util.Set dependencies,
                                          boolean simplify);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.IntegPolicy {
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.IntegPolicy) fetch()).join(arg1, arg2,
                                                                     arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.IntegPolicy) fetch()).meet(arg1, arg2,
                                                                     arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.IntegPolicy) fetch()).join(arg1,
                                                                     arg2);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2) {
            return ((fabric.lang.security.IntegPolicy) fetch()).meet(arg1,
                                                                     arg2);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.IntegPolicy) fetch()).join(arg1, arg2,
                                                                     arg3);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.IntegPolicy) fetch()).meet(arg1, arg2,
                                                                     arg3);
        }
        
        public fabric.lang.security.IntegPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.IntegPolicy) fetch()).join(arg1, arg2,
                                                                     arg3,
                                                                     arg4);
        }
        
        public fabric.lang.security.IntegPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.IntegPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.IntegPolicy) fetch()).meet(arg1, arg2,
                                                                     arg3,
                                                                     arg4);
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.IntegPolicy) fetch()).relabelsTo(
                                                                  arg1, arg2);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
}
