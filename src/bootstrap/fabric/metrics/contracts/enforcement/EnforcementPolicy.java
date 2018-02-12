package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;

/**
 * A policy for enforcing a {@link MetricContract}. This class is responsible
 * for ensuring a {@link MetricContract} (and the API implementation) is
 * monitoring evidence of the {@link MetricContract}'s validity and updating the
 * expiration time correctly. It effectively acts as a bundle of the currently
 * monitored information for enforcing a {@link MetricContract}.
 */
public interface EnforcementPolicy extends fabric.lang.Object {
    /**
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
    public abstract long expiry();
    
    /**
   * Update book-keeping to use this {@link EnforcementPolicy} for the given
   * {@link MetricContract}. This will add the given {@link MetricContract} as
   * an {@link metrics.util.Observer Observer} of the necessary
   * {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to apply this policy to.
   */
    public abstract void apply(fabric.metrics.contracts.MetricContract mc);
    
    /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link MetricContract}. This will remove the given
   * {@link MetricContract} as an {@link metrics.util.Observer Observer} of
   * the necessary {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to stop applying this policy to.
   */
    public abstract void unapply(fabric.metrics.contracts.MetricContract mc);
    
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
        
        public void apply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract arg1) {
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
    
    public static final byte[] $classHash = new byte[] { -70, 58, -107, 98, 78,
    -32, -64, 33, 12, -32, -38, 109, -17, -57, 126, 50, -127, 110, 97, -3, -40,
    -29, 44, -79, -33, 80, 30, 38, -50, 46, -52, -122 };
    java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    long jlc$SourceLastModified$fabil = 1518448064000L;
    java.lang.String jlc$ClassType$fabil =
      "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";
}
