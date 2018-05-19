package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.StatsMap;
import fabric.worker.transaction.TransactionManager;

/**
 * An {@link EnforcementPolicy} for enforcing a {@link Contract}s by
 * checking every update to the associated {@link Metric}, using equality to a
 * given value.
 */
public interface DirectEqualityPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy,
          fabric.lang.Object
{
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public double get$value();
    
    public double set$value(double val);
    
    public double postInc$value();
    
    public double postDec$value();
    
    public long get$expiry();
    
    public long set$expiry(long val);
    
    public long postInc$expiry();
    
    public long postDec$expiry();
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param metric
   *            the Metric the associated contract is bounding.
   * @param rate
   *            the rate of the bound enforced by the associated contract.
   * @param base
   *            the base of the bound enforced by the associated contract.
   */
    public fabric.metrics.contracts.enforcement.DirectEqualityPolicy
      fabric$metrics$contracts$enforcement$DirectEqualityPolicy$(
      fabric.metrics.Metric metric, double value);
    
    public void activate(fabric.worker.metrics.StatsMap weakStats);
    
    public void refresh(fabric.worker.metrics.StatsMap weakStats);
    
    public long expiry(fabric.worker.metrics.StatsMap weakStats);
    
    public void apply(fabric.metrics.contracts.Contract mc);
    
    public void unapply(fabric.metrics.contracts.Contract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.DirectEqualityPolicy {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).set$metric(val);
        }
        
        public double get$value() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).get$value();
        }
        
        public double set$value(double val) {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).set$value(val);
        }
        
        public double postInc$value() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).postInc$value();
        }
        
        public double postDec$value() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).postDec$value();
        }
        
        public long get$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).get$expiry();
        }
        
        public long set$expiry(long val) {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).set$expiry(val);
        }
        
        public long postInc$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).postInc$expiry();
        }
        
        public long postDec$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).postDec$expiry();
        }
        
        public boolean get$activated() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).get$activated();
        }
        
        public boolean set$activated(boolean val) {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                      _Impl) fetch()).set$activated(val);
        }
        
        public fabric.metrics.contracts.enforcement.DirectEqualityPolicy
          fabric$metrics$contracts$enforcement$DirectEqualityPolicy$(
          fabric.metrics.Metric arg1, double arg2) {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$DirectEqualityPolicy$(arg1,
                                                                         arg2);
        }
        
        public void activate(fabric.worker.metrics.StatsMap arg1) {
            ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
               fetch()).activate(arg1);
        }
        
        public void refresh(fabric.worker.metrics.StatsMap arg1) {
            ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
               fetch()).refresh(arg1);
        }
        
        public long expiry(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                      fetch()).expiry(arg1);
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
               fetch()).apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
               fetch()).unapply(arg1);
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
               fetch()).acquireReconfigLocks();
        }
        
        public _Proxy(DirectEqualityPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.enforcement.DirectEqualityPolicy {
        public fabric.metrics.Metric get$metric() { return this.metric; }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.Metric metric;
        
        public double get$value() { return this.value; }
        
        public double set$value(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp - 1));
            return tmp;
        }
        
        public double value;
        
        public long get$expiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.expiry;
        }
        
        public long set$expiry(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.expiry = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp - 1));
            return tmp;
        }
        
        /** The currently calculated expiration time to use for the contract. */
        private long expiry;
        
        public boolean get$activated() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.activated;
        }
        
        public boolean set$activated(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.activated = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** Is this policy active and being enforced? */
        private boolean activated;
        
        /**
   * @param metric
   *            the Metric the associated contract is bounding.
   * @param rate
   *            the rate of the bound enforced by the associated contract.
   * @param base
   *            the base of the bound enforced by the associated contract.
   */
        public fabric.metrics.contracts.enforcement.DirectEqualityPolicy
          fabric$metrics$contracts$enforcement$DirectEqualityPolicy$(
          fabric.metrics.Metric metric, double value) {
            this.set$metric(metric);
            this.set$value((double) value);
            fabric$lang$Object$();
            this.set$expiry((long) -1);
            this.set$activated(false);
            return (fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                     this.$getProxy();
        }
        
        public void activate(fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.contracts.enforcement.DirectEqualityPolicy._Impl.
              static_activate(
                (fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                  this.$getProxy(), weakStats);
        }
        
        private static void static_activate(
          fabric.metrics.contracts.enforcement.DirectEqualityPolicy tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$activated()) return;
                tmp.refresh(weakStats);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm451 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled454 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff452 = 1;
                    boolean $doBackoff453 = true;
                    boolean $retry448 = true;
                    $label446: for (boolean $commit447 = false; !$commit447; ) {
                        if ($backoffEnabled454) {
                            if ($doBackoff453) {
                                if ($backoff452 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff452);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e449) {
                                            
                                        }
                                    }
                                }
                                if ($backoff452 < 5000) $backoff452 *= 2;
                            }
                            $doBackoff453 = $backoff452 <= 32 || !$doBackoff453;
                        }
                        $commit447 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh(weakStats);
                        }
                        catch (final fabric.worker.RetryException $e449) {
                            $commit447 = false;
                            continue $label446;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e449) {
                            $commit447 = false;
                            fabric.common.TransactionID $currentTid450 =
                              $tm451.getCurrentTid();
                            if ($e449.tid.isDescendantOf($currentTid450))
                                continue $label446;
                            if ($currentTid450.parent != null) {
                                $retry448 = false;
                                throw $e449;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e449) {
                            $commit447 = false;
                            if ($tm451.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid450 =
                              $tm451.getCurrentTid();
                            if ($e449.tid.isDescendantOf($currentTid450)) {
                                $retry448 = true;
                            }
                            else if ($currentTid450.parent != null) {
                                $retry448 = false;
                                throw $e449;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e449) {
                            $commit447 = false;
                            if ($tm451.checkForStaleObjects())
                                continue $label446;
                            $retry448 = false;
                            throw new fabric.worker.AbortException($e449);
                        }
                        finally {
                            if ($commit447) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e449) {
                                    $commit447 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e449) {
                                    $commit447 = false;
                                    fabric.common.TransactionID $currentTid450 =
                                      $tm451.getCurrentTid();
                                    if ($currentTid450 != null) {
                                        if ($e449.tid.equals($currentTid450) ||
                                              !$e449.tid.isDescendantOf(
                                                           $currentTid450)) {
                                            throw $e449;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit447 && $retry448) {
                                {  }
                                continue $label446;
                            }
                        }
                    }
                }
            }
        }
        
        public void refresh(fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.contracts.enforcement.DirectEqualityPolicy._Impl.
              static_refresh(
                (fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                  this.$getProxy(), weakStats);
        }
        
        private static void static_refresh(
          fabric.metrics.contracts.enforcement.DirectEqualityPolicy tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$value() != tmp.get$metric().value()) {
                    tmp.set$expiry((long) 0);
                } else {
                    tmp.set$expiry((long) java.lang.Long.MAX_VALUE);
                }
                tmp.set$activated(true);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm460 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled463 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff461 = 1;
                    boolean $doBackoff462 = true;
                    boolean $retry457 = true;
                    $label455: for (boolean $commit456 = false; !$commit456; ) {
                        if ($backoffEnabled463) {
                            if ($doBackoff462) {
                                if ($backoff461 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff461);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e458) {
                                            
                                        }
                                    }
                                }
                                if ($backoff461 < 5000) $backoff461 *= 2;
                            }
                            $doBackoff462 = $backoff461 <= 32 || !$doBackoff462;
                        }
                        $commit456 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$value() != tmp.get$metric().value()) {
                                tmp.set$expiry((long) 0);
                            } else {
                                tmp.set$expiry((long) java.lang.Long.MAX_VALUE);
                            }
                            tmp.set$activated(true);
                        }
                        catch (final fabric.worker.RetryException $e458) {
                            $commit456 = false;
                            continue $label455;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e458) {
                            $commit456 = false;
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid.isDescendantOf($currentTid459))
                                continue $label455;
                            if ($currentTid459.parent != null) {
                                $retry457 = false;
                                throw $e458;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e458) {
                            $commit456 = false;
                            if ($tm460.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid.isDescendantOf($currentTid459)) {
                                $retry457 = true;
                            }
                            else if ($currentTid459.parent != null) {
                                $retry457 = false;
                                throw $e458;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e458) {
                            $commit456 = false;
                            if ($tm460.checkForStaleObjects())
                                continue $label455;
                            $retry457 = false;
                            throw new fabric.worker.AbortException($e458);
                        }
                        finally {
                            if ($commit456) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e458) {
                                    $commit456 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e458) {
                                    $commit456 = false;
                                    fabric.common.TransactionID $currentTid459 =
                                      $tm460.getCurrentTid();
                                    if ($currentTid459 != null) {
                                        if ($e458.tid.equals($currentTid459) ||
                                              !$e458.tid.isDescendantOf(
                                                           $currentTid459)) {
                                            throw $e458;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit456 && $retry457) {
                                {  }
                                continue $label455;
                            }
                        }
                    }
                }
            }
        }
        
        public long expiry(fabric.worker.metrics.StatsMap weakStats) {
            refresh(weakStats);
            return this.get$expiry();
        }
        
        public void apply(fabric.metrics.contracts.Contract mc) {
            fabric.metrics.contracts.enforcement.DirectEqualityPolicy._Impl.
              static_apply(
                (fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                  this.$getProxy(), mc);
        }
        
        private static void static_apply(
          fabric.metrics.contracts.enforcement.DirectEqualityPolicy tmp,
          fabric.metrics.contracts.Contract mc) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$activated())
                    tmp.activate(fabric.worker.metrics.StatsMap.emptyStats());
                tmp.get$metric().addObserver(mc);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm469 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled472 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff470 = 1;
                    boolean $doBackoff471 = true;
                    boolean $retry466 = true;
                    $label464: for (boolean $commit465 = false; !$commit465; ) {
                        if ($backoffEnabled472) {
                            if ($doBackoff471) {
                                if ($backoff470 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff470);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e467) {
                                            
                                        }
                                    }
                                }
                                if ($backoff470 < 5000) $backoff470 *= 2;
                            }
                            $doBackoff471 = $backoff470 <= 32 || !$doBackoff471;
                        }
                        $commit465 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated())
                                tmp.activate(
                                      fabric.worker.metrics.StatsMap.emptyStats(
                                                                       ));
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e467) {
                            $commit465 = false;
                            continue $label464;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e467) {
                            $commit465 = false;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468))
                                continue $label464;
                            if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468)) {
                                $retry466 = true;
                            }
                            else if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects())
                                continue $label464;
                            $retry466 = false;
                            throw new fabric.worker.AbortException($e467);
                        }
                        finally {
                            if ($commit465) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e467) {
                                    $commit465 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e467) {
                                    $commit465 = false;
                                    fabric.common.TransactionID $currentTid468 =
                                      $tm469.getCurrentTid();
                                    if ($currentTid468 != null) {
                                        if ($e467.tid.equals($currentTid468) ||
                                              !$e467.tid.isDescendantOf(
                                                           $currentTid468)) {
                                            throw $e467;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit465 && $retry466) {
                                {  }
                                continue $label464;
                            }
                        }
                    }
                }
            }
        }
        
        public void unapply(fabric.metrics.contracts.Contract mc) {
            this.get$metric().removeObserver(mc);
        }
        
        public java.lang.String toString() {
            return "Directly watching " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$metric())) +
            " == " +
            this.get$value();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.DirectEqualityPolicy))
                return false;
            fabric.metrics.contracts.enforcement.DirectEqualityPolicy that =
              (fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return this.get$metric().equals(that.get$metric()) &&
              this.get$value() == that.get$value();
        }
        
        public void acquireReconfigLocks() {  }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.
                     DirectEqualityPolicy._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeDouble(this.value);
            out.writeLong(this.expiry);
            out.writeBoolean(this.activated);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.metric = (fabric.metrics.Metric)
                            $readRef(fabric.metrics.Metric._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.value = in.readDouble();
            this.expiry = in.readLong();
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.DirectEqualityPolicy._Impl
              src =
              (fabric.metrics.contracts.enforcement.DirectEqualityPolicy._Impl)
                other;
            this.metric = src.metric;
            this.value = src.value;
            this.expiry = src.expiry;
            this.activated = src.activated;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy
        extends fabric.
          lang.
          Object.
          _Proxy
          implements fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                       _Static
        {
            public _Proxy(fabric.metrics.contracts.enforcement.
                            DirectEqualityPolicy._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.
              DirectEqualityPolicy._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  DirectEqualityPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    DirectEqualityPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                        _Static._Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                    _Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl
        extends fabric.
          lang.
          Object.
          _Impl
          implements fabric.metrics.contracts.enforcement.DirectEqualityPolicy.
                       _Static
        {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.enforcement.
                         DirectEqualityPolicy._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -74, -105, 56, 67, -42,
    74, -90, -117, 32, -23, 94, -68, -97, 22, -120, -93, -32, -114, 84, 14, 44,
    -58, 38, 28, 127, -30, 127, 112, 12, -81, 118, 23 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526756120000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
