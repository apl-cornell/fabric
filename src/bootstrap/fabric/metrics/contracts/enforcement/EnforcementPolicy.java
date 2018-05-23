package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.StatsMap;

/**
 * A policy for enforcing a {@link Contract}. This class is responsible
 * for ensuring a {@link Contract} (and the API implementation) is
 * monitoring evidence of the {@link Contract}'s validity and updating the
 * expiration time correctly. It effectively acts as a bundle of the currently
 * monitored information for enforcing a {@link Contract}.
 */
public interface EnforcementPolicy extends fabric.lang.Object {
    /**
   * @param weakStats stats to use for weakly consistent statistics.
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
    public abstract long expiry(fabric.worker.metrics.StatsMap weakStats);
    
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
   * @param weakStats stats to use for weakly consistent statistics.
   */
    public abstract void activate(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Acquire reconfig locks for the evidence used for this policy.
   */
    public abstract void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.EnforcementPolicy {
        public long expiry(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.contracts.enforcement.EnforcementPolicy)
                      fetch()).expiry(arg1);
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              unapply(arg1);
        }
        
        public void activate(fabric.worker.metrics.StatsMap arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              activate(arg1);
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              acquireReconfigLocks();
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final byte[] $classHash = new byte[] { 114, 97, -8, 70, 107,
    70, 19, -8, 110, -103, 47, -72, -73, -63, 51, 101, -83, 7, -74, 66, 101,
    -82, 72, 76, 58, 8, 6, 51, 115, -91, -25, 68 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1527094903000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
