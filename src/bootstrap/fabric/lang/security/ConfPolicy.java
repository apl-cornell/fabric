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
public interface ConfPolicy
  extends fabric.lang.security.Policy, fabric.lang.Object {
    /**
   * Returns the join of this policy and p. The set s contains all delegations
   * (i.e., DelegationPairs) that this join result depends upon.
   */
    fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies);
    
    fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies);
    
    fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p);
    
    fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p);
    
    fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         boolean simplify);
    
    fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         boolean simplify);
    
    fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies,
                                         boolean simplify);
    
    fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
                                         fabric.lang.security.ConfPolicy p,
                                         java.util.Set dependencies,
                                         boolean simplify);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.ConfPolicy {
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.ConfPolicy) fetch()).join(arg1, arg2,
                                                                    arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3) {
            return ((fabric.lang.security.ConfPolicy) fetch()).meet(arg1, arg2,
                                                                    arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.ConfPolicy) fetch()).join(arg1, arg2);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2) {
            return ((fabric.lang.security.ConfPolicy) fetch()).meet(arg1, arg2);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.ConfPolicy) fetch()).join(arg1, arg2,
                                                                    arg3);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          boolean arg3) {
            return ((fabric.lang.security.ConfPolicy) fetch()).meet(arg1, arg2,
                                                                    arg3);
        }
        
        public fabric.lang.security.ConfPolicy join(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.ConfPolicy) fetch()).join(arg1, arg2,
                                                                    arg3, arg4);
        }
        
        public fabric.lang.security.ConfPolicy meet(
          fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
          java.util.Set arg3, boolean arg4) {
            return ((fabric.lang.security.ConfPolicy) fetch()).meet(arg1, arg2,
                                                                    arg3, arg4);
        }
        
        public boolean relabelsTo(fabric.lang.security.Policy arg1,
                                  java.util.Set arg2) {
            return ((fabric.lang.security.ConfPolicy) fetch()).relabelsTo(arg1,
                                                                          arg2);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 116, -28, 9, -87, 116,
    91, -53, -47, 67, -115, 10, -112, -101, -90, -128, 70, -82, 51, -123, -107,
    -71, 72, -22, 79, 83, -40, 42, -59, 126, 55, 7, -67 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525719795000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
