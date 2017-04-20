package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

/**
 * A Warranty (also known as a General Contract) is a time-limited assertion of
 * the form <code>f(...) = y</code> and is enforced by an associated metric
 * contract.
 */
public interface Warranty extends fabric.metrics.contracts.Contract {
    public fabric.metrics.contracts.warranties.WarrantyComp get$computation();
    
    public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
      fabric.metrics.contracts.warranties.WarrantyComp val);
    
    public fabric.lang.Object get$value();
    
    public fabric.lang.Object set$value(fabric.lang.Object val);
    
    public fabric.metrics.contracts.MetricContract get$witness();
    
    public fabric.metrics.contracts.MetricContract set$witness(
      fabric.metrics.contracts.MetricContract val);
    
    /**
   * @param computation
   *        the computation being warrantied.
   */
    public fabric.metrics.contracts.warranties.Warranty
      fabric$metrics$contracts$warranties$Warranty$(
      fabric.metrics.contracts.warranties.WarrantyComp computation);
    
    public boolean refresh(boolean force);
    
    public long getExpiry();
    
    /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
    public fabric.lang.Object value();
    
    public java.lang.String toString();
    
    public fabric.util.Set getLeafSubjects();
    
    public void activate();
    
    public void deactivate();
    
    public void attemptExtension_remote(fabric.lang.security.Principal caller);
    
    public void attemptExtension();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$computation();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$computation(val);
        }
        
        public fabric.lang.Object get$value() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$value();
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$value(val);
        }
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$witness();
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$witness(val);
        }
        
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp arg1) {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              fabric$metrics$contracts$warranties$Warranty$(arg1);
        }
        
        public fabric.lang.Object value() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              value();
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(Warranty._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.computation;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.computation = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp computation;
        
        public fabric.lang.Object get$value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.lang.Object value;
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.witness;
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witness = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.MetricContract witness;
        
        /**
   * @param computation
   *        the computation being warrantied.
   */
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp computation) {
            this.set$computation(computation);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.warranties.Warranty)
                     this.$getProxy();
        }
        
        public boolean refresh(boolean force) {
            if (force &&
                  !fabric.lang.Object._Proxy.idEquals(this.get$witness(), null))
                return false;
            long currentTime = java.lang.System.currentTimeMillis();
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (!curVal.get$value().equals(this.get$value())) {
                    this.set$value(curVal.get$value());
                }
                if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                        null) &&
                      !fabric.lang.Object._Proxy.idEquals(
                                                   this.get$witness(),
                                                   curVal.get$contract())) {
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                    this.get$witness().deactivate();
                }
                this.set$witness(curVal.get$contract());
                this.get$witness().activate();
                this.get$witness().
                  addObserver((fabric.metrics.contracts.warranties.Warranty)
                                this.$getProxy());
                return true;
            }
            return false;
        }
        
        public long getExpiry() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) &&
                  isActive())
                return this.get$witness().getExpiry();
            return -1;
        }
        
        /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
        public fabric.lang.Object value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$value();
        }
        
        public java.lang.String toString() {
            return "Warranty " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    this.get$computation())) +
            " = " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$value())) +
            " until " +
            getExpiry();
        }
        
        public fabric.util.Set getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null))
                return this.get$witness().getLeafSubjects();
            else
                return fabric.util.Collections._Static._Proxy.$instance.
                  get$EMPTY_SET();
        }
        
        public void activate() {
            fabric.metrics.contracts.warranties.WarrantyValue curVal = null;
            fabric.metrics.contracts.MetricContract w = null;
            {
                fabric.metrics.contracts.warranties.WarrantyValue
                  curVal$var153 = curVal;
                fabric.metrics.contracts.MetricContract w$var154 = w;
                fabric.worker.transaction.TransactionManager $tm159 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff160 = 1;
                boolean $doBackoff161 = true;
                $label155: for (boolean $commit156 = false; !$commit156; ) {
                    if ($doBackoff161) {
                        if ($backoff160 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff160);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e157) {
                                    
                                }
                            }
                        }
                        if ($backoff160 < 5000) $backoff160 *= 2;
                    }
                    $doBackoff161 = $backoff160 <= 32 || !$doBackoff161;
                    $commit156 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ASDF TRYING TO COMPUTE");
                        long currentTime = java.lang.System.currentTimeMillis();
                        curVal = this.get$computation().apply(currentTime);
                        w = curVal.get$contract();
                    }
                    catch (final fabric.worker.RetryException $e157) {
                        $commit156 = false;
                        continue $label155;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e157) {
                        $commit156 = false;
                        fabric.common.TransactionID $currentTid158 =
                          $tm159.getCurrentTid();
                        if ($e157.tid.isDescendantOf($currentTid158))
                            continue $label155;
                        if ($currentTid158.parent != null) throw $e157;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e157) {
                        $commit156 = false;
                        if ($tm159.checkForStaleObjects()) continue $label155;
                        throw new fabric.worker.AbortException($e157);
                    }
                    finally {
                        if ($commit156) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e157) {
                                $commit156 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e157) {
                                $commit156 = false;
                                fabric.common.TransactionID $currentTid158 =
                                  $tm159.getCurrentTid();
                                if ($currentTid158 != null) {
                                    if ($e157.tid.equals($currentTid158) ||
                                          !$e157.tid.isDescendantOf(
                                                       $currentTid158)) {
                                        throw $e157;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit156) {
                            curVal = curVal$var153;
                            w = w$var154;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine("ASDF ACTIVATING");
            w.activate();
            {
                fabric.metrics.contracts.warranties.WarrantyValue
                  curVal$var162 = curVal;
                fabric.metrics.contracts.MetricContract w$var163 = w;
                fabric.worker.transaction.TransactionManager $tm168 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff169 = 1;
                boolean $doBackoff170 = true;
                $label164: for (boolean $commit165 = false; !$commit165; ) {
                    if ($doBackoff170) {
                        if ($backoff169 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff169);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e166) {
                                    
                                }
                            }
                        }
                        if ($backoff169 < 5000) $backoff169 *= 2;
                    }
                    $doBackoff170 = $backoff169 <= 32 || !$doBackoff170;
                    $commit165 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ASDF OBSERVING");
                        this.set$value(curVal.get$value());
                        this.set$witness(curVal.get$contract());
                        this.get$witness().
                          addObserver(
                            (fabric.metrics.contracts.warranties.Warranty)
                              this.$getProxy());
                        super.activate();
                    }
                    catch (final fabric.worker.RetryException $e166) {
                        $commit165 = false;
                        continue $label164;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e166) {
                        $commit165 = false;
                        fabric.common.TransactionID $currentTid167 =
                          $tm168.getCurrentTid();
                        if ($e166.tid.isDescendantOf($currentTid167))
                            continue $label164;
                        if ($currentTid167.parent != null) throw $e166;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e166) {
                        $commit165 = false;
                        if ($tm168.checkForStaleObjects()) continue $label164;
                        throw new fabric.worker.AbortException($e166);
                    }
                    finally {
                        if ($commit165) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e166) {
                                $commit165 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e166) {
                                $commit165 = false;
                                fabric.common.TransactionID $currentTid167 =
                                  $tm168.getCurrentTid();
                                if ($currentTid167 != null) {
                                    if ($e166.tid.equals($currentTid167) ||
                                          !$e166.tid.isDescendantOf(
                                                       $currentTid167)) {
                                        throw $e166;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit165) {
                            curVal = curVal$var162;
                            w = w$var163;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine("ASDF ACTIVATE");
        }
        
        public void deactivate() {
            {
                fabric.worker.transaction.TransactionManager $tm175 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff176 = 1;
                boolean $doBackoff177 = true;
                $label171: for (boolean $commit172 = false; !$commit172; ) {
                    if ($doBackoff177) {
                        if ($backoff176 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff176);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e173) {
                                    
                                }
                            }
                        }
                        if ($backoff176 < 5000) $backoff176 *= 2;
                    }
                    $doBackoff177 = $backoff176 <= 32 || !$doBackoff177;
                    $commit172 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!isObserved()) {
                            if (!fabric.lang.Object._Proxy.idEquals(
                                                             this.get$witness(),
                                                             null)) {
                                this.get$witness().
                                  removeObserver(
                                    (fabric.metrics.contracts.warranties.Warranty)
                                      this.$getProxy());
                                this.get$witness().deactivate();
                            }
                            this.set$value(null);
                        }
                        super.deactivate();
                    }
                    catch (final fabric.worker.RetryException $e173) {
                        $commit172 = false;
                        continue $label171;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e173) {
                        $commit172 = false;
                        fabric.common.TransactionID $currentTid174 =
                          $tm175.getCurrentTid();
                        if ($e173.tid.isDescendantOf($currentTid174))
                            continue $label171;
                        if ($currentTid174.parent != null) throw $e173;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e173) {
                        $commit172 = false;
                        if ($tm175.checkForStaleObjects()) continue $label171;
                        throw new fabric.worker.AbortException($e173);
                    }
                    finally {
                        if ($commit172) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e173) {
                                $commit172 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e173) {
                                $commit172 = false;
                                fabric.common.TransactionID $currentTid174 =
                                  $tm175.getCurrentTid();
                                if ($currentTid174 != null) {
                                    if ($e173.tid.equals($currentTid174) ||
                                          !$e173.tid.isDescendantOf(
                                                       $currentTid174)) {
                                        throw $e173;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit172) {  }
                    }
                }
            }
        }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            
        }
        
        public void attemptExtension() {  }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.Warranty._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.computation, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.witness, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.computation =
              (fabric.metrics.contracts.warranties.WarrantyComp)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyComp._Proxy.class,
                  (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                  intraStoreRefs, interStoreRefs);
            this.value = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.witness =
              (fabric.metrics.contracts.MetricContract)
                $readRef(fabric.metrics.contracts.MetricContract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.Warranty._Impl src =
              (fabric.metrics.contracts.warranties.Warranty._Impl) other;
            this.computation = src.computation;
            this.value = src.value;
            this.witness = src.witness;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.Warranty._Static {
            public _Proxy(fabric.metrics.contracts.warranties.Warranty._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.Warranty.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  Warranty.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    Warranty.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.Warranty._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.Warranty._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.Warranty._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.Warranty._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -9, 28, -113, -93, 27,
    0, -7, -13, 19, 77, 78, -98, -99, 38, -85, 27, -44, 27, -82, -112, -9, -4,
    -27, -17, 1, 85, -63, -93, -126, -84, -37, -32 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492660216000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
