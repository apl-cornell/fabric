package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.HashSet;
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
            long currentTime = java.lang.System.currentTimeMillis();
            if (!force &&
                  !fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                      null) &&
                  this.get$witness().valid(currentTime))
                return false;
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (fabric.lang.Object._Proxy.idEquals(curVal.get$value(),
                                                       null) &&
                      !fabric.lang.Object._Proxy.idEquals(this.get$value(),
                                                          null) ||
                      !fabric.lang.Object._Proxy.idEquals(curVal.get$value(),
                                                          null) &&
                      !curVal.get$value().equals(this.get$value())) {
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
            refresh(false);
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
                return ((fabric.util.HashSet)
                          new fabric.util.HashSet._Impl(this.$getStore()).
                          $getProxy()).fabric$util$HashSet$();
        }
        
        public void activate() {
            {
                fabric.worker.transaction.TransactionManager $tm157 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff158 = 1;
                boolean $doBackoff159 = true;
                $label153: for (boolean $commit154 = false; !$commit154; ) {
                    if ($doBackoff159) {
                        if ($backoff158 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff158);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e155) {
                                    
                                }
                            }
                        }
                        if ($backoff158 < 5000) $backoff158 *= 2;
                    }
                    $doBackoff159 = $backoff158 <= 32 || !$doBackoff159;
                    $commit154 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (isActive()) return; }
                    catch (final fabric.worker.RetryException $e155) {
                        $commit154 = false;
                        continue $label153;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e155) {
                        $commit154 = false;
                        fabric.common.TransactionID $currentTid156 =
                          $tm157.getCurrentTid();
                        if ($e155.tid.isDescendantOf($currentTid156))
                            continue $label153;
                        if ($currentTid156.parent != null) throw $e155;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e155) {
                        $commit154 = false;
                        if ($tm157.checkForStaleObjects()) continue $label153;
                        throw new fabric.worker.AbortException($e155);
                    }
                    finally {
                        if ($commit154) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e155) {
                                $commit154 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e155) {
                                $commit154 = false;
                                fabric.common.TransactionID $currentTid156 =
                                  $tm157.getCurrentTid();
                                if ($currentTid156 != null) {
                                    if ($e155.tid.equals($currentTid156) ||
                                          !$e155.tid.isDescendantOf(
                                                       $currentTid156)) {
                                        throw $e155;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit154) {  }
                    }
                }
            }
            fabric.metrics.contracts.warranties.WarrantyValue curVal = null;
            fabric.metrics.contracts.MetricContract w = null;
            {
                fabric.metrics.contracts.warranties.WarrantyValue
                  curVal$var160 = curVal;
                fabric.metrics.contracts.MetricContract w$var161 = w;
                fabric.worker.transaction.TransactionManager $tm166 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff167 = 1;
                boolean $doBackoff168 = true;
                $label162: for (boolean $commit163 = false; !$commit163; ) {
                    if ($doBackoff168) {
                        if ($backoff167 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff167);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e164) {
                                    
                                }
                            }
                        }
                        if ($backoff167 < 5000) $backoff167 *= 2;
                    }
                    $doBackoff168 = $backoff167 <= 32 || !$doBackoff168;
                    $commit163 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          fine("ASDF TRYING TO COMPUTE");
                        long currentTime = java.lang.System.currentTimeMillis();
                        curVal = this.get$computation().apply(currentTime);
                        w = curVal.get$contract();
                    }
                    catch (final fabric.worker.RetryException $e164) {
                        $commit163 = false;
                        continue $label162;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e164) {
                        $commit163 = false;
                        fabric.common.TransactionID $currentTid165 =
                          $tm166.getCurrentTid();
                        if ($e164.tid.isDescendantOf($currentTid165))
                            continue $label162;
                        if ($currentTid165.parent != null) throw $e164;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e164) {
                        $commit163 = false;
                        if ($tm166.checkForStaleObjects()) continue $label162;
                        throw new fabric.worker.AbortException($e164);
                    }
                    finally {
                        if ($commit163) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e164) {
                                $commit163 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e164) {
                                $commit163 = false;
                                fabric.common.TransactionID $currentTid165 =
                                  $tm166.getCurrentTid();
                                if ($currentTid165 != null) {
                                    if ($e164.tid.equals($currentTid165) ||
                                          !$e164.tid.isDescendantOf(
                                                       $currentTid165)) {
                                        throw $e164;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit163) {
                            curVal = curVal$var160;
                            w = w$var161;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine("ASDF ACTIVATING");
            w.activate();
            {
                fabric.metrics.contracts.warranties.WarrantyValue
                  curVal$var169 = curVal;
                fabric.metrics.contracts.MetricContract w$var170 = w;
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
                        if (!$commit172) {
                            curVal = curVal$var169;
                            w = w$var170;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.fine("ASDF ACTIVATE");
        }
        
        public void deactivate() {
            {
                fabric.worker.transaction.TransactionManager $tm182 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff183 = 1;
                boolean $doBackoff184 = true;
                $label178: for (boolean $commit179 = false; !$commit179; ) {
                    if ($doBackoff184) {
                        if ($backoff183 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff183);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e180) {
                                    
                                }
                            }
                        }
                        if ($backoff183 < 5000) $backoff183 *= 2;
                    }
                    $doBackoff184 = $backoff183 <= 32 || !$doBackoff184;
                    $commit179 = true;
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
                    catch (final fabric.worker.RetryException $e180) {
                        $commit179 = false;
                        continue $label178;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e180) {
                        $commit179 = false;
                        fabric.common.TransactionID $currentTid181 =
                          $tm182.getCurrentTid();
                        if ($e180.tid.isDescendantOf($currentTid181))
                            continue $label178;
                        if ($currentTid181.parent != null) throw $e180;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e180) {
                        $commit179 = false;
                        if ($tm182.checkForStaleObjects()) continue $label178;
                        throw new fabric.worker.AbortException($e180);
                    }
                    finally {
                        if ($commit179) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e180) {
                                $commit179 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e180) {
                                $commit179 = false;
                                fabric.common.TransactionID $currentTid181 =
                                  $tm182.getCurrentTid();
                                if ($currentTid181 != null) {
                                    if ($e180.tid.equals($currentTid181) ||
                                          !$e180.tid.isDescendantOf(
                                                       $currentTid181)) {
                                        throw $e180;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit179) {  }
                    }
                }
            }
        }
        
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
    
    public static final byte[] $classHash = new byte[] { -51, -28, -45, 78, 16,
    -60, 73, 112, 83, -56, -87, 127, 12, 74, -66, -50, -115, 99, 92, 56, 51,
    120, 32, 51, -12, -94, 64, -109, 21, 66, 85, 31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495740956000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
