package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
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
    
    public void activate();
    
    public void refresh();
    
    public long expiry();
    
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
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
               fetch()).activate();
        }
        
        public void refresh() {
            ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
               fetch()).refresh();
        }
        
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                      fetch()).expiry();
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
        
        public void activate() {
            fabric.metrics.contracts.enforcement.DirectEqualityPolicy._Impl.
              static_activate(
                (fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                  this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.enforcement.DirectEqualityPolicy tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$activated()) return;
                tmp.refresh();
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm542 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled545 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff543 = 1;
                    boolean $doBackoff544 = true;
                    boolean $retry539 = true;
                    $label537: for (boolean $commit538 = false; !$commit538; ) {
                        if ($backoffEnabled545) {
                            if ($doBackoff544) {
                                if ($backoff543 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff543);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e540) {
                                            
                                        }
                                    }
                                }
                                if ($backoff543 < 5000) $backoff543 *= 2;
                            }
                            $doBackoff544 = $backoff543 <= 32 || !$doBackoff544;
                        }
                        $commit538 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh();
                        }
                        catch (final fabric.worker.RetryException $e540) {
                            $commit538 = false;
                            continue $label537;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e540) {
                            $commit538 = false;
                            fabric.common.TransactionID $currentTid541 =
                              $tm542.getCurrentTid();
                            if ($e540.tid.isDescendantOf($currentTid541))
                                continue $label537;
                            if ($currentTid541.parent != null) {
                                $retry539 = false;
                                throw $e540;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e540) {
                            $commit538 = false;
                            if ($tm542.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid541 =
                              $tm542.getCurrentTid();
                            if ($e540.tid.isDescendantOf($currentTid541)) {
                                $retry539 = true;
                            }
                            else if ($currentTid541.parent != null) {
                                $retry539 = false;
                                throw $e540;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e540) {
                            $commit538 = false;
                            if ($tm542.checkForStaleObjects())
                                continue $label537;
                            $retry539 = false;
                            throw new fabric.worker.AbortException($e540);
                        }
                        finally {
                            if ($commit538) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e540) {
                                    $commit538 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e540) {
                                    $commit538 = false;
                                    fabric.common.TransactionID $currentTid541 =
                                      $tm542.getCurrentTid();
                                    if ($currentTid541 != null) {
                                        if ($e540.tid.equals($currentTid541) ||
                                              !$e540.tid.isDescendantOf(
                                                           $currentTid541)) {
                                            throw $e540;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit538 && $retry539) {
                                {  }
                                continue $label537;
                            }
                        }
                    }
                }
            }
        }
        
        public void refresh() {
            fabric.metrics.contracts.enforcement.DirectEqualityPolicy._Impl.
              static_refresh(
                (fabric.metrics.contracts.enforcement.DirectEqualityPolicy)
                  this.$getProxy());
        }
        
        private static void static_refresh(
          fabric.metrics.contracts.enforcement.DirectEqualityPolicy tmp) {
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
                    fabric.worker.transaction.TransactionManager $tm551 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled554 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff552 = 1;
                    boolean $doBackoff553 = true;
                    boolean $retry548 = true;
                    $label546: for (boolean $commit547 = false; !$commit547; ) {
                        if ($backoffEnabled554) {
                            if ($doBackoff553) {
                                if ($backoff552 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff552);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e549) {
                                            
                                        }
                                    }
                                }
                                if ($backoff552 < 5000) $backoff552 *= 2;
                            }
                            $doBackoff553 = $backoff552 <= 32 || !$doBackoff553;
                        }
                        $commit547 = true;
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
                        catch (final fabric.worker.RetryException $e549) {
                            $commit547 = false;
                            continue $label546;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e549) {
                            $commit547 = false;
                            fabric.common.TransactionID $currentTid550 =
                              $tm551.getCurrentTid();
                            if ($e549.tid.isDescendantOf($currentTid550))
                                continue $label546;
                            if ($currentTid550.parent != null) {
                                $retry548 = false;
                                throw $e549;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e549) {
                            $commit547 = false;
                            if ($tm551.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid550 =
                              $tm551.getCurrentTid();
                            if ($e549.tid.isDescendantOf($currentTid550)) {
                                $retry548 = true;
                            }
                            else if ($currentTid550.parent != null) {
                                $retry548 = false;
                                throw $e549;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e549) {
                            $commit547 = false;
                            if ($tm551.checkForStaleObjects())
                                continue $label546;
                            $retry548 = false;
                            throw new fabric.worker.AbortException($e549);
                        }
                        finally {
                            if ($commit547) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e549) {
                                    $commit547 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e549) {
                                    $commit547 = false;
                                    fabric.common.TransactionID $currentTid550 =
                                      $tm551.getCurrentTid();
                                    if ($currentTid550 != null) {
                                        if ($e549.tid.equals($currentTid550) ||
                                              !$e549.tid.isDescendantOf(
                                                           $currentTid550)) {
                                            throw $e549;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit547 && $retry548) {
                                {  }
                                continue $label546;
                            }
                        }
                    }
                }
            }
        }
        
        public long expiry() {
            refresh();
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
                if (!tmp.get$activated()) tmp.activate();
                tmp.get$metric().addObserver(mc);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm560 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled563 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff561 = 1;
                    boolean $doBackoff562 = true;
                    boolean $retry557 = true;
                    $label555: for (boolean $commit556 = false; !$commit556; ) {
                        if ($backoffEnabled563) {
                            if ($doBackoff562) {
                                if ($backoff561 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff561);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e558) {
                                            
                                        }
                                    }
                                }
                                if ($backoff561 < 5000) $backoff561 *= 2;
                            }
                            $doBackoff562 = $backoff561 <= 32 || !$doBackoff562;
                        }
                        $commit556 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e558) {
                            $commit556 = false;
                            continue $label555;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e558) {
                            $commit556 = false;
                            fabric.common.TransactionID $currentTid559 =
                              $tm560.getCurrentTid();
                            if ($e558.tid.isDescendantOf($currentTid559))
                                continue $label555;
                            if ($currentTid559.parent != null) {
                                $retry557 = false;
                                throw $e558;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e558) {
                            $commit556 = false;
                            if ($tm560.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid559 =
                              $tm560.getCurrentTid();
                            if ($e558.tid.isDescendantOf($currentTid559)) {
                                $retry557 = true;
                            }
                            else if ($currentTid559.parent != null) {
                                $retry557 = false;
                                throw $e558;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e558) {
                            $commit556 = false;
                            if ($tm560.checkForStaleObjects())
                                continue $label555;
                            $retry557 = false;
                            throw new fabric.worker.AbortException($e558);
                        }
                        finally {
                            if ($commit556) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e558) {
                                    $commit556 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e558) {
                                    $commit556 = false;
                                    fabric.common.TransactionID $currentTid559 =
                                      $tm560.getCurrentTid();
                                    if ($currentTid559 != null) {
                                        if ($e558.tid.equals($currentTid559) ||
                                              !$e558.tid.isDescendantOf(
                                                           $currentTid559)) {
                                            throw $e558;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit556 && $retry557) {
                                {  }
                                continue $label555;
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
    
    public static final byte[] $classHash = new byte[] { 1, -6, 86, 84, 16, 48,
    -94, 115, 81, 118, 5, 22, 24, 83, 8, 67, 48, 74, -126, 103, 99, -109, -41,
    17, -24, 83, 81, 49, 116, -78, -51, 125 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525209021000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
