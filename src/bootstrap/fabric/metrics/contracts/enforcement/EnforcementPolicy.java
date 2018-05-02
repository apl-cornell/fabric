package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;

/**
 * A policy for enforcing a {@link Contract}. This class is responsible
 * for ensuring a {@link Contract} (and the API implementation) is
 * monitoring evidence of the {@link Contract}'s validity and updating the
 * expiration time correctly. It effectively acts as a bundle of the currently
 * monitored information for enforcing a {@link Contract}.
 */
public interface EnforcementPolicy extends fabric.lang.Object {
    /**
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
    public abstract long expiry();
    
    /**
   * Update book-keeping to use this {@link EnforcementPolicy} for the given
   * {@link Contract}. This will add the given {@link Contract} as
   * an {@link metrics.util.Observer Observer} of the necessary
   * {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link Contract} to apply this policy to.
   */
    public abstract void apply(fabric.metrics.contracts.Contract mc);
    
    /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link Contract}. This will remove the given
   * {@link Contract} as an {@link metrics.util.Observer Observer} of
   * the necessary {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link Contract} to stop applying this policy to.
   */
    public abstract void unapply(fabric.metrics.contracts.Contract mc);
    
    /**
   * Activate this policy, activating witnesses and setting the expiry.
   */
    public abstract void activate();
    
    /**
   * Acquire reconfig locks for the evidence used for this policy.
   */
    public abstract void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.EnforcementPolicy {
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.EnforcementPolicy)
                      fetch()).expiry();
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              unapply(arg1);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              activate();
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              acquireReconfigLocks();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { -117, -77, -62, -94,
    -47, -70, -33, 38, 44, 79, 10, 59, -47, 27, -98, 36, 18, 8, 33, 3, 105,
    -127, -106, 32, 114, -56, -33, 124, 76, -95, 79, -63 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1525212243000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
