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
                    fabric.worker.transaction.TransactionManager $tm482 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled485 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff483 = 1;
                    boolean $doBackoff484 = true;
                    boolean $retry479 = true;
                    $label477: for (boolean $commit478 = false; !$commit478; ) {
                        if ($backoffEnabled485) {
                            if ($doBackoff484) {
                                if ($backoff483 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff483);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e480) {
                                            
                                        }
                                    }
                                }
                                if ($backoff483 < 5000) $backoff483 *= 2;
                            }
                            $doBackoff484 = $backoff483 <= 32 || !$doBackoff484;
                        }
                        $commit478 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh(weakStats);
                        }
                        catch (final fabric.worker.RetryException $e480) {
                            $commit478 = false;
                            continue $label477;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e480) {
                            $commit478 = false;
                            fabric.common.TransactionID $currentTid481 =
                              $tm482.getCurrentTid();
                            if ($e480.tid.isDescendantOf($currentTid481))
                                continue $label477;
                            if ($currentTid481.parent != null) {
                                $retry479 = false;
                                throw $e480;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e480) {
                            $commit478 = false;
                            if ($tm482.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid481 =
                              $tm482.getCurrentTid();
                            if ($e480.tid.isDescendantOf($currentTid481)) {
                                $retry479 = true;
                            }
                            else if ($currentTid481.parent != null) {
                                $retry479 = false;
                                throw $e480;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e480) {
                            $commit478 = false;
                            if ($tm482.checkForStaleObjects())
                                continue $label477;
                            $retry479 = false;
                            throw new fabric.worker.AbortException($e480);
                        }
                        finally {
                            if ($commit478) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e480) {
                                    $commit478 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e480) {
                                    $commit478 = false;
                                    fabric.common.TransactionID $currentTid481 =
                                      $tm482.getCurrentTid();
                                    if ($currentTid481 != null) {
                                        if ($e480.tid.equals($currentTid481) ||
                                              !$e480.tid.isDescendantOf(
                                                           $currentTid481)) {
                                            throw $e480;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit478 && $retry479) {
                                {  }
                                continue $label477;
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
                    fabric.worker.transaction.TransactionManager $tm491 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled494 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff492 = 1;
                    boolean $doBackoff493 = true;
                    boolean $retry488 = true;
                    $label486: for (boolean $commit487 = false; !$commit487; ) {
                        if ($backoffEnabled494) {
                            if ($doBackoff493) {
                                if ($backoff492 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff492);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e489) {
                                            
                                        }
                                    }
                                }
                                if ($backoff492 < 5000) $backoff492 *= 2;
                            }
                            $doBackoff493 = $backoff492 <= 32 || !$doBackoff493;
                        }
                        $commit487 = true;
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
                        catch (final fabric.worker.RetryException $e489) {
                            $commit487 = false;
                            continue $label486;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e489) {
                            $commit487 = false;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490))
                                continue $label486;
                            if ($currentTid490.parent != null) {
                                $retry488 = false;
                                throw $e489;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e489) {
                            $commit487 = false;
                            if ($tm491.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490)) {
                                $retry488 = true;
                            }
                            else if ($currentTid490.parent != null) {
                                $retry488 = false;
                                throw $e489;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e489) {
                            $commit487 = false;
                            if ($tm491.checkForStaleObjects())
                                continue $label486;
                            $retry488 = false;
                            throw new fabric.worker.AbortException($e489);
                        }
                        finally {
                            if ($commit487) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e489) {
                                    $commit487 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e489) {
                                    $commit487 = false;
                                    fabric.common.TransactionID $currentTid490 =
                                      $tm491.getCurrentTid();
                                    if ($currentTid490 != null) {
                                        if ($e489.tid.equals($currentTid490) ||
                                              !$e489.tid.isDescendantOf(
                                                           $currentTid490)) {
                                            throw $e489;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit487 && $retry488) {
                                {  }
                                continue $label486;
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
                    fabric.worker.transaction.TransactionManager $tm500 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled503 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff501 = 1;
                    boolean $doBackoff502 = true;
                    boolean $retry497 = true;
                    $label495: for (boolean $commit496 = false; !$commit496; ) {
                        if ($backoffEnabled503) {
                            if ($doBackoff502) {
                                if ($backoff501 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff501);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e498) {
                                            
                                        }
                                    }
                                }
                                if ($backoff501 < 5000) $backoff501 *= 2;
                            }
                            $doBackoff502 = $backoff501 <= 32 || !$doBackoff502;
                        }
                        $commit496 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated())
                                tmp.activate(
                                      fabric.worker.metrics.StatsMap.emptyStats(
                                                                       ));
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e498) {
                            $commit496 = false;
                            continue $label495;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e498) {
                            $commit496 = false;
                            fabric.common.TransactionID $currentTid499 =
                              $tm500.getCurrentTid();
                            if ($e498.tid.isDescendantOf($currentTid499))
                                continue $label495;
                            if ($currentTid499.parent != null) {
                                $retry497 = false;
                                throw $e498;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e498) {
                            $commit496 = false;
                            if ($tm500.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid499 =
                              $tm500.getCurrentTid();
                            if ($e498.tid.isDescendantOf($currentTid499)) {
                                $retry497 = true;
                            }
                            else if ($currentTid499.parent != null) {
                                $retry497 = false;
                                throw $e498;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e498) {
                            $commit496 = false;
                            if ($tm500.checkForStaleObjects())
                                continue $label495;
                            $retry497 = false;
                            throw new fabric.worker.AbortException($e498);
                        }
                        finally {
                            if ($commit496) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e498) {
                                    $commit496 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e498) {
                                    $commit496 = false;
                                    fabric.common.TransactionID $currentTid499 =
                                      $tm500.getCurrentTid();
                                    if ($currentTid499 != null) {
                                        if ($e498.tid.equals($currentTid499) ||
                                              !$e498.tid.isDescendantOf(
                                                           $currentTid499)) {
                                            throw $e498;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit496 && $retry497) {
                                {  }
                                continue $label495;
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
    
    public static final byte[] $classHash = new byte[] { 83, -81, 42, 26, -38,
    33, 123, 62, 4, 124, 47, -39, -8, -39, -21, 71, 83, 11, -68, -103, -32, 29,
    1, -12, 1, -51, 109, -23, 81, 12, -87, -67 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
