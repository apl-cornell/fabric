package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
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
    
    public static final byte[] $classHash = new byte[] { -33, 112, -101, 41,
    -125, 2, -119, -88, 25, -17, 38, 89, -81, 12, -120, -15, -121, -71, 31, 75,
    87, -72, -83, -117, 55, 67, -88, -91, 25, -123, -98, 30 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
