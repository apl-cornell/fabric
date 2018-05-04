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
    
    public static final byte[] $classHash = new byte[] { 45, 39, 55, -8, 100,
    26, -78, 41, 44, 85, -6, -48, 127, 42, -5, -102, 110, 118, 46, -118, -80,
    83, 73, -46, 48, -4, -40, 125, -105, -55, -70, 26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525364618000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
