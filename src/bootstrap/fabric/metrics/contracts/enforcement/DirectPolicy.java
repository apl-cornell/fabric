package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.Metric;

/**
 * An enforcement policy for enforcing a {@link MetricContract}s by checking
 * every update to the associated metric.
 */
public interface DirectPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy {
    public fabric.metrics.contracts.MetricContract get$contract();
    
    public fabric.metrics.contracts.MetricContract set$contract(
      fabric.metrics.contracts.MetricContract val);
    
    public long get$expiry();
    
    public long set$expiry(long val);
    
    public long postInc$expiry();
    
    public long postDec$expiry();
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param expiry
   *        how long to enforce the {@link MetricContract} this is applied
   *        to using direct checks on updates to the metric.
   */
    public fabric.metrics.contracts.enforcement.DirectPolicy
      fabric$metrics$contracts$enforcement$DirectPolicy$(
      fabric.metrics.contracts.MetricContract contract);
    
    public void activate();
    
    public void refresh();
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Proxy
      implements fabric.metrics.contracts.enforcement.DirectPolicy {
        public fabric.metrics.contracts.MetricContract get$contract() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$contract();
        }
        
        public fabric.metrics.contracts.MetricContract set$contract(
          fabric.metrics.contracts.MetricContract val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$contract(val);
        }
        
        public long get$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$expiry();
        }
        
        public long set$expiry(long val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$expiry(val);
        }
        
        public long postInc$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postInc$expiry();
        }
        
        public long postDec$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postDec$expiry();
        }
        
        public boolean get$activated() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$activated();
        }
        
        public boolean set$activated(boolean val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$activated(val);
        }
        
        public fabric.metrics.contracts.enforcement.DirectPolicy
          fabric$metrics$contracts$enforcement$DirectPolicy$(
          fabric.metrics.contracts.MetricContract arg1) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(arg1);
        }
        
        public _Proxy(DirectPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Impl
      implements fabric.metrics.contracts.enforcement.DirectPolicy {
        public fabric.metrics.contracts.MetricContract get$contract() {
            return this.contract;
        }
        
        public fabric.metrics.contracts.MetricContract set$contract(
          fabric.metrics.contracts.MetricContract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.contract = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.MetricContract contract;
        
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
        
        public long expiry;
        
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
        
        public boolean activated;
        
        /**
   * @param expiry
   *        how long to enforce the {@link MetricContract} this is applied
   *        to using direct checks on updates to the metric.
   */
        public fabric.metrics.contracts.enforcement.DirectPolicy
          fabric$metrics$contracts$enforcement$DirectPolicy$(
          fabric.metrics.contracts.MetricContract contract) {
            this.set$contract(contract);
            fabric$metrics$contracts$enforcement$EnforcementPolicy$();
            this.set$expiry((long) -1);
            this.set$activated(false);
            return (fabric.metrics.contracts.enforcement.DirectPolicy)
                     this.$getProxy();
        }
        
        public void activate() {
            {
                fabric.worker.transaction.TransactionManager $tm32 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff33 = 1;
                boolean $doBackoff34 = true;
                $label28: for (boolean $commit29 = false; !$commit29; ) {
                    if ($doBackoff34) {
                        if ($backoff33 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff33);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e30) {  }
                            }
                        }
                        if ($backoff33 < 5000) $backoff33 *= 2;
                    }
                    $doBackoff34 = $backoff33 <= 32 || !$doBackoff34;
                    $commit29 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e30) {
                        $commit29 = false;
                        continue $label28;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e30) {
                        $commit29 = false;
                        fabric.common.TransactionID $currentTid31 =
                          $tm32.getCurrentTid();
                        if ($e30.tid.isDescendantOf($currentTid31))
                            continue $label28;
                        if ($currentTid31.parent != null) throw $e30;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e30) {
                        $commit29 = false;
                        if ($tm32.checkForStaleObjects()) continue $label28;
                        throw new fabric.worker.AbortException($e30);
                    }
                    finally {
                        if ($commit29) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e30) {
                                $commit29 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e30) {
                                $commit29 = false;
                                fabric.common.TransactionID $currentTid31 =
                                  $tm32.getCurrentTid();
                                if ($currentTid31 != null) {
                                    if ($e30.tid.equals($currentTid31) ||
                                          !$e30.tid.isDescendantOf(
                                                      $currentTid31)) {
                                        throw $e30;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit29) {  }
                    }
                }
            }
            refresh();
        }
        
        public void refresh() {
            {
                fabric.worker.transaction.TransactionManager $tm39 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff40 = 1;
                boolean $doBackoff41 = true;
                $label35: for (boolean $commit36 = false; !$commit36; ) {
                    if ($doBackoff41) {
                        if ($backoff40 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff40);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e37) {  }
                            }
                        }
                        if ($backoff40 < 5000) $backoff40 *= 2;
                    }
                    $doBackoff41 = $backoff40 <= 32 || !$doBackoff41;
                    $commit36 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        long currentTime = java.lang.System.currentTimeMillis();
                        long trueTime =
                          this.get$contract().getBound().trueExpiry(
                                                           this.get$contract(
                                                                  ).getMetric(),
                                                           currentTime);
                        if (trueTime < currentTime) this.set$expiry((long) 0);
                        long
                          hedgedTime =
                          ((fabric.metrics.contracts.enforcement.DirectPolicy.
                             _Impl) this.fetch()).hedged(currentTime);
                        if (this.get$contract().getExpiry() <= trueTime) {
                            hedgedTime =
                              java.lang.Math.max(
                                               this.get$contract().getExpiry(),
                                               hedgedTime);
                        }
                        this.set$expiry((long) hedgedTime);
                        this.set$activated(true);
                    }
                    catch (final fabric.worker.RetryException $e37) {
                        $commit36 = false;
                        continue $label35;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e37) {
                        $commit36 = false;
                        fabric.common.TransactionID $currentTid38 =
                          $tm39.getCurrentTid();
                        if ($e37.tid.isDescendantOf($currentTid38))
                            continue $label35;
                        if ($currentTid38.parent != null) throw $e37;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e37) {
                        $commit36 = false;
                        if ($tm39.checkForStaleObjects()) continue $label35;
                        throw new fabric.worker.AbortException($e37);
                    }
                    finally {
                        if ($commit36) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e37) {
                                $commit36 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e37) {
                                $commit36 = false;
                                fabric.common.TransactionID $currentTid38 =
                                  $tm39.getCurrentTid();
                                if ($currentTid38 != null) {
                                    if ($e37.tid.equals($currentTid38) ||
                                          !$e37.tid.isDescendantOf(
                                                      $currentTid38)) {
                                        throw $e37;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit36) {  }
                    }
                }
            }
        }
        
        /**
   * Using the associated {@link Metric's} statistics and some tuning
   * constants, figure out a time to advertise given the current time.
   *
   * @param time
   *        the current time we're calculating this hedged expiration at
   * @return an appropriately conservative time to advertise to other nodes in
   *       the system for this contract.
   */
        private long hedged(long time) {
            double r = this.get$contract().getBound().get$rate();
            double b = this.get$contract().getBound().value(time);
            fabric.metrics.Metric m = this.get$contract().getMetric();
            double x = m.value();
            double v = m.velocity();
            double n = m.noise() *
              fabric.metrics.contracts.enforcement.DirectPolicy._Static._Proxy.$instance.get$HEDGE_FACTOR() *
              fabric.metrics.contracts.enforcement.DirectPolicy._Static._Proxy.$instance.get$HEDGE_FACTOR();
            long hedgedResult = this.get$contract().getBound().trueExpiry(m,
                                                                          time);
            double bm = b - x;
            double rv = r - v;
            double minYs = x - (v > 0 ? n : -n) /
              (4.0 * (v * java.lang.Math.sqrt(v * v + 1)));
            long min = this.get$contract().getBound().trueExpiry(minYs, time);
            if (minYs < x && this.get$contract().getBound().test(minYs, time)) {
                hedgedResult = java.lang.Math.min(min, hedgedResult);
            }
            else if (bm * rv > 0) {
                double rotatedY1 =
                  1.0 - java.lang.Math.sqrt(4.0 * rv * bm + 1.0) / (2.0 * rv);
                double rotatedX1 =
                  (rotatedY1 + bm) / rv;
                double rotatedY2 =
                  1.0 + java.lang.Math.sqrt(4.0 * rv * bm + 1.0) / (2.0 * rv);
                double rotatedX2 =
                  (rotatedY2 + bm) / rv;
                double xxFact =
                  1 / java.lang.Math.sqrt(v * v + 1);
                double xyFact =
                  v / java.lang.Math.sqrt(v * v + 1);
                double intersectX1 = rotatedX1 *
                  xxFact +
                  rotatedY1 * xyFact;
                double intersectX2 = rotatedX2 *
                  xxFact +
                  rotatedY2 * xyFact;
                double soonestX1 =
                  intersectX1 >
                  0
                  ? time +
                  intersectX1
                  : java.lang.Long.MAX_VALUE;
                double soonestX2 =
                  intersectX2 >
                  0
                  ? time +
                  intersectX2
                  : java.lang.Long.MAX_VALUE;
                hedgedResult =
                  java.lang.Math.min((long)
                                       java.lang.Math.min(soonestX1, soonestX2),
                                     hedgedResult);
            }
            return hedgedResult;
        }
        
        public long expiry() { return this.get$expiry(); }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            {
                fabric.worker.transaction.TransactionManager $tm46 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff47 = 1;
                boolean $doBackoff48 = true;
                $label42: for (boolean $commit43 = false; !$commit43; ) {
                    if ($doBackoff48) {
                        if ($backoff47 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff47);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e44) {  }
                            }
                        }
                        if ($backoff47 < 5000) $backoff47 *= 2;
                    }
                    $doBackoff48 = $backoff47 <= 32 || !$doBackoff48;
                    $commit43 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) refresh();
                        mc.getMetric().startTracking(mc);
                    }
                    catch (final fabric.worker.RetryException $e44) {
                        $commit43 = false;
                        continue $label42;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e44) {
                        $commit43 = false;
                        fabric.common.TransactionID $currentTid45 =
                          $tm46.getCurrentTid();
                        if ($e44.tid.isDescendantOf($currentTid45))
                            continue $label42;
                        if ($currentTid45.parent != null) throw $e44;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e44) {
                        $commit43 = false;
                        if ($tm46.checkForStaleObjects()) continue $label42;
                        throw new fabric.worker.AbortException($e44);
                    }
                    finally {
                        if ($commit43) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e44) {
                                $commit43 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e44) {
                                $commit43 = false;
                                fabric.common.TransactionID $currentTid45 =
                                  $tm46.getCurrentTid();
                                if ($currentTid45 != null) {
                                    if ($e44.tid.equals($currentTid45) ||
                                          !$e44.tid.isDescendantOf(
                                                      $currentTid45)) {
                                        throw $e44;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit43) {  }
                    }
                }
            }
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            mc.getMetric().stopTracking(mc);
        }
        
        public java.lang.String toString() {
            return "Directly watching " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$contract()));
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.DirectPolicy))
                return false;
            fabric.metrics.contracts.enforcement.DirectPolicy that =
              (fabric.metrics.contracts.enforcement.DirectPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return fabric.lang.Object._Proxy.idEquals(this.get$contract(),
                                                      that.get$contract());
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.DirectPolicy._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.contract, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeLong(this.expiry);
            out.writeBoolean(this.activated);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.contract =
              (fabric.metrics.contracts.MetricContract)
                $readRef(fabric.metrics.contracts.MetricContract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.expiry = in.readLong();
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl src =
              (fabric.metrics.contracts.enforcement.DirectPolicy._Impl) other;
            this.contract = src.contract;
            this.expiry = src.expiry;
            this.activated = src.activated;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$HEDGE_FACTOR();
        
        public double set$HEDGE_FACTOR(double val);
        
        public double postInc$HEDGE_FACTOR();
        
        public double postDec$HEDGE_FACTOR();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.enforcement.DirectPolicy._Static {
            public double get$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).get$HEDGE_FACTOR();
            }
            
            public double set$HEDGE_FACTOR(double val) {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).set$HEDGE_FACTOR(val);
            }
            
            public double postInc$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).postInc$HEDGE_FACTOR();
            }
            
            public double postDec$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).postDec$HEDGE_FACTOR();
            }
            
            public _Proxy(fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.
              DirectPolicy._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  DirectPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    DirectPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.DirectPolicy._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.DirectPolicy._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.enforcement.DirectPolicy._Static {
            public double get$HEDGE_FACTOR() { return this.HEDGE_FACTOR; }
            
            public double set$HEDGE_FACTOR(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.HEDGE_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$HEDGE_FACTOR() {
                double tmp = this.get$HEDGE_FACTOR();
                this.set$HEDGE_FACTOR((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$HEDGE_FACTOR() {
                double tmp = this.get$HEDGE_FACTOR();
                this.set$HEDGE_FACTOR((double) (tmp - 1));
                return tmp;
            }
            
            private double HEDGE_FACTOR;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.HEDGE_FACTOR);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
                this.HEDGE_FACTOR = in.readDouble();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.enforcement.DirectPolicy.
                         _Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm53 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff54 = 1;
                        boolean $doBackoff55 = true;
                        $label49: for (boolean $commit50 = false; !$commit50;
                                       ) {
                            if ($doBackoff55) {
                                if ($backoff54 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff54);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e51) {
                                            
                                        }
                                    }
                                }
                                if ($backoff54 < 5000) $backoff54 *= 2;
                            }
                            $doBackoff55 = $backoff54 <= 32 || !$doBackoff55;
                            $commit50 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 0);
                            }
                            catch (final fabric.worker.RetryException $e51) {
                                $commit50 = false;
                                continue $label49;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e51) {
                                $commit50 = false;
                                fabric.common.TransactionID $currentTid52 =
                                  $tm53.getCurrentTid();
                                if ($e51.tid.isDescendantOf($currentTid52))
                                    continue $label49;
                                if ($currentTid52.parent != null) throw $e51;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e51) {
                                $commit50 = false;
                                if ($tm53.checkForStaleObjects())
                                    continue $label49;
                                throw new fabric.worker.AbortException($e51);
                            }
                            finally {
                                if ($commit50) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e51) {
                                        $commit50 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e51) {
                                        $commit50 = false;
                                        fabric.common.TransactionID
                                          $currentTid52 = $tm53.getCurrentTid();
                                        if ($currentTid52 != null) {
                                            if ($e51.tid.equals(
                                                           $currentTid52) ||
                                                  !$e51.tid.isDescendantOf(
                                                              $currentTid52)) {
                                                throw $e51;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit50) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 125, 16, 94, 11, 6, 62,
    87, 92, -55, 82, -82, 45, -20, 22, 27, -67, -108, -20, -7, 127, 25, -73, 12,
    -114, 11, 114, 113, -28, 64, 51, 19, 91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492662239000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
